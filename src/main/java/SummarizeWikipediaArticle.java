package main.java;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.*;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.standalone.CommandLineOptions;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsLoader;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import java.net.URI;
import java.net.URL;

import static us.monoid.web.Resty.content;

public class SummarizeWikipediaArticle {
	private URL url;
	private URI uri;
	private JSONResource response;
	private JSONArray sentences;
    private static final String X_MASHAPE_KEY = "dz76zlrCVimsh0GehwwnBjbZVXdgp1LwLckjsnety8AmdZq63k";

	public SummarizeWikipediaArticle(String articleToSummarize, int numberOfSentances, boolean useFakeServer) {
	
		Resty restCall = new Resty();
        restCall.withHeader("X-Mashape-Key", X_MASHAPE_KEY);

		WireMockServer wireMockServer = null;
		
		try {
			if (!useFakeServer) {
				url = new URL("https://textanalysis-text-summarization.p.mashape.com/text-summarizer");
			} else {				
				FileSource fileSource=new SingleRootFileSource("./wiremock");
				FileSource filesFileSource=fileSource.child("__files");
				FileSource mappingsFileSource=fileSource.child("mappings");
				
				CommandLineOptions options=new CommandLineOptions();

				wireMockServer=new WireMockServer(Options.DEFAULT_PORT, fileSource, options.browserProxyingEnabled());				
				wireMockServer.loadMappingsUsing(new JsonFileMappingsLoader(mappingsFileSource));
				
				wireMockServer.start();

				url = new URL("http://localhost:8080/text-summarizer");
			}

			uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			String requestToPost = "{\"url\": \"https://en.wikipedia.org/wiki/" + articleToSummarize + "\",\"text\": \"\",\"sentnum\": " + Integer.toString(numberOfSentances) + "}";
            response = restCall.json(uri,content(new JSONObject(requestToPost)));
			sentences = new JSONArray(response.get("sentences").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (useFakeServer) {
			wireMockServer.stop();
		}
	}

    public String getSentence(int lineNumber) throws Exception {
        return sentences.get(lineNumber-1).toString();
    }

}
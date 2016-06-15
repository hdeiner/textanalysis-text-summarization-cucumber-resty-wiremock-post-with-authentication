package test.java;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import main.java.SummarizeWikipediaArticle;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StepDefs {
	private boolean useFakeServer;
	private static SummarizeWikipediaArticle summarizeWikipediaArticle;
	private static String cachedArticleToSummarize = "";

	@Given("^I am testing on the actual web service$")
	public void i_am_testing_on_the_actual_web_service() throws Throwable {
		useFakeServer = false;
	}

	@Given("^I am testing on the fake web service$")
	public void i_am_testing_on_the_fake_web_service() throws Throwable {
		useFakeServer = true;
	}

	@When("^I summarize the wikipedia article for \"([^\"]*)\" in \"([^\"]*)\" sentances,$")
	public void i_summarize_the_wikipedia_article_for_in_sentances(String articleToSummarize, int numberOfSentances) throws Throwable {
		if (!articleToSummarize.equals(cachedArticleToSummarize)) {
			summarizeWikipediaArticle = new SummarizeWikipediaArticle(articleToSummarize, numberOfSentances, useFakeServer);
			cachedArticleToSummarize = articleToSummarize;
		}
	}

	@Then("^sentence \"([^\"]*)\" should be \"([^\"]*)\"$")
	public void i_sentence_should_be(int sentenceNumber, String sentence) throws Throwable {
		assertThat(summarizeWikipediaArticle.getSentence(sentenceNumber),is(sentence));
	}

}

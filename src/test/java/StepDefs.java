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
	private static int delayInMilliSeconds = 0;

	@Given("^I am testing on the actual web service$")
	public void i_am_testing_on_the_actual_web_service() throws Throwable {
		useFakeServer = false;
	}

	@Given("^I am testing on the fake web service$")
	public void i_am_testing_on_the_fake_web_service() throws Throwable {
		useFakeServer = true;
		delayInMilliSeconds = 0;
	}

	@Given("^I am testing on the actual web service with a timeout of \"(\\d+)\" milliseconds$")
	public void i_am_testing_on_the_actual_web_service_with_a_timeout(int delayInMilliSeconds) throws Throwable {
		useFakeServer = false;
		StepDefs.delayInMilliSeconds = delayInMilliSeconds;
	}

	@When("^I summarize the wikipedia article for \"([^\"]*)\" in \"([^\"]*)\" sentances,$")
	public void i_summarize_the_wikipedia_article_for_in_sentances(String articleToSummarize, int numberOfSentances) throws Throwable {
		if (!articleToSummarize.equals(cachedArticleToSummarize)) {
			summarizeWikipediaArticle = new SummarizeWikipediaArticle(articleToSummarize, numberOfSentances, useFakeServer, 0);
			cachedArticleToSummarize = articleToSummarize;
		}
	}

    @Then("^sentence \"([^\"]*)\" should be \"([^\"]*)\"$")
    public void i_sentence_should_be(int sentenceNumber, String sentence) throws Throwable {
        assertThat(summarizeWikipediaArticle.getSentence(sentenceNumber),is(sentence));
    }

    @Then("^when I summarize the wikipedia article for \"([^\"]*)\" in \"([^\"]*)\" sentances, I should see a timeout exception$")
    public void when_I_summarize_the_wikipedia_article_for_in_sentances_I_should_see_a_timeout(String articleToSummarize, int numberOfSentances)  {
        try {
            summarizeWikipediaArticle = new SummarizeWikipediaArticle(articleToSummarize, numberOfSentances, useFakeServer, StepDefs.delayInMilliSeconds);
            assertThat("Should have timedout within " + delayInMilliSeconds + " milliseconds", true, is(false));
        } catch (Exception e) {
            assertThat(e.getClass().toString(),is("class java.net.SocketTimeoutException"));
            assertThat(e.getMessage(),is("connect timed out"));
        }
    }

}

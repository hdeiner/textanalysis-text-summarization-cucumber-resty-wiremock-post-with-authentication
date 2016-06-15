Feature: Automatic Summarization Service
  https://market.mashape.com/textanalysis/text-summarization
  http://textsummarization.net/text-summarization-api-document

  Scenario Outline: Wikipedia article on Cucumber_(software)
    Given I am testing on the fake web service
    When I summarize the wikipedia article for "Cucumber_(software)" in "6" sentances,
    Then sentence "<lineNumber>" should be "<sentence>"

    Examples:
      |lineNumber|sentence                                                                                |
      |1         |Some use Ruby Cucumber with a bridge into the target language (e.g.                     |
      |2         |Cucumber is a software tool that computer programmers use for testing other software.   |
      |3         |It runs automated acceptance tests written in a behavior-driven development (BDD) style.|
      |4         |Others use the Gherkin parser but implement everything else in the target language.     |
      |5         |Cucumber is written in the Ruby programming language.                                   |
      |6         |Gherkin is the language that Cucumber uses to define test cases.                        |


  Scenario Outline: Wikipedia article on Automatic_summarization
    Given I am testing on the fake web service
    When I summarize the wikipedia article for "Automatic_summarization" in "5" sentances,
    Then sentence "<lineNumber>" should be "<sentence>"

    Examples:
      |lineNumber|sentence                                                                                                                                                   |
      |1         |Other examples include document summarization, image collection summarization and video summarization.                                                     |
      |2         |Examples of these include image collection summarization and video summarization.                                                                          |
      |3         |Automatic data summarization is part of machine learning and data mining.                                                                                  |
      |4         |In some application domains, extractive summarization makes more sense.                                                                                    |
      |5         |The different types of automatic summarization include extraction-based, abstraction-based, maximum entropy-based, and aided summarization.                |

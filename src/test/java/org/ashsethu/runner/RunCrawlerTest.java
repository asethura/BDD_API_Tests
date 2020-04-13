package org.ashsethu.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "pretty",
        glue = "org.ashsethu.stepDefinitions",
        features = "classpath:features/CrawlTheWeb.feature")
public class RunCrawlerTest {
}

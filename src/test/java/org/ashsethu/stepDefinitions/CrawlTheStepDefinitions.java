package org.ashsethu.stepDefinitions;


import cucumber.api.java8.En;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CrawlTheStepDefinitions implements En {

    private String BASE_URI = "http://localhost:8089/startCrawling?startingUrl=";
    private RequestSpecification request;
    private Response response;
    private ValidatableResponse json;
    public static final List<Integer> RESPONSE_CODES = Arrays.asList(HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_MOVED_PERM);

    public CrawlTheStepDefinitions() {
        Given("^a url \"([^\"]*)\" that is reachable by http$", (String url) -> {
            try {
                URL Url = new URL(url);
                HttpURLConnection huc = (HttpURLConnection) Url.openConnection();
                int responseCode = huc.getResponseCode();
                Assert.assertTrue(RESPONSE_CODES.contains(responseCode));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        When("^a user calls crawll the web api with url \"([^\"]*)\" as query parameter$", (String url) -> {
            String full_Url = BASE_URI + url;
            request = given().request();
            response = request.when().get(full_Url);
            System.out.println("response: " + response.prettyPrint());

        });

        Then("^the status code is (\\d+)$", (Integer responseCode) -> {
            json = response.then().statusCode(responseCode);
        });


        And("^response is empty list for images and links$", () -> {
            json.body("internal_images.urls", Matchers.hasSize(0))
                    .body("internal_links.urls", Matchers.hasSize(0))
                    .body("external_images.urls", Matchers.hasSize(0))
                    .body("external_links.urls", Matchers.hasSize(0));
        });

        And("^response is valid list of images and links$", () -> {
            File file = new File("JSON_Schema.jsd");
            json.body(JsonSchemaValidator.matchesJsonSchema(file));
        });
    }
}

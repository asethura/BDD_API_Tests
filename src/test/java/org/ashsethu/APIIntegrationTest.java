package org.ashsethu;


import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static org.hamcrest.Matchers.*;

public class APIIntegrationTest {

    @BeforeClass
    public static void setup() {
        // Setting BaseURI once
        RestAssured.port = 8089;

    }

    @Test
    public void givenSimpleUrl_whenSuccessOnGetsEmptyCrawlList_thenCorrect() {
        RestAssured.get("/startCrawling?startingUrl=http://www.brainjar.com/java/host/test.html").then().statusCode(200).assertThat()
                .body("internal_images.urls", Matchers.hasSize(0))
                .body("internal_links.urls", Matchers.hasSize(0))
                .body("external_images.urls", Matchers.hasSize(0))
                .body("external_links.urls", Matchers.hasSize(0));
    }

    @Test
    public void givenValidUrl_whenSuccessOnGetsJSONStructure_thenCorrect() {
        File file = new File("JSON_Schema.jsd");
        RestAssured.get("/startCrawling?startingUrl=http://www.brainjar.com/java/host/test.html").then().statusCode(200).assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(file));

    }


}


package org.acme.app.adapter.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class InfoResourceTest {

    @Test
    public void testInfoEndpoint() {
        given()
                .when().get("api/info")
                .then()
                .statusCode(200)
                .body("artifactId", equalTo("quarkus-example-app"));
    }
}

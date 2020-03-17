package org.acme.example.application.adapter.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class PingResourceIT {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("api/ping")
                .then()
                .statusCode(200)
                .body(is("pong"));
    }
}

package org.acme.example.documents.adapter.rest;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.acme.util.hamcrest.matchers.UuidMatcher.isValidUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
class DocumentResourceIT {

    private static String uuid;

    @TestHTTPResource
    URL deploymentURL;

    @SneakyThrows
    private URL getResourceURL(String path) {
        return new URL(deploymentURL.getProtocol(), "localhost", deploymentURL.getPort(), path);
    }

    private String relation(String path, String name) {
        return "<" + getResourceURL(path) + ">; rel=\"" + name + "\"";
    }

    @SneakyThrows
    private String location(String path) {
        return getResourceURL(path).toString();
    }

    @Test
    @Order(1)
    public void shouldCreateDocument() {
        var response = given().body(DocumentMessage.builder().name("foo").build())
                .and().contentType(ContentType.JSON)
                .when().post("api/documents")
                .then().statusCode(201)
                .and().extract().response();
        uuid = response.body().path("uuid");
        assertThat(uuid, isValidUUID());
        assertThat(response.header("Location"), equalTo(location("/api/documents/" + uuid)));
    }

    @Test
    @Order(2)
    public void shouldGetDocuments() {
        given()
                .when().get("api/documents")
                .then().statusCode(200)
                .and().body("size()", equalTo(1));
    }

    @Test
    @Order(3)
    public void shouldGetDocument() {
        given()
                .pathParam("id", uuid)
                .when().get("api/documents/{id}")
                .then().statusCode(200)
                .and().header("Link", equalTo(relation("/api/documents/" + uuid, "self")))
                .and().body("uuid", equalTo(uuid))
                .and().body("name", equalTo("foo"));
    }

    @Test
    @Order(4)
    public void shouldUpdateDocument() {
        given().body(DocumentMessage.builder().name("bar").build())
                .and().contentType(ContentType.JSON)
                .and().pathParam("uuid", uuid)
                .when().put("api/documents/{uuid}")
                .then().statusCode(200)
                .and().header("Link", equalTo(relation("/api/documents/" + uuid, "self")))
                .and().body("uuid", equalTo(uuid))
                .and().body("name", equalTo("bar"));
    }

    @Test
    @Order(5)
    public void shouldCountDocuments() {
        given().accept(ContentType.TEXT)
                .when().get("api/documents/count")
                .then().statusCode(200)
                .and().body(equalTo("1"));
    }

    @Test
    @Order(6)
    public void shouldDeleteDocument() {
        given()
                .when().pathParam("uuid", uuid)
                .and().delete("api/documents/{uuid}")
                .then().statusCode(204);
    }

    @Test
    @Order(7)
    public void shouldNotGetDocument() {
        given()
                .when().pathParam("uuid", uuid)
                .and().get("api/documents/{uuid}")
                .then().statusCode(404);
    }

    @Test
    @Order(8)
    public void shouldNotUpdateDocument() {
        given().body(DocumentMessage.builder().name("bar").build())
                .and().contentType(ContentType.JSON)
                .when().pathParam("uuid", uuid)
                .and().put("api/documents/{uuid}")
                .then().statusCode(404);
    }
}
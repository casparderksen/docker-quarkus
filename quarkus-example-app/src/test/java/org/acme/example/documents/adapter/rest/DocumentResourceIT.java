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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
class DocumentResourceIT {

    private static String id;

    @TestHTTPResource
    URL url;

    @SneakyThrows
    private String relation(String path, String name) {
        return "<" + new URL(url, path) + ">; rel=\"" + name + "\"";
    }

    @SneakyThrows
    private String location(String path) {
        return new URL(url, path).toString();
    }

    @Test
    @Order(1)
    public void shouldCreateDocument() {
        var response = given().body(DocumentDTO.builder().name("foo").build())
                .and().contentType(ContentType.JSON)
                .when().post("api/documents")
                .then().statusCode(201)
                .and().extract().response();
        id = response.body().path("id");
        assertThat(response.header("Location"), equalTo(location("api/documents/" + id)));
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
                .pathParam("id", id)
                .when().get("api/documents/{id}")
                .then().statusCode(200)
                .and().header("Link", equalTo(relation("api/documents/" + id, "self")))
                .and().body("id", equalTo(id))
                .and().body("name", equalTo("foo"));
    }

    @Test
    @Order(4)
    public void shouldUpdateDocument() {
        given().body(DocumentDTO.builder().name("bar").build())
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", id)
                .when().put("api/documents/{id}")
                .then().statusCode(200)
                .and().header("Link", equalTo(relation("api/documents/" + id, "self")))
                .and().body("id", equalTo(id))
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
                .when().pathParam("id", id)
                .and().delete("api/documents/{id}")
                .then().statusCode(204);
    }

    @Test
    @Order(7)
    public void shouldNotGetDocument() {
        given()
                .when().pathParam("id", id)
                .and().get("api/documents/{id}")
                .then().statusCode(404);
    }

    @Test
    @Order(8)
    public void shouldNotUpdateDocument() {
        given().body(DocumentDTO.builder().name("bar").build())
                .and().contentType(ContentType.JSON)
                .when().pathParam("id", id)
                .and().put("api/documents/{id}")
                .then().statusCode(404);
    }
}
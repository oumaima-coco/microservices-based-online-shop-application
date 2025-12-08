package com.ouma.microservices.Product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.shaded.org.hamcrest.Matchers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @ServiceConnection
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(

            DockerImageName.parse("mongo:7.0.5")
    );


    @LocalServerPort
    private int port;
    @BeforeEach
    void setup() {
        // Configures RestAssured to hit the correct local endpoint
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
    static {
        mongoDBContainer.start();
    }

        @Test
        void shouldCreateProduct() {
            String requestBody = """
            {
                "name": "iPhone 15",
                "description": "iPhone 15 is a smartphone from Apple",
                "price": 1000
            }
            """;
            RestAssured.given()
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/api/product")
                    .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("name", equalTo("iPhone 15"))
                    .body("description", equalTo("iPhone 15 is a smartphone from Apple"))
                    .body("price", equalTo(1000));
        }
    }


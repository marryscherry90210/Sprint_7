package ru.praktikum.scooter.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.scooter.config.ApiConfig;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {

    protected RequestSpecification spec() {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssured());
    }
}

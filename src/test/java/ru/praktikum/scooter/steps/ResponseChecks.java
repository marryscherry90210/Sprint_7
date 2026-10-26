package ru.praktikum.scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public final class ResponseChecks {

    private ResponseChecks() {
    }

    @Step("Проверить код ответа {statusCode} и ok: true")
    public static void checkOkTrue(ValidatableResponse response, int statusCode) {
        response.statusCode(statusCode)
                .body("ok", equalTo(true));
    }

    @Step("Проверить код ответа {statusCode} и сообщение об ошибке «{message}»")
    public static void checkError(ValidatableResponse response, int statusCode, String message) {
        response.statusCode(statusCode)
                .body("message", containsString(message));
    }

    @Step("Проверить код ответа {statusCode} и наличие поля «{field}»")
    public static void checkFieldPresent(ValidatableResponse response, int statusCode, String field) {
        response.statusCode(statusCode)
                .body(field, notNullValue());
    }

    @Step("Проверить код ответа {statusCode} и непустой список «{field}»")
    public static void checkNotEmptyList(ValidatableResponse response, int statusCode, String field) {
        response.statusCode(statusCode)
                .body(field, notNullValue())
                .body(field + ".size()", greaterThan(0));
    }
}

package ru.praktikum.scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.scooter.model.Courier;
import ru.praktikum.scooter.model.CourierCredentials;

import static ru.praktikum.scooter.config.ApiConfig.COURIER_BY_ID_PATH;
import static ru.praktikum.scooter.config.ApiConfig.COURIER_LOGIN_PATH;
import static ru.praktikum.scooter.config.ApiConfig.COURIER_PATH;

public class CourierClient extends BaseClient {

    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Авторизовать курьера")
    public ValidatableResponse login(CourierCredentials credentials) {
        return spec()
                .body(credentials)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then();
    }

    @Step("Удалить курьера с id {id}")
    public ValidatableResponse delete(int id) {
        return spec()
                .pathParam("id", id)
                .when()
                .delete(COURIER_BY_ID_PATH)
                .then();
    }

    @Step("Удалить курьера без указания id")
    public ValidatableResponse deleteWithoutId() {
        return spec()
                .when()
                .delete(COURIER_PATH)
                .then();
    }

    @Step("Получить id курьера через авторизацию")
    public Integer getId(CourierCredentials credentials) {
        return login(credentials).extract().path("id");
    }
}

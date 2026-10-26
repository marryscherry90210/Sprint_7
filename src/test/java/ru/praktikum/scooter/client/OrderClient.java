package ru.praktikum.scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.scooter.model.Order;

import java.util.Map;

import static ru.praktikum.scooter.config.ApiConfig.ORDERS_PATH;
import static ru.praktikum.scooter.config.ApiConfig.ORDER_ACCEPT_BY_ID_PATH;
import static ru.praktikum.scooter.config.ApiConfig.ORDER_ACCEPT_PATH;
import static ru.praktikum.scooter.config.ApiConfig.ORDER_CANCEL_PATH;
import static ru.praktikum.scooter.config.ApiConfig.ORDER_TRACK_PATH;

public class OrderClient extends BaseClient {

    private static final String COURIER_ID_PARAM = "courierId";
    private static final String TRACK_PARAM = "t";

    @Step("Создать заказ")
    public ValidatableResponse create(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getList() {
        return spec()
                .when()
                .get(ORDERS_PATH)
                .then();
    }

    @Step("Получить заказ по трек-номеру {track}")
    public ValidatableResponse getByTrack(int track) {
        return spec()
                .queryParam(TRACK_PARAM, track)
                .when()
                .get(ORDER_TRACK_PATH)
                .then();
    }

    @Step("Получить заказ без трек-номера")
    public ValidatableResponse getWithoutTrack() {
        return spec()
                .when()
                .get(ORDER_TRACK_PATH)
                .then();
    }

    @Step("Принять заказ {orderId} курьером {courierId}")
    public ValidatableResponse accept(int orderId, int courierId) {
        return spec()
                .pathParam("id", orderId)
                .queryParam(COURIER_ID_PARAM, courierId)
                .when()
                .put(ORDER_ACCEPT_BY_ID_PATH)
                .then();
    }

    @Step("Принять заказ {orderId} без id курьера")
    public ValidatableResponse acceptWithoutCourier(int orderId) {
        return spec()
                .pathParam("id", orderId)
                .when()
                .put(ORDER_ACCEPT_BY_ID_PATH)
                .then();
    }

    @Step("Принять заказ без номера заказа курьером {courierId}")
    public ValidatableResponse acceptWithoutOrder(int courierId) {
        return spec()
                .queryParam(COURIER_ID_PARAM, courierId)
                .when()
                .put(ORDER_ACCEPT_PATH)
                .then();
    }

    @Step("Получить id заказа по трек-номеру {track}")
    public Integer getOrderId(int track) {
        return getByTrack(track).extract().path("order.id");
    }

    @Step("Отменить заказ с трек-номером {track}")
    public ValidatableResponse cancel(int track) {
        return spec()
                .body(Map.of("track", track))
                .when()
                .put(ORDER_CANCEL_PATH)
                .then();
    }
}

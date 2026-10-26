package ru.praktikum.scooter.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.scooter.client.OrderClient;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static ru.praktikum.scooter.config.ErrorMessages.NOT_ENOUGH_DATA_TO_SEARCH;
import static ru.praktikum.scooter.config.ErrorMessages.ORDER_NOT_FOUND;
import static ru.praktikum.scooter.data.OrderGenerator.nonExistentId;
import static ru.praktikum.scooter.data.OrderGenerator.randomOrder;
import static ru.praktikum.scooter.steps.ResponseChecks.checkError;
import static ru.praktikum.scooter.steps.ResponseChecks.checkFieldPresent;

public class GetOrderByTrackTest {

    private OrderClient orderClient;
    private Integer track;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        track = orderClient.create(randomOrder(List.of())).extract().path("track");
    }

    @After
    public void tearDown() {
        if (track != null) {
            orderClient.cancel(track);
        }
    }

    @Test
    @DisplayName("Получение заказа по трек-номеру: 200 и объект order")
    public void getOrderByTrackReturnsOrder() {
        ValidatableResponse response = orderClient.getByTrack(track);

        checkFieldPresent(response, HTTP_OK, "order");
    }

    @Test
    @DisplayName("Получение заказа без трек-номера: 400")
    public void getOrderWithoutTrackReturnsBadRequest() {
        ValidatableResponse response = orderClient.getWithoutTrack();

        checkError(response, HTTP_BAD_REQUEST, NOT_ENOUGH_DATA_TO_SEARCH);
    }

    @Test
    @DisplayName("Получение заказа по несуществующему трек-номеру: 404")
    public void getOrderByNonExistentTrackReturnsNotFound() {
        ValidatableResponse response = orderClient.getByTrack(nonExistentId());

        checkError(response, HTTP_NOT_FOUND, ORDER_NOT_FOUND);
    }
}

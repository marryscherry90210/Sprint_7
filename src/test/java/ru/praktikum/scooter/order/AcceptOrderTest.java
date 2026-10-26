package ru.praktikum.scooter.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.scooter.client.CourierClient;
import ru.praktikum.scooter.client.OrderClient;
import ru.praktikum.scooter.model.Courier;
import ru.praktikum.scooter.model.CourierCredentials;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static ru.praktikum.scooter.config.ErrorMessages.COURIER_NOT_EXISTS;
import static ru.praktikum.scooter.config.ErrorMessages.NOT_ENOUGH_DATA_TO_SEARCH;
import static ru.praktikum.scooter.config.ErrorMessages.ORDER_NOT_EXISTS;
import static ru.praktikum.scooter.data.CourierGenerator.randomCourier;
import static ru.praktikum.scooter.data.OrderGenerator.nonExistentId;
import static ru.praktikum.scooter.data.OrderGenerator.randomOrder;
import static ru.praktikum.scooter.steps.ResponseChecks.checkError;
import static ru.praktikum.scooter.steps.ResponseChecks.checkOkTrue;

public class AcceptOrderTest {

    private CourierClient courierClient;
    private OrderClient orderClient;
    private Integer courierId;
    private Integer track;
    private Integer orderId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        orderClient = new OrderClient();

        Courier courier = randomCourier();
        courierClient.create(courier);
        courierId = courierClient.getId(CourierCredentials.from(courier));

        track = orderClient.create(randomOrder(List.of())).extract().path("track");
        orderId = orderClient.getOrderId(track);
    }

    @After
    public void tearDown() {
        if (track != null) {
            orderClient.cancel(track);
        }
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Принятие заказа: 200 и ok: true")
    public void acceptOrderReturnsOkTrue() {
        ValidatableResponse response = orderClient.accept(orderId, courierId);

        checkOkTrue(response, HTTP_OK);
    }

    @Test
    @DisplayName("Принятие заказа без id курьера: 400")
    public void acceptOrderWithoutCourierIdReturnsBadRequest() {
        ValidatableResponse response = orderClient.acceptWithoutCourier(orderId);

        checkError(response, HTTP_BAD_REQUEST, NOT_ENOUGH_DATA_TO_SEARCH);
    }

    @Test
    @DisplayName("Принятие заказа с неверным id курьера: 404")
    public void acceptOrderWithWrongCourierIdReturnsNotFound() {
        ValidatableResponse response = orderClient.accept(orderId, nonExistentId());

        checkError(response, HTTP_NOT_FOUND, COURIER_NOT_EXISTS);
    }

    @Test
    @DisplayName("Принятие заказа без номера заказа: 400")
    public void acceptOrderWithoutOrderIdReturnsBadRequest() {
        ValidatableResponse response = orderClient.acceptWithoutOrder(courierId);

        checkError(response, HTTP_BAD_REQUEST, NOT_ENOUGH_DATA_TO_SEARCH);
    }

    @Test
    @DisplayName("Принятие заказа с неверным номером заказа: 404")
    public void acceptOrderWithWrongOrderIdReturnsNotFound() {
        ValidatableResponse response = orderClient.accept(nonExistentId(), courierId);

        checkError(response, HTTP_NOT_FOUND, ORDER_NOT_EXISTS);
    }
}

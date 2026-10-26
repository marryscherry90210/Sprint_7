package ru.praktikum.scooter.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.scooter.client.OrderClient;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;
import static ru.praktikum.scooter.data.OrderGenerator.randomOrder;
import static ru.praktikum.scooter.steps.ResponseChecks.checkNotEmptyList;

public class GetOrdersListTest {

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
    @DisplayName("Получение списка заказов: 200 и непустой список orders")
    public void getOrdersListReturnsOrders() {
        ValidatableResponse response = orderClient.getList();

        checkNotEmptyList(response, HTTP_OK, "orders");
    }
}

package ru.praktikum.scooter.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.scooter.client.OrderClient;
import ru.praktikum.scooter.model.Order;

import java.util.Collection;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static ru.praktikum.scooter.data.OrderGenerator.randomOrder;
import static ru.praktikum.scooter.steps.ResponseChecks.checkFieldPresent;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private static final String BLACK = "BLACK";
    private static final String GREY = "GREY";

    private final List<String> colors;
    private OrderClient orderClient;
    private Integer track;

    public CreateOrderTest(String caseName, List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Цвет: {0}")
    public static Collection<Object[]> colors() {
        return List.of(new Object[][]{
                {BLACK, List.of(BLACK)},
                {GREY, List.of(GREY)},
                {"BLACK и GREY", List.of(BLACK, GREY)},
                {"не указан", List.of()}
        });
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        if (track != null) {
            orderClient.cancel(track);
        }
    }

    @Test
    @DisplayName("Создание заказа с разными цветами: 201 и track в ответе")
    public void createOrderReturnsTrack() {
        Order order = randomOrder(colors);

        ValidatableResponse response = orderClient.create(order);
        track = response.extract().path("track");

        checkFieldPresent(response, HTTP_CREATED, "track");
    }
}

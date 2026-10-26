package ru.praktikum.scooter.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.scooter.client.CourierClient;
import ru.praktikum.scooter.model.Courier;
import ru.praktikum.scooter.model.CourierCredentials;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static ru.praktikum.scooter.config.ErrorMessages.COURIER_WITH_ID_NOT_FOUND;
import static ru.praktikum.scooter.config.ErrorMessages.NOT_ENOUGH_DATA_TO_DELETE;
import static ru.praktikum.scooter.data.CourierGenerator.randomCourier;
import static ru.praktikum.scooter.steps.ResponseChecks.checkError;
import static ru.praktikum.scooter.steps.ResponseChecks.checkOkTrue;

public class DeleteCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = randomCourier();
        courierClient.create(courier);
        courierId = courierClient.getId(CourierCredentials.from(courier));
    }

    @After
    public void tearDown() {
        Integer existingId = courierClient.getId(CourierCredentials.from(courier));
        if (existingId != null) {
            courierClient.delete(existingId);
        }
    }

    @Test
    @DisplayName("Удаление курьера: 200 и ok: true")
    public void deleteCourierReturnsOkTrue() {
        ValidatableResponse response = courierClient.delete(courierId);

        checkOkTrue(response, HTTP_OK);
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id: 404")
    public void deleteNonExistentCourierReturnsNotFound() {
        courierClient.delete(courierId);

        ValidatableResponse response = courierClient.delete(courierId);

        checkError(response, HTTP_NOT_FOUND, COURIER_WITH_ID_NOT_FOUND);
    }

    @Test
    @DisplayName("Удаление курьера без id: 400")
    public void deleteCourierWithoutIdReturnsBadRequest() {
        ValidatableResponse response = courierClient.deleteWithoutId();

        checkError(response, HTTP_BAD_REQUEST, NOT_ENOUGH_DATA_TO_DELETE);
    }
}

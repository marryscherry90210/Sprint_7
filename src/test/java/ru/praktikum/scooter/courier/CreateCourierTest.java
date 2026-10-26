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
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static ru.praktikum.scooter.config.ErrorMessages.LOGIN_ALREADY_USED;
import static ru.praktikum.scooter.config.ErrorMessages.NOT_ENOUGH_DATA_TO_CREATE;
import static ru.praktikum.scooter.data.CourierGenerator.randomCourier;
import static ru.praktikum.scooter.data.CourierGenerator.randomString;
import static ru.praktikum.scooter.steps.ResponseChecks.checkError;
import static ru.praktikum.scooter.steps.ResponseChecks.checkOkTrue;

public class CreateCourierTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = randomCourier();
    }

    @After
    public void tearDown() {
        Integer courierId = courierClient.getId(CourierCredentials.from(courier));
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьера можно создать: 201 и ok: true")
    public void createCourierReturnsOkTrue() {
        ValidatableResponse response = courierClient.create(courier);

        checkOkTrue(response, HTTP_CREATED);
    }

    @Test
    @DisplayName("Курьера можно создать только с обязательными полями (без firstName)")
    public void createCourierWithRequiredFieldsOnlyReturnsOkTrue() {
        courier.setFirstName(null);

        ValidatableResponse response = courierClient.create(courier);

        checkOkTrue(response, HTTP_CREATED);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров: 409")
    public void createDuplicateCourierReturnsConflict() {
        courierClient.create(courier);

        ValidatableResponse response = courierClient.create(courier);

        checkError(response, HTTP_CONFLICT, LOGIN_ALREADY_USED);
    }

    @Test
    @DisplayName("Нельзя создать курьера с уже существующим логином: 409")
    public void createCourierWithExistingLoginReturnsConflict() {
        courierClient.create(courier);
        Courier courierWithSameLogin =
                new Courier(courier.getLogin(), randomString("pass_"), randomString("name_"));

        ValidatableResponse response = courierClient.create(courierWithSameLogin);

        checkError(response, HTTP_CONFLICT, LOGIN_ALREADY_USED);
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина: 400")
    public void createCourierWithoutLoginReturnsBadRequest() {
        courier.setLogin(null);

        ValidatableResponse response = courierClient.create(courier);

        checkError(response, HTTP_BAD_REQUEST, NOT_ENOUGH_DATA_TO_CREATE);
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля: 400")
    public void createCourierWithoutPasswordReturnsBadRequest() {
        courier.setPassword(null);

        ValidatableResponse response = courierClient.create(courier);

        checkError(response, HTTP_BAD_REQUEST, NOT_ENOUGH_DATA_TO_CREATE);
    }
}

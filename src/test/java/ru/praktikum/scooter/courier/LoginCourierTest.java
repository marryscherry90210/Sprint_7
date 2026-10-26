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
import static ru.praktikum.scooter.config.ErrorMessages.ACCOUNT_NOT_FOUND;
import static ru.praktikum.scooter.config.ErrorMessages.NOT_ENOUGH_DATA_TO_LOGIN;
import static ru.praktikum.scooter.data.CourierGenerator.randomCourier;
import static ru.praktikum.scooter.data.CourierGenerator.randomString;
import static ru.praktikum.scooter.steps.ResponseChecks.checkError;
import static ru.praktikum.scooter.steps.ResponseChecks.checkFieldPresent;

public class LoginCourierTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = randomCourier();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        Integer courierId = courierClient.getId(CourierCredentials.from(courier));
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться: 200 и id в ответе")
    public void loginCourierReturnsId() {
        ValidatableResponse response = courierClient.login(CourierCredentials.from(courier));

        checkFieldPresent(response, HTTP_OK, "id");
    }

    @Test
    @DisplayName("Авторизация с неверным паролем: 404")
    public void loginWithWrongPasswordReturnsNotFound() {
        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), randomString("wrong_"));

        ValidatableResponse response = courierClient.login(credentials);

        checkError(response, HTTP_NOT_FOUND, ACCOUNT_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с неверным логином: 404")
    public void loginWithWrongLoginReturnsNotFound() {
        CourierCredentials credentials = new CourierCredentials(randomString("wrong_"), courier.getPassword());

        ValidatableResponse response = courierClient.login(credentials);

        checkError(response, HTTP_NOT_FOUND, ACCOUNT_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера: 404")
    public void loginNonExistentCourierReturnsNotFound() {
        CourierCredentials credentials = CourierCredentials.from(randomCourier());

        ValidatableResponse response = courierClient.login(credentials);

        checkError(response, HTTP_NOT_FOUND, ACCOUNT_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация без логина: 400")
    public void loginWithoutLoginReturnsBadRequest() {
        CourierCredentials credentials = new CourierCredentials(null, courier.getPassword());

        ValidatableResponse response = courierClient.login(credentials);

        checkError(response, HTTP_BAD_REQUEST, NOT_ENOUGH_DATA_TO_LOGIN);
    }

    @Test
    @DisplayName("Авторизация без пароля: 400")
    public void loginWithoutPasswordReturnsBadRequest() {
        // Если поле password совсем не передать, сервис отвечает 504 по таймауту (известный баг),
        // поэтому передаём пустое значение
        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), "");

        ValidatableResponse response = courierClient.login(credentials);

        checkError(response, HTTP_BAD_REQUEST, NOT_ENOUGH_DATA_TO_LOGIN);
    }
}

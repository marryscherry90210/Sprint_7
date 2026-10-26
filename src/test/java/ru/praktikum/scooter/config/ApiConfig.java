package ru.praktikum.scooter.config;

public final class ApiConfig {

    public static final String BASE_URI =
            System.getProperty("baseUri", "https://qa-scooter.praktikum-services.ru");

    public static final String COURIER_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    public static final String COURIER_BY_ID_PATH = "/api/v1/courier/{id}";

    public static final String ORDERS_PATH = "/api/v1/orders";
    public static final String ORDER_TRACK_PATH = "/api/v1/orders/track";
    public static final String ORDER_ACCEPT_PATH = "/api/v1/orders/accept";
    public static final String ORDER_ACCEPT_BY_ID_PATH = "/api/v1/orders/accept/{id}";
    public static final String ORDER_CANCEL_PATH = "/api/v1/orders/cancel";

    private ApiConfig() {
    }
}

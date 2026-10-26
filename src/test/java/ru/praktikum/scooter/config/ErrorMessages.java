package ru.praktikum.scooter.config;

public final class ErrorMessages {

    public static final String NOT_ENOUGH_DATA_TO_CREATE = "Недостаточно данных для создания учетной записи";
    public static final String LOGIN_ALREADY_USED = "Этот логин уже используется";
    public static final String NOT_ENOUGH_DATA_TO_LOGIN = "Недостаточно данных для входа";
    public static final String ACCOUNT_NOT_FOUND = "Учетная запись не найдена";

    public static final String NOT_ENOUGH_DATA_TO_DELETE = "Недостаточно данных для удаления курьера";
    public static final String COURIER_WITH_ID_NOT_FOUND = "Курьера с таким id нет";

    public static final String NOT_ENOUGH_DATA_TO_SEARCH = "Недостаточно данных для поиска";
    public static final String COURIER_NOT_EXISTS = "Курьера с таким id не существует";
    public static final String ORDER_NOT_EXISTS = "Заказа с таким id не существует";
    public static final String ORDER_NOT_FOUND = "Заказ не найден";

    private ErrorMessages() {
    }
}

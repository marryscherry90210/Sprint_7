package ru.praktikum.scooter.data;

import ru.praktikum.scooter.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class OrderGenerator {

    private static final int MIN_RENT_DAYS = 1;
    private static final int MAX_RENT_DAYS = 7;
    private static final int MIN_METRO_STATION = 1;
    private static final int MAX_METRO_STATION = 200;
    private static final int MIN_NON_EXISTENT_ID = 900_000_000;

    private OrderGenerator() {
    }

    public static Order randomOrder(List<String> colors) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Order(
                "Иван",
                "Петров",
                "Москва, ул. Тестовая, " + random.nextInt(1, 100),
                String.valueOf(random.nextInt(MIN_METRO_STATION, MAX_METRO_STATION)),
                "+7999" + random.nextInt(1_000_000, 9_999_999),
                random.nextInt(MIN_RENT_DAYS, MAX_RENT_DAYS + 1),
                LocalDate.now().plusDays(1).toString(),
                CourierGenerator.randomString("comment_"),
                colors
        );
    }

    public static int nonExistentId() {
        return ThreadLocalRandom.current().nextInt(MIN_NON_EXISTENT_ID, Integer.MAX_VALUE);
    }
}

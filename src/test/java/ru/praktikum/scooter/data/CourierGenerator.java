package ru.praktikum.scooter.data;

import ru.praktikum.scooter.model.Courier;

import java.util.UUID;

public final class CourierGenerator {

    private static final int RANDOM_PART_LENGTH = 10;

    private CourierGenerator() {
    }

    public static Courier randomCourier() {
        return new Courier(randomString("login_"), randomString("pass_"), randomString("name_"));
    }

    public static String randomString(String prefix) {
        return prefix + UUID.randomUUID().toString().replace("-", "").substring(0, RANDOM_PART_LENGTH);
    }
}

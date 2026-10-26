# Sprint_7 — API-тесты учебного сервиса «Яндекс.Самокат»

Документация API: https://qa-scooter.praktikum-services.ru/docs/

## Стек
Java 11, Maven, JUnit 4, RestAssured, Gson, Allure.

## Что покрыто
| Класс | Ручка |
|---|---|
| `CreateCourierTest` | POST /api/v1/courier — создание курьера |
| `LoginCourierTest` | POST /api/v1/courier/login — логин курьера |
| `CreateOrderTest` | POST /api/v1/orders — создание заказа (параметризация по цвету) |
| `GetOrdersListTest` | GET /api/v1/orders — список заказов |
| `DeleteCourierTest` | DELETE /api/v1/courier/:id — удаление курьера (доп.) |
| `AcceptOrderTest` | PUT /api/v1/orders/accept/:id — принятие заказа (доп.) |
| `GetOrderByTrackTest` | GET /api/v1/orders/track — заказ по трек-номеру (доп.) |

Тестовые данные (курьеры, заказы) создаются в `@Before` и удаляются/отменяются в `@After`.

## Запуск
```bash
mvn clean test
mvn allure:serve      # открыть отчёт в браузере
```

## Отчёт Allure в репозиторий
```bash
git add -f target/allure-results/.
git commit -m "add allure report"
git push
```

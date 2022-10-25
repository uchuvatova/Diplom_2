package orders;

import config.BaseClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrdersClient extends BaseClient {

    private static final String ORDERS = "/orders";
    @Step("Создание заказа")
    public ValidatableResponse createOrder(Orders order, String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .and()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }
    @Step("Получение заказов пользователя")
    public ValidatableResponse getOrders(String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS)
                .then().log().all();
    }
    public ValidatableResponse getWithoutAuth() {
        return getSpec()
                .when()
                .get(ORDERS)
                .then().log().all();
    }


}
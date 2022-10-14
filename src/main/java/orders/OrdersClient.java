package orders;

import io.restassured.response.ValidatableResponse;

public class OrdersClient extends BaseOrders {

    private final String ORDERS = "/orders";

    public ValidatableResponse createWithoutAuth(Orders order) {
        return getSpec()
                .and()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    public ValidatableResponse createWithoutIngr() {
        return getSpec()
                .when()
                .post(ORDERS)
                .then().log().all();

    }
    public ValidatableResponse createWithAuth(String accessToken, Orders order) {
        return getSpec()
                .header("Authorization", accessToken)
                .and()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }
    public ValidatableResponse getWithAuth(String accessToken) {
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
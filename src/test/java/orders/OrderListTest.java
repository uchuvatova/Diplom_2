package orders;

import ingredients.IngredientClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import users.User;
import users.UserClient;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Получение заказов конкретного пользователя")
public class OrderListTest {

    private Orders order;
    private final OrdersClient orderClient = new OrdersClient();
    private final UserClient userClient = new UserClient();
    private String accessToken;
    IngredientClient ingredientClient = new IngredientClient();
    ValidatableResponse response;

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void orderListAuthTest() {
        User user = User.getRandomUser();
        accessToken = userClient.create(user).extract().path("accessToken");
        order = Orders.random(ingredientClient.getIngredients(), 3);
        orderClient.createOrder(order, accessToken);
        orderClient.createOrder(order, accessToken);
        orderClient.createOrder(order, accessToken);
        response = orderClient.getOrders(accessToken);
        userClient.deleteUser(accessToken);
        response.and().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void orderListNotAuthTest() {
        accessToken = "";
        order = Orders.random(ingredientClient.getIngredients(), 3);
        orderClient.createOrder(order, accessToken);
        orderClient.createOrder(order, accessToken);
        orderClient.createOrder(order, accessToken);
        response = orderClient.getOrders(accessToken);
        response.and().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
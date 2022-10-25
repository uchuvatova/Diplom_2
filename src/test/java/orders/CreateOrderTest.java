package orders;

import ingredients.IngredientClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import users.User;
import users.UserClient;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

@DisplayName("Создание заказа")
public class CreateOrderTest{
    User user;
    private Orders order;
    private OrdersClient ordersClient = new OrdersClient();
    private IngredientClient ingredientClient = new IngredientClient();
    private final UserClient userClient = new UserClient();
    private String accessToken;
    private String hash;

    @Before
    public void setUp() {
        ingredientClient = new IngredientClient();
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Создание заказа с авторизацией с ингредиентами")
    public void shouldOrderWithAuth() {
        User user = User.getRandomUser();
        accessToken = userClient.create(user).extract().path("accessToken");
        order = Orders.random(ingredientClient.getIngredients(), 3);
        ordersClient.createOrder(order, accessToken)
                .and()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("order.number", notNullValue())
                .and()
                .body("order.owner.email", is(user.getEmail().toLowerCase()));

    }

    @Test
    @DisplayName("Создание заказа без авторизации с ингредиентами")
    public void shouldOrderWithoutAuth() {
        User user = User.getRandomUser();
        accessToken = "";
        order = Orders.random(ingredientClient.getIngredients(), 3);
        ordersClient.createOrder(order, accessToken)
                .and()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true))
                .and()
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации без ингредиентов")
    public void shouldNotOrderWithoutIngr() {
        User user = User.getRandomUser();
        accessToken = "";
        order = Orders.random(ingredientClient.getIngredients(), 0);
        ordersClient.createOrder(order, accessToken)
                .and()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", is("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void shouldNotCreateWithInvalidHash() {
        User user = User.getRandomUser();
        accessToken = userClient.create(user).extract().path("accessToken");
        hash = "000000000000000000000000";
        order = Orders.withHash(hash);
        ordersClient.createOrder(order, accessToken)
                .and()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", is("One or more ids provided are incorrect"));
    }
    @After
    public void deleteUser(){
        userClient.deleteUser(accessToken);
}
}
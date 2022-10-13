package orders;

import ingredients.IngredientClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import setup.Setup;
import static org.hamcrest.Matchers.*;

@DisplayName("Создание заказа")
public class CreateOrderTest extends Setup {

    private String hash;

    @Before
    public void setUp() {
        ingredientClient = new IngredientClient();
        orderClient = new OrdersClient();
        order = Orders.random(ingredientClient.getIngredients(), 3);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией с ингредиентами")
    public void shouldOrderWithAuth() {
        registerTestUser();
        accessToken = userClient.getAccessToken(user);
        orderClient.createWithAuth(accessToken, order)
                .and()
                .assertThat()
                .statusCode(200)
                .and()
                .body("order.number", notNullValue())
                .and()
                .body("order.owner.email", is(user.getEmail().toLowerCase()));
    }

    @Test
    @DisplayName("Создание заказа без авторизации с ингредиентами")
    public void shouldOrderWithoutAuth() {
        orderClient.createWithoutAuth(order)
                .and()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", is(true))
                .and()
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void shouldNotOrderWithoutIngr() {
        orderClient.createWithoutIngr()
                .and()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void shouldNotCreateWithInvalidHash() {
        hash = "000000000000000000000000";
        order = Orders.withHash(hash);
        orderClient.createWithoutAuth(order)
                .and()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", is("One or more ids provided are incorrect"));
    }
}
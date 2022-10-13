package orders;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import setup.Setup;

import static org.hamcrest.Matchers.*;

@DisplayName("Получение заказов конкретного пользователя")
public class OrderListTest extends Setup {

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void shouldGetWithAuth() {
        registerTestUser();
        accessToken = userClient.getAccessToken(user);
        setUpOrder(accessToken);
        orderClient.getWithAuth(accessToken)
                .and()
                .assertThat()
                .statusCode(200)
                .and()
                .body("orders", hasSize(greaterThan(0)));
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void shouldNotGetWithoutAuth() {
        orderClient = new OrdersClient();
        orderClient.getWithoutAuth()
                .and()
                .assertThat()
                .statusCode(401)
                .and()
                .body("message", is("You should be authorised"));
    }
}
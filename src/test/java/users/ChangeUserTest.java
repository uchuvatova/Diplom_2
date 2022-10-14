package users;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChangeUserTest {

    User user;
    UserClient userClient;
    private boolean success;
    private String accessToken;

    @Before
    public void setup() {
        user = User.getRandomUser();
        userClient = new UserClient();

    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void changeAuthUserTest() {
        String accessToken = userClient.create(user).extract().path("accessToken");
        boolean successLogin = userClient.login(user).extract().path("success");
        assertTrue(successLogin);
        boolean successChange = userClient.update(user, accessToken).extract().path("success");
        assertTrue(successChange);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void changeNotAuthUserTest() {
        String accessToken = userClient.create(user).extract().path("accessToken");
        String message = userClient.updateWithoutAuth(user).extract().path("message");
        assertEquals("You should be authorised", message);

    }
}

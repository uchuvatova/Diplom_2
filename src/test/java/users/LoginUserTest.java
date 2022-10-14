package users;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class LoginUserTest {
    User user;
    UserClient userClient;
    private String accessToken;

    @Before
    public void setup() {
        user = User.getRandomUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void successLoginTest() {
        String accessToken = userClient.create(user).extract().path("accessToken");
        boolean success = userClient.login(user).extract().path("success");
        assertTrue(success);
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void loginWrongCredsTest() {
        String accessToken = userClient.create(user).extract().path("accessToken");
        UserWrongCreds wrongCreds = UserWrongCreds.from(user);
        String message = userClient.loginWrongCreds(wrongCreds).extract().path("message");
        assertEquals("email or password are incorrect", message);
    }

}

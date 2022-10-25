package users;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.apache.http.HttpStatus.*;


public class LoginUserTest {
    User user;
    UserClient userClient;
    private String accessToken;
    private String email;
    private String password;
    Response response;
    @Before
    public void setup() {
        user = User.getRandomUser();
        userClient = new UserClient();
        accessToken = userClient.create(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void successLoginTest() {
        userClient.login(user, accessToken)
                .then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void wrongLoginTest() {
        email = user.getEmail();
        user.setEmail("1" + email);
        response = userClient.login(user, accessToken);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
    @Test
    @DisplayName("Логин с неверным паролем")
    public void wrongPasswordTest() {
        password = user.getPassword();
        user.setPassword(password + "1");
        response = userClient.login(user, accessToken);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
    @After
    public void deleteUser(){
        userClient.deleteUser(accessToken); // удаляем тестового пользователя
    }
}

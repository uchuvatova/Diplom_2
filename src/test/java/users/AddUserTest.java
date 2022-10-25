package users;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;


public class AddUserTest {

    User user;
    UserClient userClient;
    ValidatableResponse response;
    private String accessToken;


    @Before
    public void setup() {
        user = User.getRandomUser();
        userClient = new UserClient();


    }
    @Test
    @DisplayName("Создание уникального пользователя")
    public void createValidUserTest() {
        response = userClient.create(user);
        accessToken = response.extract().body().path("accessToken");
        userClient.deleteUser(accessToken);
        response.assertThat().body("success", equalTo(true))
               .and()
               .statusCode(SC_OK);
        }
    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createSameUserTest() {
        accessToken = userClient.create(user).extract().body().path("accessToken");
        response = userClient.create(user);
        response.and().assertThat().body("message", equalTo("User already exists"))
        .and()
        .statusCode(SC_FORBIDDEN);
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля email")
    public void createWithoutEmailTest() {
        user = User.getWithoutEmail();
        response = userClient.create(user);
        response.and().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }
    @Test
    @DisplayName("Создание пользователя без обязательного поля Пароль")
    public void createWithoutPasswordTest() {
        user = User.getWithoutPassword();
        response = userClient.create(user);
        response.and().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }
    @Test
    @DisplayName("Создание пользователя без обязательного поля Имя")
    public void createWithoutNameTest() {
        user = User.getWithoutName();
        response = userClient.create(user);
        response.and().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }
}
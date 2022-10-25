package users;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;
public class ChangeUserTest {

    User user;
    UserClient userClient;
    private String accessToken;
    private String data;
    Response response;

    @Before
    public void setup() {
        user = User.getRandomUser();
        userClient = new UserClient();
        accessToken = userClient.create(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Изменение имени пользователя с авторизацией")
    public void changeNameAuthTest() {
        data = user.getName();
        user.setName(data+"ru");
        response = userClient.changeUser(user, accessToken);
        user.setName(data);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Изменение логина пользователя с авторизацией")
    public void changeEmailAuthTest() {
        data = user.getEmail();
        user.setEmail("123" + data);
        response = userClient.changeUser(user, accessToken);
        user.setEmail(data);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Изменение пароля пользователя с авторизацией")
    public void changePasswordAuthTest() {
        data = user.getPassword();
        user.setPassword("123" + data);
        response = userClient.changeUser(user, accessToken);
        user.setPassword(data);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Изменение имени пользователя без авторизации")
    public void changeNameNotAuthTest() {
        data = user.getName();
        user.setName(data+"ru");
        accessToken = "";
        response = userClient.changeUser(user, accessToken);
        user.setName(data);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
    @Test
    @DisplayName("Изменение логина пользователя без авторизации")
    public void changeEmailNotAuthTest() {
        data = user.getEmail();
        user.setEmail("123" + data);
        accessToken = "";
        response = userClient.changeUser(user, accessToken);
        user.setName(data);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
    @Test
    @DisplayName("Изменение пароля пользователя без авторизации")
    public void changePasswordNotAuthTest() {
        data = user.getPassword();
        user.setPassword("123" + data);
        accessToken = "";
        response = userClient.changeUser(user, accessToken);
        user.setName(data);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void deleteUser(){
        userClient.deleteUser(accessToken);
    }
}

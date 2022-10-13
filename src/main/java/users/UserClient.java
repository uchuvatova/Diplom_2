package users;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static users.UserCredentials.loginData;

public class UserClient extends BaseClient {

    private final String ROOT = "/auth";
    private final String REGISTER = ROOT + "/register";
    private final String LOGIN = ROOT + "/login";
    private final String LOGOUT = ROOT + "/logout";
    private final String USER = ROOT + "/user";

    @Step("Создание нового пользователя")
    public ValidatableResponse create(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Создание пользователя с незаполненными данными")
    public ValidatableResponse createFailed(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .statusCode(403);
    }

    @Step("Логин с правильными данными")
    public ValidatableResponse login(User user) {
        return getSpec()
                .body(loginData(user))
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Step("Получение accessToken (Bearer) существующего пользователя")
    public String getAccessToken(User user) {
        return login(user)
                .and()
                .extract()
                .path("accessToken");
    }

    @Step("Логин с неправильными данными")
    public ValidatableResponse loginWrongCreds(UserWrongCreds wrongCreds) {
        return getSpec()
                .body(wrongCreds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(401);
    }

    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse updateWithoutAuth(User user) {
        return getSpec()
                .and()
                .body(user)
                .when()
                .patch(USER)
                .then().log().all()
                .assertThat()
                .statusCode(401);
    }
    @Step("Изменение данных пользователя с авторизацией")
    public ValidatableResponse update(User user, String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .and()
                .body(user)
                .when()
                .patch(USER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
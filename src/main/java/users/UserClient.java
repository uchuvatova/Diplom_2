package users;
import config.BaseClient;
import config.Config;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    private static final String ROOT = "/auth";
    private static final String REGISTER = ROOT + "/register";
    private static final String LOGIN = ROOT + "/login";
    private static final String USER = ROOT + "/user";

    @Step("Создание нового пользователя")
    public ValidatableResponse create(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }
    @Step("Удаление созданного пользователя")
    public void deleteUser(String accessToken) {
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .baseUri(Config.BASE_URL)
                .delete(USER)
                .then().log().all();
    }

    @Step("Логин пользователя")
    public Response login(User user, String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .baseUri(Config.BASE_URL)
                .body(user)
                .post(LOGIN);
    }

    @Step("Изменение данных пользователя")
    public Response changeUser(User newUser, String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .baseUri(Config.BASE_URL)
                .body(newUser)
                .patch(USER);
    }
}
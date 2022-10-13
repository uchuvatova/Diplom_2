package users;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class AddUserTest {

    User user;
    UserClient userClient;
    private String doubleEmail;


    @Before
    public void setup() {
        user = User.getRandomUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createValidUserTest() {
        boolean success = userClient.create(user).extract().path("success");
        assertTrue(success);
        }
    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createSameUserTest() {
        doubleEmail = userClient.create(user).extract().path("email");
        String message = userClient.create(user).extract().path("message");
        assertEquals("User already exists", message);
        int code = userClient.create(user).extract().statusCode();
        assertEquals(403, code);
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля email")
    public void createWithoutEmailTest() {
        user = User.getWithoutEmail();
        String message = userClient.createFailed(user).extract().path("message");
        assertEquals("Email, password and name are required fields", message);
    }
    @Test
    @DisplayName("Создание пользователя без обязательного поля Пароль")
    public void createWithoutPasswordTest() {
        user = User.getWithoutPassword();
        String message = userClient.createFailed(user).extract().path("message");
        assertEquals("Email, password and name are required fields", message);
    }
    @Test
    @DisplayName("Создание пользователя без обязательного поля Имя")
    public void createWithoutNameTest() {
        user = User.getWithoutName();
        String message = userClient.createFailed(user).extract().path("message");
        assertEquals("Email, password and name are required fields", message);
    }


}
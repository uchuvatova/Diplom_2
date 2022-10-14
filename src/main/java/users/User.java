package users;
import org.apache.commons.lang3.RandomStringUtils;
public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandomUser() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru",
                "P@ssw0rd",
                RandomStringUtils.randomAlphabetic(10)
        );
    }
    public static User getWithoutPassword() {

        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru",
                "",
                RandomStringUtils.randomAlphabetic(10) );
    }
    public static User getWithoutEmail() {

        return new User(
                "",
                "P@ssw0rd",
                RandomStringUtils.randomAlphabetic(10) );
    }
    public static User getWithoutName() {

        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru",
                "P@ssw0rd",
                "");
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = name;
    }

}
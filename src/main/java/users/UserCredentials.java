package users;

import io.qameta.allure.Step;
import lombok.Data;

@Data

public class UserCredentials {

    private String email;

    private String password;

    private String name;

    public UserCredentials(String email, String password, String name ) {

        this.email = email;

        this.password = password;

        this.name = name;

    }

    public static users.UserCredentials from(User user) {

        return new users.UserCredentials(user.getEmail(), user.getPassword(), user.getName() );

    }

    @Step("Пользователь для авторизации")
    public static User loginData(User user) {

        return new User(user.getEmail(), user.getPassword(), null);

    }
}
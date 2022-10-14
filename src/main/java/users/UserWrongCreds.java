package users;
import lombok.Data;

@Data
public class UserWrongCreds {

    private String email;

    private String password;

    private String accessToken;

    public UserWrongCreds(String email, String password, String accessToken ) {

        this.email = email;

        this.password = password;

        this.accessToken = accessToken;

    }
    public static UserWrongCreds from(User user) {

        return new UserWrongCreds("1" + user.getEmail(), "1" + user.getPassword(), null );
    }


}
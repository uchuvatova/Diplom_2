package setup;

import ingredients.IngredientClient;
import orders.Orders;
import orders.OrdersClient;
import users.User;
import users.UserClient;


public class Setup {

    protected OrdersClient orderClient;
    protected IngredientClient ingredientClient;
    protected Orders order;
    protected static UserClient userClient;
    protected static User user;
    protected static String accessToken;


    public static void registerTestUser() {
        userClient = new UserClient();
        user = User.getRandomUser();
        userClient.create(user)
                .and()
                .statusCode(200);
    }
    public void setUpOrder(String accessToken) {
        ingredientClient = new IngredientClient();
        orderClient = new OrdersClient();
        order = Orders.random(ingredientClient.getIngredients(), 3);
        orderClient.createWithAuth(accessToken, order)
                .and()
                .statusCode(200);
    }

   }

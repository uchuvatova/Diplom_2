package orders;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Orders {
    public List<String> ingredients = null;

    public Orders() {
    }

    public Orders (List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static Orders random(List<String> availableIngr, int quantity) {
        Orders orders = new Orders();
        orders.ingredients = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            orders.ingredients.add(availableIngr.get(random.nextInt(availableIngr.size())));
        }
        return orders;
    }

    public static Orders withHash(String hash) {
        Orders orders = new Orders();
        orders.ingredients = new ArrayList<>();
        orders.ingredients.add(hash);
        return orders;
    }


}
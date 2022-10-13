package ingredients;
import java.util.ArrayList;
import java.util.List;

public class IngredientClient extends BaseClient {
    private final String INGRED = "/ingredients";
            public List<String> getIngredients() {
            List<String> ingredientIds = new ArrayList<>();
            Ingredients ingr = getSpec()
                    .when()
                    .get(INGRED)
                    .body()
                    .as(Ingredients.class);
           for (IngredientData data : ingr.getData())
           ingredientIds.add(data.get_id());
            return ingredientIds;
        }
    }


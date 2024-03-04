package com.recipe.demo.repo;

import com.recipe.demo.model.Ingredient;
import com.recipe.demo.model.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    private Ingredient ingredient1, ingredient2;
    private Recipe recipe1;
//    private List<Recipe> recipeList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        recipe1 = new Recipe(1L, "name", "description", "instruction", null);
        ingredient1 = new Ingredient(1L, "name-1", "quantity-1", recipe1);
        ingredient2 = new Ingredient(2L, "name-2", "quantity-2", recipe1);
    }

    @AfterEach
    public void tearDown() {
        ingredientRepository.deleteAll();
        recipeRepository.deleteAll();
        ingredient1 = ingredient2 = null;
        recipe1 = null;
    }

//    @Test
//    public void saveIngredientTest() {
//
//        ingredientRepository.save(ingredient1);
//        Ingredient fetchedIngredient = ingredientRepository.findById(1L).get();
//        assertEquals(1, fetchedIngredient.getId());
//    }

    @Test
    public void getListOfIngredientsTest() {

        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertEquals("name-1", ingredientList.get(0).getName());

    }
//    @Test
//    public void findAllIngredientsByRecipeIdTest() {
//
//        recipeRepository.save(recipe1);
//
//        ingredientRepository.save(ingredient1);
//        ingredientRepository.save(ingredient2);
//        List<Ingredient> ingredientList = ingredientRepository.findAllByRecipeId(1L);
//        assertEquals("name-1", ingredientList.get(0).getName());
//
//    }
}

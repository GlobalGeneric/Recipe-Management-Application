package com.recipe.demo.service;

import com.recipe.demo.model.Ingredient;
import com.recipe.demo.model.Recipe;
import com.recipe.demo.repo.IngredientRepository;
import com.recipe.demo.repo.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
@Transactional
public class IngredientServiceTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientServiceImpl ingredientService;

    private Ingredient ingredient1;
    private Recipe recipe1;

    @BeforeEach
    public void setUp() {
        recipe1 = new Recipe(1L, "name", "description", "instruction", null);
        ingredient1 = new Ingredient(1L, "name-1", "quantity-1", recipe1);
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        recipe1.setIngredients(ingredientList);
        recipeRepository.save(recipe1);
    }


    @Test
    @Rollback(false)
    public void addNewIngredientMethodTest() {
        Ingredient newIngredient = new Ingredient(2L, "name-1", "quantity-1", null);
        ingredientService.addNewIngredient(1L, newIngredient);
        Ingredient ingredient = ingredientRepository.findById(2L).get();
        assertEquals(ingredient.getId(), newIngredient.getId());
        assertEquals(ingredient.getName(), newIngredient.getName());

    }

    @Test
    @Rollback(false)
    public void getIngredientMethodTest() {
        Recipe recipe1 = new Recipe(1L, "name", "description", "instruction", null);
        Ingredient ingredient1 = new Ingredient(1L, "name-1", "quantity-1", recipe1);
        Ingredient ingredient2 = new Ingredient(2L, "name-1", "quantity-1", recipe1);

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        recipe1.setIngredients(ingredientList);

        recipeRepository.save(recipe1);
        List<Ingredient> ingredientList2 = ingredientService.getAllIngredientOfRecipe(1L);
        Ingredient ingredient3 = ingredientService.getIngredient(1L);
        assertEquals(ingredient3.getId(), 1L);
        assertEquals(ingredientList2.size(), 2);

    }

    @Test
    @Rollback(false)
    public void getAllIngredientMethodTest() {


        Recipe recipe1 = new Recipe(1L, "name", "description", "instruction", null);
        Ingredient ingredient1 = new Ingredient(1L, "name-1", "quantity-1", recipe1);
        Ingredient ingredient2 = new Ingredient(2L, "name-1", "quantity-1", recipe1);

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        recipe1.setIngredients(ingredientList);

        recipeRepository.save(recipe1);
        List<Ingredient> ingredientList2 = ingredientService.getAllIngredientOfRecipe(1L);
        Ingredient ingredientList3 = ingredientService.getIngredient(1L);
        assertEquals(ingredientList2.get(0).getName(), ingredientList.get(0).getName());

    }

}

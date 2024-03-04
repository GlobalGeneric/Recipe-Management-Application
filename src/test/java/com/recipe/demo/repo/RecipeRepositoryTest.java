package com.recipe.demo.repo;

import com.recipe.demo.model.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;
    private Recipe recipe;
//    private List<Recipe> recipeList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
//        recipeList = new ArrayList<>();
        recipe = new Recipe(1L, "name", "description", "instruction", null);
//        recipeList.add(recipe);
    }

    @AfterEach
    public void tearDown() {
        recipeRepository.deleteAll();
        recipe = null;
    }

    @Test
    public void saveRecipeTest() {
        recipeRepository.save(recipe);
        Recipe fetchedRecipe = recipeRepository.findById(recipe.getId()).get();
        assertEquals(1, fetchedRecipe.getId());
    }

    @Test
    public void getListOfRecipesTest() {
        Recipe recipe1 = new Recipe(1L, "name", "description", "instruction", null);
        Recipe recipe2 = new Recipe(2L, "name2", "description2", "instruction2", null);
        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        List<Recipe> recipeList1 = recipeRepository.findAll();
        assertEquals("name", recipeList1.get(0).getName());

    }

    @Test
    public void getListOfSortedRecipesTest() {
        Recipe recipe1 = new Recipe(1L, "b", "description", "instruction", null);
        Recipe recipe2 = new Recipe(2L, "a", "description2", "instruction2", null);
        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        List<Recipe> recipeList1 = recipeRepository.findAllRecipes();
        //the second item comes first
        assertEquals("a", recipeList1.get(0).getName());

    }

    @Test
    public void givenIdThenShouldReturnRecipeOfThatId() {
        Recipe recipe1 = new Recipe(1L, "name", "description", "instruction", null);
        Recipe recipe2 = recipeRepository.save(recipe1);

        Optional<Recipe> optional = recipeRepository.findById(recipe2.getId());
        assertEquals(recipe2.getId(), optional.get().getId());
        assertEquals(recipe2.getName(), optional.get().getName());
    }

}

package com.recipe.demo.service;

import com.recipe.demo.exception.IngredientNotFoundException;
import com.recipe.demo.exception.RecipeNotFoundException;
import com.recipe.demo.model.Ingredient;
import com.recipe.demo.model.Recipe;
import com.recipe.demo.repo.IngredientRepository;
import com.recipe.demo.repo.RecipeRepository;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest2 {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @InjectMocks
    private IngredientServiceImpl ingredientService;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Recipe recipe1;
    private List<Ingredient> ingredientList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        ingredientList = new ArrayList<>();
        ingredient1 = new Ingredient(1L, "name-1", "quantity-1", null);
        ingredient2 = new Ingredient(2L, "name-2", "quantity-2", null);
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        recipe1 = new Recipe(1L, "name", "description", "instruction", null);
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() {
        ingredient1 = ingredient2 = null;
        ingredientRepository.deleteAll();
    }

    @Test
    public void addNewIngredientExpectedRecipeNotFoundException() {
        Throwable exception = assertThrows(RecipeNotFoundException.class,
                () -> ingredientService.addNewIngredient(1L, ingredient1));
        assertEquals("Recipe not found with ID: 1", exception.getMessage());
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    public void GivenIngredientIdTDeleteRecipe() {

        ingredientService.setIngredientRepository(this.ingredientRepository);
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        ingredientService.removeIngredient(1L);
        verify(ingredientRepository, times(1)).findById(1L);
    }

    @Test
    public void GivenIngredientIdThrowIngredientNotFoundExceptionIfIdNotFoundForDeleteIngredient() {
        IngredientNotFoundException thrown = Assertions.assertThrows(IngredientNotFoundException.class, () -> {
            IngredientService ingredientService = new IngredientServiceImpl(ingredientRepository, recipeRepository);
            exception.expect(IngredientNotFoundException.class);
            ingredientService.removeIngredient(1L);
        });
        MatcherAssert.assertThat(thrown.getMessage(), containsString("Ingredient not found with ID: "));
    }
}
package com.recipe.demo.service;

import com.recipe.demo.exception.RecipeNotFoundException;
import com.recipe.demo.model.Recipe;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Mock
    private RecipeRepository recipeRepository;
    @Autowired
    @InjectMocks
    private RecipeServiceImpl recipeService;
    //
    private Recipe recipe1, recipe2;
    private List<Recipe> recipeList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeList = new ArrayList<>();
        recipe1 = new Recipe(1L, "name", "description", "instruction", null);
        recipe2 = new Recipe(1L, "name", "description", "instruction", null);
        recipeList.add(recipe1);
        recipeList.add(recipe2);
    }

    @AfterEach
    public void tearDown() {
        recipe1 = null;
        recipeRepository.deleteAll();
    }

    @Test
    public void testAddRecipe() {

        when(recipeRepository.save(any())).thenReturn(recipe1);
        recipeService.addRecipe(recipe1);
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void GivenGetAllRecipesShouldReturnListOfAllRecipes() {
        recipeRepository.save(recipe1);
        when(recipeRepository.findAllRecipes()).thenReturn(recipeList);
        List<Recipe> recipeList1 = recipeService.getAllRecipes();
        assertEquals(recipeList1, recipeList);
        verify(recipeRepository, times(1)).save(recipe1);
        verify(recipeRepository, times(1)).findAllRecipes();
    }

    @Test
    public void GivenRecipeIdShouldReturnRecipe() {
        recipeRepository.save(recipe1);
        lenient().when(recipeRepository.findById(recipe1.getId())).thenReturn(Optional.of(recipe1));
        assertEquals(recipe1.getId(), 1L);
        verify(recipeRepository, times(1)).save(recipe1);
    }

    @Test
    public void GivenRecipeIdTDeleteRecipe() {

        recipeService.setRecipeRepository(this.recipeRepository);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));
        recipeService.removeRecipe(1L);
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    public void GivenRecipeIdThrowRecipeNotFoundExceptionIfIdNotFoundForDeleteRecipe() {
        RecipeNotFoundException thrown = Assertions.assertThrows(RecipeNotFoundException.class, () -> {
            RecipeService recipeService = new RecipeServiceImpl(recipeRepository);
            exception.expect(RecipeNotFoundException.class);
            recipeService.removeRecipe(1L);
        });
        MatcherAssert.assertThat(thrown.getMessage(), containsString("Recipe not found with ID: "));
    }
}
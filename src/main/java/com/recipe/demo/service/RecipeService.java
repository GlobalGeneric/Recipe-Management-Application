package com.recipe.demo.service;

import com.recipe.demo.model.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(Long recipeId);

    List<Recipe> getAllRecipes();

    Recipe removeRecipe(long recipeId);
}

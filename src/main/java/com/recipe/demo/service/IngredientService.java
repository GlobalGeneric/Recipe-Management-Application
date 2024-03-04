package com.recipe.demo.service;

import com.recipe.demo.model.Ingredient;

import java.util.List;

public interface IngredientService {
    //    void deleteByIdAndRecipe_id(long ingredientId, long recipeId);
    Ingredient getIngredient(long ingredientId);

    List<Ingredient> getAllIngredientOfRecipe(long recipeId);

    Ingredient addNewIngredient(long recipeId, Ingredient ingredient);

    Ingredient removeIngredient(long ingredientId);
}

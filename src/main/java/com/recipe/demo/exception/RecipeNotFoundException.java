package com.recipe.demo.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long recipeId) {
        super("Recipe not found with ID: " + recipeId);
    }
}

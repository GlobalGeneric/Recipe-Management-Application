package com.recipe.demo.exception;

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException(long ingredient) {
        super("Ingredient not found with ID: " + ingredient);
    }
}

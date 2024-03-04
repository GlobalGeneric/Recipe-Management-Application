package com.recipe.demo.service;

import com.recipe.demo.exception.IngredientNotFoundException;
import com.recipe.demo.exception.RecipeNotFoundException;
import com.recipe.demo.model.Ingredient;
import com.recipe.demo.model.Recipe;
import com.recipe.demo.repo.IngredientRepository;
import com.recipe.demo.repo.RecipeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Setter
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

//    @Override
//    public void deleteByIdAndRecipe_id(long ingredientId, long recipeId) {
//        Ingredient ingredient = ingredientRepository.findByIdAndRecipe_Id(ingredientId, recipeId).orElseThrow(
//                () -> new IngredientNotFoundException(ingredientId));
//        ingredientRepository.delete(ingredient);
//    }

    @Override
    public Ingredient getIngredient(long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(
                () -> new IngredientNotFoundException(ingredientId));
    }

    @Override
    public List<Ingredient> getAllIngredientOfRecipe(long recipeId) {
        return ingredientRepository.findAllByRecipeId(recipeId);
    }

    @Transactional
    @Override
    public Ingredient addNewIngredient(long recipeId, Ingredient newIngredient) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(
                () -> new RecipeNotFoundException(recipeId));
        newIngredient.setRecipe(recipe);
        return ingredientRepository.save(newIngredient);
    }

    @Override
    public Ingredient removeIngredient(long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(
                () -> new IngredientNotFoundException(ingredientId));
        ingredientRepository.deleteById(ingredientId);
        return ingredient;
    }
}

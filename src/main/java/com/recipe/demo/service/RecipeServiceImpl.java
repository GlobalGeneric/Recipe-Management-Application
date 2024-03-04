package com.recipe.demo.service;

import com.recipe.demo.exception.RecipeNotFoundException;
import com.recipe.demo.model.Recipe;
import com.recipe.demo.repo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {


    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.setRecipeRepository(recipeRepository);
    }

    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    //    @Autowired
//    public RecipeServiceImpl(RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty())
            recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipe(Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (!optionalRecipe.isPresent())
            throw new RecipeNotFoundException(recipeId);
        else return optionalRecipe.get();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllRecipes();
    }


//    @Transactional
//    @Override
//    public void addNewIngredient(long recipeId, Ingredient newIngredient) {
//        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
//        if (!optionalRecipe.isPresent())
//            throw new RecipeNotFoundException(recipeId);
//        newIngredient.setRecipe(optionalRecipe.get());
//        optionalRecipe.get().getIngredients().add(newIngredient);
//    }

//    @Transactional
//    @Override
//    public void removeIngredientFromRecipe(long recipeId, long ingredientId) {
//
//        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
//        if (!optionalRecipe.isPresent())
//            throw new RecipeNotFoundException(recipeId);
//
//        Ingredient ingredient = optionalRecipe.get().getIngredients().stream()
//                .filter(i -> i.getId() == ingredientId)
//                .findFirst()
//                .orElseThrow(() -> new IngredientNotFoundException(ingredientId));
//        optionalRecipe.get().getIngredients().remove(ingredient);
//    }

    @Override
    public Recipe removeRecipe(long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (!optionalRecipe.isPresent())
            throw new RecipeNotFoundException(recipeId);
        recipeRepository.deleteById(optionalRecipe.get().getId());
        return optionalRecipe.get();
    }
}

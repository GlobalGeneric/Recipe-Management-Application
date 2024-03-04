package com.recipe.demo.controller;

import com.recipe.demo.model.Recipe;
import com.recipe.demo.service.RecipeService;
import com.recipe.demo.tools.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/recipes")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @PostMapping
    public ResponseEntity<ResponseModel> addRecipe(@RequestBody Recipe newRecipe) {
//        recipeService.addRecipe(newRecipe);
        return new ResponseEntity<>(new ResponseModel(false, recipeService.addRecipe(newRecipe)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseModel> findRecipe(@PathVariable("id") long recipeId) {
        return new ResponseEntity<>(new ResponseModel(false, recipeService.getRecipe(recipeId)), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{recipeId}")
    public ResponseEntity<ResponseModel> removeRecipe(@PathVariable("recipeId") long recipeId) {
        recipeService.removeRecipe(recipeId);
        return new ResponseEntity<>(new ResponseModel(false, "recipe with id " + recipeId + " deleted"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseModel> allRecipe() {
        return new ResponseEntity<>(new ResponseModel(false, recipeService.getAllRecipes()), HttpStatus.OK);
    }
}

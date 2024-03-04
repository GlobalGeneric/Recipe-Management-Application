package com.recipe.demo.controller;

import com.recipe.demo.model.Ingredient;
import com.recipe.demo.service.IngredientService;
import com.recipe.demo.tools.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/ingredients")
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseModel> getIngredient(@PathVariable("id") long ingredientId) {
        return new ResponseEntity<>(new ResponseModel(false, ingredientService.getIngredient(ingredientId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> addIngredientToRecipe(@RequestParam("recipeId") long recipeId, @RequestBody Ingredient newIngredient) {
        ingredientService.addNewIngredient(recipeId, newIngredient);
        return new ResponseEntity<>(new ResponseModel(false, "New ingredient added to recipe with id " + recipeId + " successfully"), HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/{ingredientId}")
    public ResponseEntity<ResponseModel> removeIngredientFromRecipe(@PathVariable("ingredientId") long ingredientId) {
        ingredientService.removeIngredient(ingredientId);
        return new ResponseEntity<>(new ResponseModel(false, "ingredient with id " + ingredientId + " deleted"), HttpStatus.OK);
    }

}

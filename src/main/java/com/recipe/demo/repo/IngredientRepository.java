package com.recipe.demo.repo;

import com.recipe.demo.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByRecipeId(Long recipeId);
}

package com.recipe.demo.repo;

import com.recipe.demo.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query(value = "select distinct r from Recipe r order by r.name")
    List<Recipe> findAllRecipes();

}

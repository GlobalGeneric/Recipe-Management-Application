package com.recipe.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.demo.exception.GlobalExceptionHandler;
import com.recipe.demo.exception.RecipeNotFoundException;
import com.recipe.demo.model.Recipe;
import com.recipe.demo.service.RecipeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc2;
    private JacksonTester<Recipe> jsonRecipe;


    @Mock
    private RecipeService recipeService;
    private Recipe recipe;

    @InjectMocks
    private RecipeController recipeController;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        recipe = new Recipe(1L, "name", "description", "instruction", null);
//        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc2 = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() {
        recipe = null;
    }

    @Test
    public void addRecipe() throws Exception {
        when(recipeService.addRecipe(any())).thenReturn(recipe);
        mockMvc2.perform(post("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recipe)))
                .andExpect(status().isCreated());
        verify(recipeService, times(1)).addRecipe(any());

    }

    @Test
    public void findRecipe() throws Exception {
        when(recipeService.getRecipe(recipe.getId())).thenReturn(recipe);

        mockMvc2.perform(get("/api/recipes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRecipe.write(recipe).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findRecipeThatNotAvailable() throws Exception {
        given(recipeService.getRecipe(100L)).willThrow(new RecipeNotFoundException(100L));

        mockMvc2.perform(get("/api/recipes/100").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(recipe)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
        //expected RecipeNotFoundException

    }

    @Test
    public void removeRecipe() throws Exception {
        when(recipeService.removeRecipe(recipe.getId())).thenReturn(recipe);
        mockMvc2.perform(delete("/api/recipes/1").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(recipe)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

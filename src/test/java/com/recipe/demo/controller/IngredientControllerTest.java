package com.recipe.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.demo.exception.GlobalExceptionHandler;
import com.recipe.demo.exception.IngredientNotFoundException;
import com.recipe.demo.model.Ingredient;
import com.recipe.demo.service.IngredientService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<Ingredient> jsonIngredient;

    @Mock
    private IngredientService ingredientService;

    private Ingredient ingredient;

    @InjectMocks
    private IngredientController ingredientController;

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
        ingredient = new Ingredient(1L, "name", "quantity", null);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() {
        ingredient = null;
    }

    @Test
    public void addIngredientMethodTest() throws Exception {
        lenient().when(ingredientService.addNewIngredient(1, ingredient)).thenReturn(ingredient);
        mockMvc.perform(post("/api/ingredients?recipeId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ingredient)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getIngredientMethodTest() throws Exception {
        when(ingredientService.getIngredient(ingredient.getId())).thenReturn(ingredient);
        mockMvc.perform(get("/api/ingredients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIngredient.write(ingredient).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id", is(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getIngredientToHandleIngredientNotFoundExceptionTest() throws Exception {

        lenient().when(ingredientService.getIngredient(ingredient.getId())).thenReturn(ingredient);

        mockMvc.perform(delete("/api/ingredients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIngredient.write(ingredient).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void removeIngredientFrom() throws Exception {
        when(ingredientService.removeIngredient(ingredient.getId())).thenReturn(ingredient);

        mockMvc.perform(delete("/api/ingredients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIngredient.write(ingredient).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hasError", is(false)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void removeIngredientToHandleIngredientNotFoundExceptionTest() throws Exception {
        when(ingredientService.removeIngredient(ingredient.getId())).thenThrow(new IngredientNotFoundException(1));

        mockMvc.perform(delete("/api/ingredients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIngredient.write(ingredient).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hasError", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", is(404)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}

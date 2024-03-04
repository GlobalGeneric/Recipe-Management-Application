package com.recipe.demo.exception;

import com.recipe.demo.tools.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RecipeNotFoundException.class, IngredientNotFoundException.class})
    public ResponseEntity<ResponseModel> handleRecipeNotFoundException(Exception ex) {
        ResponseModel errorResponse = new ResponseModel(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }//

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseModel> handleRecipeNotFoundException2(Exception ex) {
        ResponseModel errorResponse = new ResponseModel(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }
}
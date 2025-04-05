package ru.rotiza.Systems1221NutritionControl.controller.dish;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.dish.Dish;
import ru.rotiza.Systems1221NutritionControl.model.dish.NewDishRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.dish.UpdateDishRequestDTO;
import ru.rotiza.Systems1221NutritionControl.service.dish.DishService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dish")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishController {

    final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("{dishId}")
    public ResponseEntity<?> handleGetDish(@PathVariable Long dishId) {
        Dish response;
        try{
            response = dishService.getDish(dishId);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<?> handleAddNewDish(@Valid @RequestBody NewDishRequestDTO newDishRequest) {
        Dish response = dishService.addDish(newDishRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("{dishId}")
    public ResponseEntity<?> handleUpdateDish(@Valid @RequestBody UpdateDishRequestDTO requestDishDAO, @PathVariable Long dishId) {
        Dish response;
        try{
            response = dishService.updateDish(requestDishDAO, dishId);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{dishId}")
    public ResponseEntity<?> handleDeleteDish(@PathVariable Long dishId) {
        try {
            dishService.deleteDish(dishId);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        return new HashMap<>(){{
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                put(fieldName, errorMessage);
            });
        }};
    }
}

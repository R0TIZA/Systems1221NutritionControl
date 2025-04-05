package ru.rotiza.Systems1221NutritionControl.controller.meal;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;
import ru.rotiza.Systems1221NutritionControl.model.meal.NewMealRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.meal.UpdateMealRequestDTO;
import ru.rotiza.Systems1221NutritionControl.repository.dish.DishRepo;
import ru.rotiza.Systems1221NutritionControl.repository.meal.MealRepo;
import ru.rotiza.Systems1221NutritionControl.repository.user.UserRepo;
import ru.rotiza.Systems1221NutritionControl.service.meal.MealService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/meal")
public class MealController {

    MealRepo mealRepo;
    UserRepo userRepo;
    DishRepo dishRepo;

    MealService mealService;

    @Autowired
    public MealController(MealRepo mealRepo,
                          UserRepo userRepo,
                          DishRepo dishRepo,
                          MealService mealService) {
        this.mealRepo = mealRepo;
        this.userRepo = userRepo;
        this.dishRepo = dishRepo;
        this.mealService = mealService;
    }

    @GetMapping("{mealId}")
    public ResponseEntity<?> handleGetMeal(@PathVariable Long mealId) {
        Meal response;
        try{
            response = mealService.getMeal(mealId);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<?> handleAddNewMeal(@Valid @RequestBody NewMealRequestDTO meal) {
        Meal response = mealService.addMeal(meal);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("{mealId}")
    public ResponseEntity<?> handleUpdateMeal(@Valid @RequestBody UpdateMealRequestDTO meal, @PathVariable Long mealId) {
        Meal response;
        try{
            response = mealService.updateMeal(meal, mealId);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("{mealId}")
    public ResponseEntity<?> handleDeleteMeal(@PathVariable Long mealId) {
        try {
            mealService.deleteMeal(mealId);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
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

package ru.rotiza.Systems1221NutritionControl.controller.meal;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import ru.rotiza.Systems1221NutritionControl.service.MealService;

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
    public Meal getMeal(@PathVariable Long mealId) {
        return mealService.getMeal(mealId);
    }

    @PostMapping
    public Meal addMeal(@Valid @RequestBody NewMealRequestDTO meal) {
        return mealService.addMeal(meal);
    }

    @PutMapping("{mealId}")
    public Meal updateMeal(@Valid @RequestBody UpdateMealRequestDTO meal, @PathVariable Long mealId) {
        return mealService.updateMeal(meal, mealId);
    }

    @DeleteMapping("{mealId}")
    public void deleteMeal(@PathVariable Long mealId) {
        mealService.deleteMeal(mealId);
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

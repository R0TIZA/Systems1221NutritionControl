package ru.rotiza.Systems1221NutritionControl.controller.dish;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.dish.Dish;
import ru.rotiza.Systems1221NutritionControl.model.dish.NewDishRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.dish.UpdateDishRequestDTO;
import ru.rotiza.Systems1221NutritionControl.repository.dish.DishRepo;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dish")
public class DishController {

    DishRepo dishRepo;

    @Autowired
    public DishController(DishRepo dishRepo) {
        this.dishRepo = dishRepo;
    }

    @GetMapping("{id}")
    public Dish getDish(@PathVariable Long id) {
        return dishRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Dish createDish(@Valid @RequestBody NewDishRequestDTO requestDishDAO) {
        Dish newDish = new Dish(requestDishDAO);
        return dishRepo.save(newDish);
    }

    @PutMapping("{id}")
    public Dish updateDish(@Valid @RequestBody UpdateDishRequestDTO requestDishDAO, @PathVariable Long id) {
        Dish dishToUpdate = dishRepo.findById(id).orElseThrow(NotFoundException::new);
        dishToUpdate.updateDish(requestDishDAO);
        return dishRepo.save(dishToUpdate);
    }

    @DeleteMapping("{id}")
    public void deleteDish(@PathVariable Long id) {
        dishRepo.deleteById(id);
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

package ru.rotiza.Systems1221NutritionControl.controller.dish;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.dish.DishDAO;
import ru.rotiza.Systems1221NutritionControl.model.dish.NewDishRequestDAO;
import ru.rotiza.Systems1221NutritionControl.model.dish.UpdateDishRequestDAO;
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
    public DishDAO getDish(@PathVariable Long id) {
        return dishRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public DishDAO createDish(@Valid @RequestBody NewDishRequestDAO requestDishDAO) {
        DishDAO newDish = new DishDAO(requestDishDAO);
        return dishRepo.save(newDish);
    }

    @PutMapping("{id}")
    public DishDAO updateDish(@Valid @RequestBody UpdateDishRequestDAO requestDishDAO, @PathVariable Long id) {
        DishDAO dishToUpdate = dishRepo.findById(id).orElseThrow(NotFoundException::new);
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

package ru.rotiza.Systems1221NutritionControl.model.meal;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.rotiza.Systems1221NutritionControl.model.dish.Dish;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMealRequestDTO {
    @NotNull
    List<Dish> dishes;
}

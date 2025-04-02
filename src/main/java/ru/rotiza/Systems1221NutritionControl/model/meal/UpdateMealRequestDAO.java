package ru.rotiza.Systems1221NutritionControl.model.meal;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.rotiza.Systems1221NutritionControl.model.dish.DishDAO;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMealRequestDAO {
    @NotNull
    List<DishDAO> dishes;
}

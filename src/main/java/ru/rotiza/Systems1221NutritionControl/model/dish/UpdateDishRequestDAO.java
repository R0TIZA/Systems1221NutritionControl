package ru.rotiza.Systems1221NutritionControl.model.dish;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDishRequestDAO {
    @Size(min = 1, max = 255)
    String name;

    @Min(0)
    Double calories;

    @Min(0)
    Double protein;

    @Min(0)
    Double fat;

    @Min(0)
    Double carbohydrate;
}

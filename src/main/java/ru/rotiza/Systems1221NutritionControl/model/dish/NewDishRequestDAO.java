package ru.rotiza.Systems1221NutritionControl.model.dish;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewDishRequestDAO {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    String name;

    @NotNull
    @Min(0)
    Double calories;

    @NotNull
    @Min(0)
    Double protein;

    @NotNull
    @Min(0)
    Double fat;

    @NotNull
    @Min(0)
    Double carbohydrate;
}

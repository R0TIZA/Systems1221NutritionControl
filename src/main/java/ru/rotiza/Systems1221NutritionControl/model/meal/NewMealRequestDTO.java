package ru.rotiza.Systems1221NutritionControl.model.meal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewMealRequestDTO {
    @NotNull
    @Size(min = 1)
    List<Long> dishesId;
}

package ru.rotiza.Systems1221NutritionControl.model.user;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequestDAO {

    @Size(min = 2, max = 255)
    String name;

    Gender gender;

    @Email
    @Size(min = 5, max = 255)
    String email;

    @Min(value = 16)
    @Max(117)
    Integer age;

    @Min(20)
    Integer weight;

    @Min(65)
    @Max(272)
    Integer height;

    Goal goal;
}

package ru.rotiza.Systems1221NutritionControl.model.user;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUserRequestDAO {

    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    String name;

    @NotNull
    Gender gender;

    @NotBlank
    @NotNull
    @Email
    @Size(min = 5, max = 255)
    String email;

    @NotNull
    @Min(value = 16)
    @Max(117)
    Integer age;

    @NotNull
    @Min(20)
    Integer weight;

    @NotNull
    @Min(65)
    @Max(272)
    Integer height;

    @NotNull
    Goal goal;
}

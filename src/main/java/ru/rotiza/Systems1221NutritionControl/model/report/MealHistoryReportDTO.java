package ru.rotiza.Systems1221NutritionControl.model.report;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;
import ru.rotiza.Systems1221NutritionControl.model.user.User;

import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MealHistoryReportDTO {
    final User user;

    final List<Meal> mealList;

    final Double totalCalories;
}

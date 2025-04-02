package ru.rotiza.Systems1221NutritionControl.repository.meal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;

@Repository
public interface MealRepo extends CrudRepository<Meal, Long> {
}

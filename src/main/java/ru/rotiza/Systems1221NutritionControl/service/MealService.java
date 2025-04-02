package ru.rotiza.Systems1221NutritionControl.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.dish.DishDAO;
import ru.rotiza.Systems1221NutritionControl.model.meal.MealDAO;
import ru.rotiza.Systems1221NutritionControl.model.meal.NewMealRequestDAO;
import ru.rotiza.Systems1221NutritionControl.model.user.UserDAO;
import ru.rotiza.Systems1221NutritionControl.repository.dish.DishRepo;
import ru.rotiza.Systems1221NutritionControl.repository.meal.MealRepo;
import ru.rotiza.Systems1221NutritionControl.repository.user.UserRepo;

import java.util.ArrayList;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MealService {

    UserRepo userRepo;
    DishRepo dishRepo;
    MealRepo mealRepo;

    @Autowired
    public MealService(UserRepo userRepo, DishRepo dishRepo, MealRepo mealRepo) {
        this.userRepo = userRepo;
        this.dishRepo = dishRepo;
        this.mealRepo = mealRepo;
    }

    public MealDAO addMeal(Long userId, NewMealRequestDAO newMealRequest) {
        UserDAO currentUser = userRepo.findById(userId).orElseThrow(NotFoundException::new);
        MealDAO newMeal = new MealDAO();

        newMeal.setUser(currentUser);
        newMeal.setDishes(new ArrayList<>(){{
            newMealRequest.getDishesId().forEach(id -> {
                DishDAO dishFromDB = dishRepo.findById(id).orElseThrow(NotFoundException::new);
                add(dishFromDB);
            });
        }});

        return mealRepo.save(newMeal);
    }
}

package ru.rotiza.Systems1221NutritionControl.service.meal;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.dish.Dish;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;
import ru.rotiza.Systems1221NutritionControl.model.meal.NewMealRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.meal.UpdateMealRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.user.User;
import ru.rotiza.Systems1221NutritionControl.repository.dish.DishRepo;
import ru.rotiza.Systems1221NutritionControl.repository.meal.MealRepo;
import ru.rotiza.Systems1221NutritionControl.repository.user.UserRepo;

import java.util.ArrayList;
import java.util.List;

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

    public Meal getMeal(Long id) {
        Meal meal = mealRepo.findByIdAndFetchAll(id);
        if (meal == null) {
            throw new NotFoundException();
        }
        return meal;
    }

    public Meal createMeal(NewMealRequestDTO newMealRequest) {
        Long userId = newMealRequest.getUserId();
        User currentUser = userRepo.findById(userId).orElseThrow(NotFoundException::new);
        Meal newMeal = Meal.builder()
                .user(currentUser)
                .dishes(new ArrayList<>(){{
                    newMealRequest.getDishesId().forEach(id -> {
                        Dish dishFromDB = dishRepo.findById(id).orElseThrow(NotFoundException::new);
                        add(dishFromDB);
                    });
                }})
                .build();

        return mealRepo.save(newMeal);
    }

    public Meal updateMeal(UpdateMealRequestDTO meal, Long mealId) {
        Meal mealFromDB = getMeal(mealId);
        if (meal.getUserId() != null) {
            User user = userRepo.findById(meal.getUserId()).orElseThrow(NotFoundException::new);
            mealFromDB.setUser(user);
        }
        if (meal.getDishesId() != null) {
            List<Dish> dishes = new ArrayList<>(){{
                meal.getDishesId().forEach(dishId -> {
                    Dish dishFromDB = dishRepo.findById(dishId).orElseThrow(NotFoundException::new);
                    add(dishFromDB);
                });
            }};
            mealFromDB.setDishes(dishes);
        }
        return mealRepo.save(mealFromDB);
    }

    public void deleteMeal(Long mealId) {
        mealRepo.delete(mealRepo.findById(mealId).orElseThrow(NotFoundException::new));
    }
}

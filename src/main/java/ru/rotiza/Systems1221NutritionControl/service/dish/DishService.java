package ru.rotiza.Systems1221NutritionControl.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.dish.Dish;
import ru.rotiza.Systems1221NutritionControl.model.dish.NewDishRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.dish.UpdateDishRequestDTO;
import ru.rotiza.Systems1221NutritionControl.repository.dish.DishRepo;

@Service
public class DishService {

    DishRepo dishRepo;

    @Autowired
    public DishService(DishRepo dishRepo) {
        this.dishRepo = dishRepo;
    }

    public Dish getDish(Long id) {
        return dishRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    public Dish addDish(NewDishRequestDTO newDishRequest) {
        String dishName = newDishRequest.getName();
        Double dishCalories = newDishRequest.getCalories();
        Double dishCarbohydrate = newDishRequest.getCarbohydrate();
        Double dishProtein = newDishRequest.getProtein();
        Double dishFat = newDishRequest.getFat();

        return dishRepo.save(Dish.builder()
                .name(dishName)
                .calories(dishCalories)
                .carbohydrate(dishCarbohydrate)
                .protein(dishProtein)
                .fat(dishFat)
                .build());
    }

    public Dish updateDish(UpdateDishRequestDTO updateDish, Long dishId) {
        String name = updateDish.getName();
        Double calories = updateDish.getCalories();
        Double protein = updateDish.getProtein();
        Double fat = updateDish.getFat();
        Double carbohydrate = updateDish.getCarbohydrate();

        Dish dishToUpdate = dishRepo.findById(dishId).orElseThrow(NotFoundException::new);

        if(!name.isEmpty()){
            dishToUpdate.setName(name);
        }
        if(calories != null){
            dishToUpdate.setCalories(calories);
        }
        if(protein != null){
            dishToUpdate.setProtein(protein);
        }
        if(fat != null){
            dishToUpdate.setFat(fat);
        }
        if(carbohydrate != null){
            dishToUpdate.setCarbohydrate(carbohydrate);
        }
        return dishRepo.save(dishToUpdate);
    }

    public void deleteDish(Long dishId) {
        Dish dishToDelete = dishRepo.findById(dishId).orElseThrow(NotFoundException::new);
        dishRepo.delete(dishToDelete);
    }
}

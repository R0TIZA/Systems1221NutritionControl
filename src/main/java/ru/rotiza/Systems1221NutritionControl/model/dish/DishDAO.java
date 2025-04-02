package ru.rotiza.Systems1221NutritionControl.model.dish;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Dish")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Name")
    String name;

    @Column(name = "Calories")
    Double calories;

    @Column(name = "Protein")
    Double protein;

    @Column(name = "Fat")
    Double fat;

    @Column(name = "Carbohydrate")
    Double carbohydrate;

    public DishDAO(NewDishRequestDAO newDish){
        this.name = newDish.getName();
        this.calories = newDish.getCalories();
        this.protein = newDish.getProtein();
        this.fat = newDish.getFat();
        this.carbohydrate = newDish.getCarbohydrate();
    }

    public void updateDish(UpdateDishRequestDAO updateDish){
        String name = updateDish.getName();
        Double calories = updateDish.getCalories();
        Double protein = updateDish.getProtein();
        Double fat = updateDish.getFat();
        Double carbohydrate = updateDish.getCarbohydrate();

        if(!name.isEmpty()){
            this.name = name;
        }
        if(calories != null){
            this.calories = calories;
        }
        if(protein != null){
            this.protein = protein;
        }
        if(fat != null){
            this.fat = fat;
        }
        if(carbohydrate != null){
            this.carbohydrate = carbohydrate;
        }
    }
}

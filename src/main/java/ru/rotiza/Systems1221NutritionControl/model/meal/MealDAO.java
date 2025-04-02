package ru.rotiza.Systems1221NutritionControl.model.meal;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.rotiza.Systems1221NutritionControl.model.dish.DishDAO;
import ru.rotiza.Systems1221NutritionControl.model.user.UserDAO;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "Meal")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MealDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "Id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    UserDAO user;

    @OneToMany
    @Column(name = "Dishes")
    List<DishDAO> dishes;


    @Column(name = "Created")
    LocalDateTime created;

    @Column(name = "Calories")
    Double calories;

    public MealDAO() {
        this.calories = 0.0;
    }

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
        dishes.forEach(dish -> {
            calories +=  dish.getCalories();
        });
    }

    @PreUpdate
    protected void onUpdate() {
        dishes.forEach(dish -> {
            calories +=  dish.getCalories();
        });
    }
}

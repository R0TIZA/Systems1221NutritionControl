package ru.rotiza.Systems1221NutritionControl.model.meal;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.rotiza.Systems1221NutritionControl.model.dish.Dish;
import ru.rotiza.Systems1221NutritionControl.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Meal")
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "Id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "Dishes")
    List<Dish> dishes;


    @Column(name = "Created")
    LocalDateTime created;

    @Column(name = "Calories")
    Double calories;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
        calories = 0.0;
        dishes.forEach(dish -> {
            calories +=  dish.getCalories();
        });
    }

    @PreUpdate
    protected void onUpdate() {
        calories = 0.0;
        dishes.forEach(dish -> {
            calories +=  dish.getCalories();
        });
    }
}

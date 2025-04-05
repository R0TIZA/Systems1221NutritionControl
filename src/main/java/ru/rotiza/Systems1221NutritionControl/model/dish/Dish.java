package ru.rotiza.Systems1221NutritionControl.model.dish;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Dish")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Dish {
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
}

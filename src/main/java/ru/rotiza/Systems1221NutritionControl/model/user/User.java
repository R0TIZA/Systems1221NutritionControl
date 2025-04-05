package ru.rotiza.Systems1221NutritionControl.model.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Users")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Name")
    String name;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "E-mail")
    String email;

    @Column(name = "Age")
    Integer age;

    @Column(name = "Weight")
    Integer weight;

    @Column(name = "Height")
    Integer height;

    @Enumerated(EnumType.STRING)
    Goal goal;

    @Column(name = "BMR")
    Double bmr;

    @PreUpdate
    @PrePersist
    public void calculateBrm() {
        Double maleBmr = 66.47+(13.75*weight)+(5.003*height)+(6.755*age);
        Double femaleBmr = 655.1+(9.563*weight)+(1.85*height)+(4.676*age);
        if(gender == Gender.MALE) {
            bmr = maleBmr;
        } else if(gender == Gender.FEMALE) {
            bmr = femaleBmr;
        } else {
            bmr = (maleBmr+femaleBmr)/2;
        }
    }
}

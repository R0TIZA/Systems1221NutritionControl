package ru.rotiza.Systems1221NutritionControl.model.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "'User'")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "'Id'")
    Long id;

    @Column(name = "'Name'")
    String name;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "'E-mail'")
    String email;

    @Column(name = "'Age'")
    Integer age;

    @Column(name = "'Weight'")
    Integer weight;

    @Column(name = "'Height'")
    Integer height;

    @Enumerated(EnumType.STRING)
    Goal goal;

    @Column(name = "'BMR'")
    Double bmr;

    public User(NewUserRequestDTO user) {
        this.name = user.getName();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.weight = user.getWeight();
        this.height = user.getHeight();
        this.goal = user.getGoal();
    }

    public void updateUser(UpdateUserRequestDTO user) {
        if(user.getName()!=null) {
            this.name = user.getName();
        }
        if(user.getGender()!=null) {
            this.gender = user.getGender();
        }
        if(user.getEmail()!=null) {
            this.email = user.getEmail();
        }
        if(user.getAge()!=null) {
            this.age = user.getAge();
        }
        if(user.getEmail()!=null) {
            this.email = user.getEmail();
        }
        if(user.getWeight()!=null) {
            this.weight = user.getWeight();
        }
        if(user.getHeight()!=null) {
            this.height = user.getHeight();
        }
        if(user.getGoal()!=null) {
            this.goal = user.getGoal();
        }
    }

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

package ru.rotiza.Systems1221NutritionControl.controller.meal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.rotiza.Systems1221NutritionControl.model.dish.Dish;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;
import ru.rotiza.Systems1221NutritionControl.model.user.Gender;
import ru.rotiza.Systems1221NutritionControl.model.user.Goal;
import ru.rotiza.Systems1221NutritionControl.model.user.User;
import ru.rotiza.Systems1221NutritionControl.service.meal.MealService;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MealControllerTest {
    @Mock
    MealService mealService;

    @InjectMocks
    MealController mealController;

    @Test
    void handleAddNewMeal_ReturnsValidResponseEntity() {
        Meal newMeal = Meal.builder()
                .id(1L)
                .user(User.builder()
                        .id(1L)
                        .name("John")
                        .age(20)
                        .email("john@example.com")
                        .gender(Gender.MALE)
                        .height(170)
                        .weight(60)
                        .goal(Goal.WEIGHT_GAIN)
                        .build())
                .dishes(new ArrayList<Dish>(){{
                    add(Dish.builder()
                            .id(1L)
                            .name("Chicken")
                            .calories(300.5)
                            .fat(15.0)
                            .carbohydrate(5.0)
                            .protein(20.0)
                            .build());
                }})
                .created(LocalDateTime.now())
                .build();
        Mockito.doReturn(newMeal).when(this.mealService).addMeal(Mockito.any());

        ResponseEntity responseEntity = mealController.handleAddNewMeal(null);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(newMeal, responseEntity.getBody());
    }
}
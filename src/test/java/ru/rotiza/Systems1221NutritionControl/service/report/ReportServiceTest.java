package ru.rotiza.Systems1221NutritionControl.service.report;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.rotiza.Systems1221NutritionControl.controller.report.ReportController;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;
import ru.rotiza.Systems1221NutritionControl.model.report.MealHistoryReportDTO;
import ru.rotiza.Systems1221NutritionControl.model.user.User;
import ru.rotiza.Systems1221NutritionControl.repository.meal.MealRepo;
import ru.rotiza.Systems1221NutritionControl.repository.user.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    UserRepo userRepo;

    @Mock
    MealRepo mealRepo;

    @InjectMocks
    ReportService reportService;

    @Test
    void verifyBmiCompliance_TotalCaloriesLessThenBmi_ReturnTrue() {
        Optional<User> currentUser = Optional.of(User.builder()
                .bmr(2600.0)
                .build());
        List<Meal> meals = new ArrayList<>(){{
            add(Meal.builder()
                    .calories(600.0)
                    .build());
            add(Meal.builder()
                    .calories(800.0)
                    .build());
        }};
        Mockito.doReturn(currentUser).when(userRepo).findById(Mockito.anyLong());
        Mockito.doReturn(meals).when(mealRepo).findAllByDay(Mockito.any(), Mockito.any(), Mockito.any());

        Boolean result = reportService.verifyBmrCompliance("2025-04-05", 1L);
        assertTrue(result);
    }

    @Test
    void verifyBmiCompliance_TotalCaloriesMoreThenBmi_ReturnFalse() {
        Optional<User> currentUser = Optional.of(User.builder()
                .bmr(2600.0)
                .build());
        List<Meal> meals = new ArrayList<>(){{
            add(Meal.builder()
                    .calories(900.0)
                    .build());
            add(Meal.builder()
                    .calories(800.0)
                    .build());
            add(Meal.builder()
                    .calories(950.0)
                    .build());
        }};
        Mockito.doReturn(currentUser).when(userRepo).findById(Mockito.anyLong());
        Mockito.doReturn(meals).when(mealRepo).findAllByDay(Mockito.any(), Mockito.any(), Mockito.any());

        Boolean result = reportService.verifyBmrCompliance("2025-04-05", 1L);
        assertFalse(result);
    }

    @Test
    void getMealHistoryReport_ReturnValidReport() {
        User currentUser = User.builder()
                .bmr(2600.0)
                .build();

        Optional<User> optionalUser = Optional.of(currentUser);

        List<Meal> meals = new ArrayList<>(){{
            add(Meal.builder()
                    .user(currentUser)
                    .calories(900.0)
                    .build());
            add(Meal.builder()
                    .user(currentUser)
                    .calories(800.0)
                    .build());
            add(Meal.builder()
                    .user(currentUser)
                    .calories(950.0)
                    .build());
        }};

        MealHistoryReportDTO expected = MealHistoryReportDTO.builder()
                .user(currentUser)
                .mealList(
                        new ArrayList<>(){{
                            add(Meal.builder()
                                    .calories(900.0)
                                    .build());
                            add(Meal.builder()
                                    .calories(800.0)
                                    .build());
                            add(Meal.builder()
                                    .calories(950.0)
                                    .build());
                        }}
                )
                .totalCalories(
                        meals.stream().map(Meal::getCalories).reduce(Double::sum).get()
                )
                .build();
        Mockito.doReturn(optionalUser).when(userRepo).findById(Mockito.anyLong());
        Mockito.doReturn(meals).when(mealRepo).findAllByDay(Mockito.any(), Mockito.any(), Mockito.any());

        MealHistoryReportDTO result = reportService.getMealHistoryReport(1L, "2025-04-05");

        assertEquals(expected.getUser().getId(), result.getUser().getId());
        assertEquals(expected.getTotalCalories(), result.getTotalCalories());
    }
}
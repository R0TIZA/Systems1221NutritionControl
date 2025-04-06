package ru.rotiza.Systems1221NutritionControl.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;
import ru.rotiza.Systems1221NutritionControl.model.report.MealHistoryReportDTO;
import ru.rotiza.Systems1221NutritionControl.model.user.User;
import ru.rotiza.Systems1221NutritionControl.repository.meal.MealRepo;
import ru.rotiza.Systems1221NutritionControl.repository.user.UserRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    MealRepo mealRepo;
    UserRepo userRepo;

    @Autowired
    public ReportService(MealRepo mealRepo,
                         UserRepo userRepo) {
        this.mealRepo = mealRepo;
        this.userRepo = userRepo;
    }

    public Boolean verifyBmrCompliance(String date, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(NotFoundException::new);
        LocalDateTime start = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        List<Meal> meals = mealRepo.findAllByDay(userId, start, end);
        Double totalCalories = 0.0;

        for (Meal meal : meals) {
            totalCalories += meal.getCalories();
        }

        if(user.getBmr() >= totalCalories) {
            return true;
        }else {
            return false;
        }
    }

    public MealHistoryReportDTO getMealHistoryReport(Long userId, String date) {
        User user = userRepo.findById(userId).orElseThrow(NotFoundException::new);
        LocalDateTime start = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        List<Meal> dailyMeals = mealRepo.findAllByDay(userId, start, end);
        Double totalCalories = 0.0;

        for (Meal meal : dailyMeals) {
            meal.setUser(null);
            totalCalories += meal.getCalories();
        }

        return MealHistoryReportDTO.builder()
                .user(user)
                .mealList(dailyMeals)
                .totalCalories(totalCalories)
                .build();
    }
}

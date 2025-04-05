package ru.rotiza.Systems1221NutritionControl.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rotiza.Systems1221NutritionControl.exception.InvalidDateException;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.report.MealHistoryReportDTO;
import ru.rotiza.Systems1221NutritionControl.service.report.ReportService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("api/v1/report")
public class ReportController {

    ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("daily/{userId}")
    public ResponseEntity<?> handleGetDailyReport(@PathVariable Long userId) {
        String today = LocalDate.now().toString();
        MealHistoryReportDTO response;
        try {
            response = reportService.getMealHistoryReport(userId, today);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("bmi/{userId}")
    public ResponseEntity<?> handleCheckBmi(@PathVariable Long userId, @RequestParam String date) {
        Boolean response;
        try{
            validateDate(date);
            response = reportService.verifyBmiCompliance(date, userId);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getMessage());
        }catch (InvalidDateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("history/{userId}")
    public ResponseEntity<?> handleGetMealHistoryReport(@PathVariable Long userId, @RequestParam String date) {
        MealHistoryReportDTO response;
        try {
            validateDate(date);
            response = reportService.getMealHistoryReport(userId, date);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getMessage());
        }catch (InvalidDateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    private void validateDate(String dateString) {
        LocalDate date;
        try{
            date = LocalDate.parse(dateString);
        }catch (DateTimeParseException e){
            throw new InvalidDateException("Incorrect date format: date format should be yyyy-mm-dd");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("The date must not be future");
        }
    }
}

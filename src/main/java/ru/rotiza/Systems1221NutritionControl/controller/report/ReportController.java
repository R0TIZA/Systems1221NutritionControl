package ru.rotiza.Systems1221NutritionControl.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.report.MealHistoryReportDTO;
import ru.rotiza.Systems1221NutritionControl.service.report.ReportService;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/report")
public class ReportController {

    ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("daily/{userId}")
    public ResponseEntity<?> getDailyReport(@PathVariable Long userId) {
        String today = LocalDate.now().toString();
        MealHistoryReportDTO response;
        try {
            response = reportService.getMealHistoryReport(userId, today);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("bmi/{userId}")
    public ResponseEntity<?> checkBmi(@PathVariable Long userId, @RequestParam String date) {
        Boolean response;
        try{
            validateDate(date);
            response = reportService.verifyBmiCompliance(date, userId);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("history/{userId}")
    public ResponseEntity<?> getDailyReport(@PathVariable Long userId, @RequestParam String date) {
        MealHistoryReportDTO response;
        try {
            validateDate(date);
            response = reportService.getMealHistoryReport(userId, date);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    private void validateDate(String date) {
        if(LocalDate.parse(date).isAfter(LocalDate.now())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date");
        }
    }
}

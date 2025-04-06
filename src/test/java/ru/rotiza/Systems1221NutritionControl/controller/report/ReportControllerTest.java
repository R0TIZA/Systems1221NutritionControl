package ru.rotiza.Systems1221NutritionControl.controller.report;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.rotiza.Systems1221NutritionControl.exception.InvalidDateException;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;
import ru.rotiza.Systems1221NutritionControl.model.report.MealHistoryReportDTO;
import ru.rotiza.Systems1221NutritionControl.model.user.Gender;
import ru.rotiza.Systems1221NutritionControl.model.user.Goal;
import ru.rotiza.Systems1221NutritionControl.model.user.User;
import ru.rotiza.Systems1221NutritionControl.service.report.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {
    @Mock
    ReportService reportService;

    @InjectMocks
    ReportController reportController;

    @Test
    void handleGetDailyReport_ValidData_ReturnsValidResponseEntity() {
        MealHistoryReportDTO result = MealHistoryReportDTO.builder()
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
                .mealList(new ArrayList<>())
                .totalCalories(0.0)
                .build();
        Mockito.doReturn(result).when(this.reportService).getMealHistoryReport(Mockito.any(), Mockito.any());

        ResponseEntity<?> response = reportController.handleGetDailyReport(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(result, response.getBody());
    }

    @Test
    void handleGetDailyReport_InvalidId_ReturnsValidResponseEntity() {
        String exceptionMessage = "Resource not found";
        Mockito.doThrow(new NotFoundException(exceptionMessage)).when(this.reportService).getMealHistoryReport(Mockito.any(), Mockito.any());

        ResponseEntity<?> response = reportController.handleGetDailyReport(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(exceptionMessage, response.getBody());
    }

    @Test
    void handleCheckBmi_ValidData_ReturnsValidResponseEntity() {
        Boolean result = true;
        Mockito.doReturn(result).when(this.reportService).verifyBmrCompliance(Mockito.any(), Mockito.any());

        ResponseEntity<?> response = reportController.handleCheckBmr(1L, "2025-04-05");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(result, response.getBody());
    }

    @Test
    void handleCheckBmi_InvalidDateFormat_ReturnsValidResponseEntity() {
        String exceptionMessage = "Incorrect date format: date format should be yyyy-mm-dd";

        ResponseEntity<?> response = reportController.handleCheckBmr(1L, "05-2025-04");

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(exceptionMessage, response.getBody());
    }

    @Test
    void handleCheckBmi_DateInFuture_ReturnsValidResponseEntity() {
        String exceptionMessage = "The date must not be future";

        ResponseEntity<?> response = reportController.handleCheckBmr(1L, "2026-04-05");

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(exceptionMessage, response.getBody());
    }

    @Test
    void handleCheckBmi_InvalidUserId_ReturnsValidResponseEntity() {
        String exceptionMessage = "Resource not found";
        Mockito.doThrow(new NotFoundException(exceptionMessage)).when(reportService).verifyBmrCompliance(Mockito.any(), Mockito.any());

        ResponseEntity<?> response = reportController.handleCheckBmr(1L, "2025-04-05");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(exceptionMessage, response.getBody());
    }

    @Test
    void handleGetMealHistoryReport_ValidData_ReturnsValidResponseEntity() {
        MealHistoryReportDTO result = MealHistoryReportDTO.builder()
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
                .mealList(new ArrayList<>())
                .totalCalories(0.0)
                .build();
        Mockito.doReturn(result).when(this.reportService).getMealHistoryReport(Mockito.any(), Mockito.any());

        ResponseEntity<?> response = reportController.handleGetMealHistoryReport(1L, "2025-04-05");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(result, response.getBody());
    }

    @Test
    void handleGetMealHistoryReport_InvalidDateFormat_ReturnsValidResponseEntity() {
        String exceptionMessage = "Incorrect date format: date format should be yyyy-mm-dd";

        ResponseEntity<?> response = reportController.handleGetMealHistoryReport(1L, "05-2025-04");

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(exceptionMessage, response.getBody());
    }

    @Test
    void handleGetMealHistoryReport_DateInFuture_ReturnsValidResponseEntity() {
        String exceptionMessage = "The date must not be future";

        ResponseEntity<?> response = reportController.handleGetMealHistoryReport(1L, "2026-04-05");

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(exceptionMessage, response.getBody());
    }

    @Test
    void handleGetMealHistoryReport_InvalidUserId_ReturnsValidResponseEntity() {
        String exceptionMessage = "Resource not found";
        Mockito.doThrow(new NotFoundException(exceptionMessage)).when(this.reportService).getMealHistoryReport(Mockito.any(), Mockito.any());

        ResponseEntity<?> response = reportController.handleGetMealHistoryReport(1L, "2025-04-05");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(exceptionMessage, response.getBody());
    }
}
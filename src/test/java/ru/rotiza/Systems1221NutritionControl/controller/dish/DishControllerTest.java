package ru.rotiza.Systems1221NutritionControl.controller.dish;

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
import ru.rotiza.Systems1221NutritionControl.service.dish.DishService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DishControllerTest {
    @Mock
    DishService dishService;

    @InjectMocks
    DishController dishController;

    @Test
    void handleAddNewDish_ReturnsValidResponseEntity() {
        Dish newDish = Dish.builder()
                .id(1L)
                .name("Mashed Potato")
                .calories(200.3)
                .fat(2.1)
                .protein(0.5)
                .carbohydrate(3.2)
                .build();
        Mockito.doReturn(newDish).when(this.dishService).addDish(Mockito.any());

        ResponseEntity<?> response = dishController.handleAddNewDish(null);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(newDish, response.getBody());
    }
}
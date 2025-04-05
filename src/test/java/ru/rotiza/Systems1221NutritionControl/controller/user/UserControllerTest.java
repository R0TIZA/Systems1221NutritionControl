package ru.rotiza.Systems1221NutritionControl.controller.user;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.user.Gender;
import ru.rotiza.Systems1221NutritionControl.model.user.Goal;
import ru.rotiza.Systems1221NutritionControl.model.user.NewUserRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.user.User;
import ru.rotiza.Systems1221NutritionControl.service.user.UserService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    void handleAddNewUser_ReturnsValidResponseEntity() {
        User newUser = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .age(20)
                .gender(Gender.MALE)
                .height(170)
                .weight(60)
                .goal(Goal.WEIGHT_GAIN)
                .build();
        Mockito.doReturn(newUser).when(this.userService).addUser(Mockito.any());

        ResponseEntity<?> response = userController.handleAddNewUser(null);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(newUser, response.getBody());
    }
}
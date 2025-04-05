package ru.rotiza.Systems1221NutritionControl.controller.user;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.user.NewUserRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.user.UpdateUserRequestDTO;
import ru.rotiza.Systems1221NutritionControl.model.user.User;
import ru.rotiza.Systems1221NutritionControl.service.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> handleGetAllUsers() {
        List<User> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> handleGetUser(@PathVariable long userId) {
        User response;
        try{
            response = userService.getUser(userId);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<?> handleAddNewUser(@Valid @RequestBody NewUserRequestDTO user) {
        User response = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("{userId}")
    public ResponseEntity<?> handleUpdateUser(@PathVariable Long userId,@Valid @RequestBody UpdateUserRequestDTO user) {
        User response;
        try{
            response = userService.updateUser(user, userId);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> handleDeleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
        }catch (NotFoundException e) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        return new HashMap<>(){{
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                put(fieldName, errorMessage);
            });
        }};
    }
}

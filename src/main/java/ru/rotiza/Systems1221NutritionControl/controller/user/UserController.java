package ru.rotiza.Systems1221NutritionControl.controller.user;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.user.NewUserRequestDAO;
import ru.rotiza.Systems1221NutritionControl.model.user.UpdateUserRequestDAO;
import ru.rotiza.Systems1221NutritionControl.repository.user.UserRepo;
import ru.rotiza.Systems1221NutritionControl.model.user.UserDAO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public Iterable<UserDAO> getUsers() {
        return userRepo.findAll();
    }

    @GetMapping("{id}")
    public UserDAO getUser(@PathVariable long id) {
        return userRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public UserDAO createUser(@Valid @RequestBody NewUserRequestDAO user) {
        UserDAO newUser = new UserDAO(user);
        return userRepo.save(newUser);
    }

    @PutMapping("{id}")
    public UserDAO updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDAO user) {
        UserDAO userToUpdate = userRepo.findById(id).orElseThrow(NotFoundException::new);
        userToUpdate.updateUser(user);
        return userRepo.save(userToUpdate);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepo.delete(userRepo.findById(id).orElseThrow(NotFoundException::new));
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

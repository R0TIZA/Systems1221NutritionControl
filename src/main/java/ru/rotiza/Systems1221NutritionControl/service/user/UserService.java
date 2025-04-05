package ru.rotiza.Systems1221NutritionControl.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rotiza.Systems1221NutritionControl.exception.NotFoundException;
import ru.rotiza.Systems1221NutritionControl.model.user.*;
import ru.rotiza.Systems1221NutritionControl.repository.user.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(){{
            userRepo.findAll().forEach(this::add);
        }};
    }

    public User getUser(Long id) {
        return userRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    public User addUser(NewUserRequestDTO newUserRequest) {
        User newUser = User.builder()
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .gender(newUserRequest.getGender())
                .age(newUserRequest.getAge())
                .weight(newUserRequest.getWeight())
                .height(newUserRequest.getHeight())
                .goal(newUserRequest.getGoal())
                .build();
        return userRepo.save(newUser);
    }

    public User updateUser(UpdateUserRequestDTO updateUserRequest, Long id) {
        User userToUpdate = userRepo.findById(id).orElseThrow(NotFoundException::new);

        String name = userToUpdate.getName();
        Gender gender = userToUpdate.getGender();
        String email = userToUpdate.getEmail();
        Integer age = userToUpdate.getAge();
        Integer weight = userToUpdate.getWeight();
        Integer height = userToUpdate.getHeight();
        Goal goal = userToUpdate.getGoal();

        if(updateUserRequest.getName()!=null) {
            userToUpdate.setName(name);
        }
        if(updateUserRequest.getGender()!=null) {
            userToUpdate.setGender(gender);
        }
        if(updateUserRequest.getEmail()!=null) {
            userToUpdate.setEmail(email);
        }
        if(updateUserRequest.getAge()!=null) {
            userToUpdate.setAge(age);
        }
        if(updateUserRequest.getWeight()!=null) {
            userToUpdate.setWeight(weight);
        }
        if(updateUserRequest.getHeight()!=null) {
            userToUpdate.setHeight(height);
        }
        if(updateUserRequest.getGoal()!=null) {
            userToUpdate.setGoal(goal);
        }

        return userRepo.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        User userToDelete = userRepo.findById(id).orElseThrow(NotFoundException::new);
        userRepo.delete(userToDelete);
    }
}

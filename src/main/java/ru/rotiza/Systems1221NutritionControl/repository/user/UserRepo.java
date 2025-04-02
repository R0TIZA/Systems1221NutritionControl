package ru.rotiza.Systems1221NutritionControl.repository.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rotiza.Systems1221NutritionControl.model.user.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
}

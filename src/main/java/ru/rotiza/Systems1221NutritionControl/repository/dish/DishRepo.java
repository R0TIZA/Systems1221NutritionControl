package ru.rotiza.Systems1221NutritionControl.repository.dish;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rotiza.Systems1221NutritionControl.model.dish.DishDAO;

@Repository
public interface DishRepo extends CrudRepository<DishDAO, Long>, JpaSpecificationExecutor<DishDAO> {
}

package ru.rotiza.Systems1221NutritionControl.repository.meal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rotiza.Systems1221NutritionControl.model.meal.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRepo extends CrudRepository<Meal, Long> {

    @Query("SELECT m " +
            "FROM Meal m " +
            "JOIN FETCH m.user " +
            "JOIN FETCH m.dishes " +
            "WHERE m.id = (:id)" +
            "ORDER BY m.id ASC ")
    public Meal findByIdAndFetchAll(@Param("id") Long id);

    @Query("SELECT m " +
            "FROM Meal m " +
            "JOIN FETCH m.user " +
            "JOIN FETCH m.dishes " +
            "WHERE m.user.id = (:userId) and m.created>=(:start) and m.created<=(:end)" +
            "ORDER BY m.id ASC ")
    public List<Meal> findAllByDay(Long userId, LocalDateTime start, LocalDateTime end);
}

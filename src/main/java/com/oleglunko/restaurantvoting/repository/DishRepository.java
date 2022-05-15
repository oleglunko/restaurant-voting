package com.oleglunko.restaurantvoting.repository;

import com.oleglunko.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id = :id")
    void delete(long id);

    List<Dish> findAllByRestaurantIdOrderByName(long restaurantId);
}
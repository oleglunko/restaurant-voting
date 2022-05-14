package com.oleglunko.restaurantvoting.repository;

import com.oleglunko.restaurantvoting.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.date = :now AND m.restaurant.id = :restaurantId")
    Optional<Menu> findCurrentByRestaurantId(Long restaurantId, LocalDate now);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id = :menuId")
    void delete(long menuId);

    List<Menu> findAllByRestaurantIdOrderByDateDesc(long restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.id = :menuId")
    Optional<Menu> findByIdWithDishes(long menuId);

    boolean existsByRestaurantIdAndDate(long restaurantId, LocalDate date);

    default void checkUnique(long restaurantId, LocalDate date) {
        if (existsByRestaurantIdAndDate(restaurantId, date)) {
            throw new RuntimeException("Restaurant has a menu for this date!");
        }
    }

    ;
}

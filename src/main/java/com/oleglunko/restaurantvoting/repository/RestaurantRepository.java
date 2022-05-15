package com.oleglunko.restaurantvoting.repository;

import com.oleglunko.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByOrderByName();

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id = :id")
    void delete(long id);
}
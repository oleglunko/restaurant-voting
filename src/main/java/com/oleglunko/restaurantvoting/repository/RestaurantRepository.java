package com.oleglunko.restaurantvoting.repository;

import com.oleglunko.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id AND r.user.id = :userId")
    Optional<Restaurant> get(long id, long userId);

    List<Restaurant> findAllOrderByName();

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id = :id")
    void delete(long id);
}

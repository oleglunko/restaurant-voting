package com.oleglunko.restaurantvoting.service;

import com.oleglunko.restaurantvoting.model.Dish;
import com.oleglunko.restaurantvoting.repository.DishRepository;
import com.oleglunko.restaurantvoting.repository.RestaurantRepository;
import com.oleglunko.restaurantvoting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oleglunko.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@RequiredArgsConstructor
@Service
public class DishService {

    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Dish save(Dish dish, long userId, long restaurantId) {
        dish.setCreator(userRepository.getById(userId));
        var restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        dish.setRestaurant(checkNotFoundWithId(restaurant, restaurantId));
        return dishRepository.save(dish);
    }
}

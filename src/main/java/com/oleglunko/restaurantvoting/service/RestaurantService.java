package com.oleglunko.restaurantvoting.service;

import com.oleglunko.restaurantvoting.model.Restaurant;
import com.oleglunko.restaurantvoting.repository.RestaurantRepository;
import com.oleglunko.restaurantvoting.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public Restaurant save(Restaurant restaurant, long userId) {
        restaurant.setCreator(userRepository.getById(userId));
        return restaurantRepository.save(restaurant);
    }
}

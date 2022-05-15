package com.oleglunko.restaurantvoting.service;

import com.oleglunko.restaurantvoting.model.Restaurant;
import com.oleglunko.restaurantvoting.repository.RestaurantRepository;
import com.oleglunko.restaurantvoting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public Restaurant save(Restaurant restaurant, long userId) {
        restaurant.setCreator(userRepository.getById(userId));
        return restaurantRepository.save(restaurant);
    }
}
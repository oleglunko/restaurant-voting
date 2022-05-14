package com.oleglunko.restaurantvoting.service;

import com.oleglunko.restaurantvoting.model.Menu;
import com.oleglunko.restaurantvoting.repository.MenuRepository;
import com.oleglunko.restaurantvoting.repository.RestaurantRepository;
import com.oleglunko.restaurantvoting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oleglunko.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Menu save(Menu menu, long userId, long restaurantId) {
        var restaurant = restaurantRepository.getById(restaurantId);
        menu.setRestaurant(checkNotFoundWithId(restaurant, restaurantId));
        menu.setCreator(userRepository.getById(userId));
        return menuRepository.save(menu);
    }
}

package com.oleglunko.restaurantvoting.util;

import com.oleglunko.restaurantvoting.dto.RestaurantDto;
import com.oleglunko.restaurantvoting.model.Restaurant;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {

    public static List<RestaurantDto> getDtos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(r -> new RestaurantDto(r.getId(), r.getName(), r.getAddress(), r.getOpenedAt(), r.getClosedAt()))
                .collect(Collectors.toList());
    }
}

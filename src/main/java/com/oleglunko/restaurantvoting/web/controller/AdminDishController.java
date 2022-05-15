package com.oleglunko.restaurantvoting.web.controller;

import com.oleglunko.restaurantvoting.model.Dish;
import com.oleglunko.restaurantvoting.repository.DishRepository;
import com.oleglunko.restaurantvoting.service.DishService;
import com.oleglunko.restaurantvoting.web.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.oleglunko.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static com.oleglunko.restaurantvoting.util.ValidationUtil.checkNew;

@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private final DishService dishService;
    private final DishRepository dishRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@AuthenticationPrincipal AuthUser authUser,
                                       @Valid @RequestBody Dish dish,
                                       @PathVariable long restaurantId) {
        long userId = authUser.id();
        log.info("create {} by user {}", dish, userId);
        checkNew(dish);
        var created = dishService.save(dish, userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @Valid @RequestBody Dish dish,
                       @PathVariable long restaurantId,
                       @PathVariable long dishId) {
        long userId = authUser.id();
        log.info("update {} by user {}", dish, userId);
        assureIdConsistent(dish, dishId);
        dishService.save(dish, userId, restaurantId);
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable long dishId) {
        log.info("delete restaurant {} by user {}", dishId, authUser.id());
        dishRepository.delete(dishId);
    }

    @GetMapping
    public List<Dish> getAllByRestaurant(@AuthenticationPrincipal AuthUser authUser, @PathVariable long restaurantId) {
        log.info("get all by restaurant {} by user {}", restaurantId, authUser.id());
        return dishRepository.findAllByRestaurantIdOrderByName(restaurantId);
    }
}
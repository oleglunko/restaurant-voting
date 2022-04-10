package com.oleglunko.restaurantvoting.web.controller;

import com.oleglunko.restaurantvoting.dto.RestaurantDto;
import com.oleglunko.restaurantvoting.model.Restaurant;
import com.oleglunko.restaurantvoting.repository.RestaurantRepository;
import com.oleglunko.restaurantvoting.service.RestaurantService;
import com.oleglunko.restaurantvoting.util.RestaurantUtil;
import com.oleglunko.restaurantvoting.web.AuthUser;
import lombok.AllArgsConstructor;
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

import static com.oleglunko.restaurantvoting.util.ValidatonUtil.assureIdConsistent;
import static com.oleglunko.restaurantvoting.util.ValidatonUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable long id) {
        log.info("get restaurant {} by user {}", id, authUser.id());
        return ResponseEntity.of(restaurantRepository.get(id, authUser.id()));
    }

    @GetMapping
    public List<RestaurantDto> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll by user {}", authUser.id());
        return RestaurantUtil.getDtos(restaurantRepository.findAllOrderByName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable long id) {
        log.info("delete restaurant {} by user {}", id, authUser.id());
        restaurantRepository.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant restaurant) {
        long userId = authUser.id();
        log.info("create {} by user {}", restaurant, userId);
        checkNew(restaurant);
        Restaurant created = restaurantService.save(restaurant, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant restaurant, @PathVariable long id) {
        long userId = authUser.id();
        log.info("update {} by user {}", restaurant, userId);
        assureIdConsistent(restaurant, id);
        restaurantService.save(restaurant, userId);
    }
}

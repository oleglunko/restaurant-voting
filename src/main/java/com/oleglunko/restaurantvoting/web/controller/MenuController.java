package com.oleglunko.restaurantvoting.web.controller;

import com.oleglunko.restaurantvoting.model.Menu;
import com.oleglunko.restaurantvoting.repository.MenuRepository;
import com.oleglunko.restaurantvoting.web.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    static final String REST_URL = "/api/restaurants/{restaurantId}/menus";

    private final MenuRepository menuRepository;

    @GetMapping("/current")
    public ResponseEntity<Menu> getCurrent(@AuthenticationPrincipal AuthUser authUser, @PathVariable long restaurantId) {
        log.info("get current menu of restaurant {} by user {}", restaurantId, authUser.id());
        return ResponseEntity.of(menuRepository.findCurrentByRestaurantId(restaurantId, LocalDate.now()));
    }
}

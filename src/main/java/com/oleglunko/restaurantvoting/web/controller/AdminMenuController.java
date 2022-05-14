package com.oleglunko.restaurantvoting.web.controller;

import com.oleglunko.restaurantvoting.model.Menu;
import com.oleglunko.restaurantvoting.repository.MenuRepository;
import com.oleglunko.restaurantvoting.service.MenuService;
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
import static com.oleglunko.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menus";

    private final MenuService menuService;
    private final MenuRepository menuRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@AuthenticationPrincipal AuthUser authUser,
                                       @Valid @RequestBody Menu menu,
                                       @PathVariable long restaurantId) {
        long userId = authUser.id();
        log.info("create {} by user {}", menu, userId);
        checkNew(menu);
        menuRepository.checkUnique(restaurantId, menu.getDate());
        var created = menuService.save(menu, userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{menuId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @Valid @RequestBody Menu menu,
                       @PathVariable long restaurantId,
                       @PathVariable long menuId) {
        var userId = authUser.id();
        log.info("update {} by user {}", menu, userId);
        assureIdConsistent(menu, menuId);
        menuService.save(menu, userId, restaurantId);
    }

    @DeleteMapping("/{menuId}")
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable long menuId) {
        log.info("delete menu {} by user {}", menuId, authUser.id());
        menuRepository.delete(menuId);
    }

    @GetMapping
    public List<Menu> getAllByRestaurantId(@AuthenticationPrincipal AuthUser authUser, @PathVariable long restaurantId) {
        log.info("get all by restaurant {} by user {}", authUser.id(), restaurantId);
        return menuRepository.findAllByRestaurantIdOrderByDateDesc(restaurantId);
    }

    @GetMapping("/{menuId}")
    public Menu getWithDishes(@AuthenticationPrincipal AuthUser authUser, @PathVariable long menuId) {
        log.info("get menu {} with dishes by user {}", menuId, authUser.id());
        return checkNotFoundWithId(menuRepository.findByIdWithDishes(menuId).orElse(null), menuId);
    }
}

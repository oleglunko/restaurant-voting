package com.oleglunko.restaurantvoting.web.controller;

import com.oleglunko.restaurantvoting.model.Vote;
import com.oleglunko.restaurantvoting.repository.VoteRepository;
import com.oleglunko.restaurantvoting.service.VoteService;
import com.oleglunko.restaurantvoting.web.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.time.LocalDate;

import static com.oleglunko.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static com.oleglunko.restaurantvoting.util.ValidationUtil.checkIsLate;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api/votes";

    private final VoteRepository voteRepository;
    private final VoteService voteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser authUser, @RequestBody long restaurantId) {
        long userId = authUser.id();
        log.info("create vote for restaurant {} by user {}", restaurantId, userId);
        checkIsLate();
        voteRepository.checkExists(userId, LocalDate.now());
        var created = voteService.create(restaurantId, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public Vote getCurrentForUser(@AuthenticationPrincipal AuthUser authUser) {
        long userId = authUser.id();
        log.info("get current vote by user {}", userId);
        return voteRepository.findCurrentByUserId(userId, LocalDate.now());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote, @PathVariable long id) {
        long userId = authUser.id();
        log.info("update {} by user {}", id, userId);
        checkIsLate();
        assureIdConsistent(vote, id);
        voteRepository.checkBelong(id, userId);
        voteService.save(vote, userId);
    }
}

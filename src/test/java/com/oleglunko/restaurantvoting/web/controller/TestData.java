package com.oleglunko.restaurantvoting.web.controller;

import com.oleglunko.restaurantvoting.MatcherFactory;
import com.oleglunko.restaurantvoting.model.Restaurant;
import com.oleglunko.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "creator", "restaurant.creator");

    public static final String USER_MAIL = "user@yandex.ru";
    public static final String USER2_MAIL = "user2@gmail.com";

    public static final Restaurant restaurant = new Restaurant(1L, "Ресторан 1", "Улица Пушкина, д. 32", LocalTime.of(10, 0), LocalTime.of(2, 0));
    public static final Vote userVote1 = new Vote(1L, LocalDate.now(), restaurant);

    public static Vote getNewVote() {
        return new Vote(null, LocalDate.now(), restaurant);
    }
}

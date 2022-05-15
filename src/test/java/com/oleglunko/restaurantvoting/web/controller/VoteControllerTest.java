package com.oleglunko.restaurantvoting.web.controller;

import com.oleglunko.restaurantvoting.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.oleglunko.restaurantvoting.web.controller.TestData.USER2_MAIL;
import static com.oleglunko.restaurantvoting.web.controller.TestData.USER_MAIL;
import static com.oleglunko.restaurantvoting.web.controller.TestData.VOTE_MATCHER;
import static com.oleglunko.restaurantvoting.web.controller.TestData.getNewVote;
import static com.oleglunko.restaurantvoting.web.controller.TestData.userVote1;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getCurrentForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(userVote1));
    }

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("1"));
        var created = VOTE_MATCHER.readFromJson(action);
        var newId = created.getId();
        var newVote = getNewVote();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.findCurrentByUserId(3L, LocalDate.now()), newVote);
    }
}

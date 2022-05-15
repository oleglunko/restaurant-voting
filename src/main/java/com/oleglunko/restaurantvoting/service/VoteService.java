package com.oleglunko.restaurantvoting.service;

import com.oleglunko.restaurantvoting.model.Vote;
import com.oleglunko.restaurantvoting.repository.RestaurantRepository;
import com.oleglunko.restaurantvoting.repository.UserRepository;
import com.oleglunko.restaurantvoting.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.oleglunko.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public Vote create(long restaurantId, long userId) {
        var restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
        var creator = userRepository.getById(userId);
        return voteRepository.save(new Vote(creator, restaurant));
    }

    public void save(Vote vote, long userId) {
        vote.setCreator(userRepository.getById(userId));
        voteRepository.save(vote);
    }
}
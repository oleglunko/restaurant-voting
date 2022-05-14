package com.oleglunko.restaurantvoting.repository;

import com.oleglunko.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT v FROM Vote v WHERE v.creator.id = :userId AND v.createdDate = :now")
    Vote findCurrentByUserId(long userId, LocalDate now);

    default void checkExists(long userId, LocalDate now) {
        if (findCurrentByUserId(userId, now) != null) {
            throw new RuntimeException("User has already voted!");
        }
    }

    ;

    default void checkBelong(long id, long userId) {
        var currentVote = findCurrentByUserId(userId, LocalDate.now());
        if (currentVote != null && currentVote.getId() != id) {
            throw new RuntimeException("Vote id = " + id + "doesn't belong to User id = " + userId);
        }
    }

    ;
}
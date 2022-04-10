package com.oleglunko.restaurantvoting.repository;

import com.oleglunko.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

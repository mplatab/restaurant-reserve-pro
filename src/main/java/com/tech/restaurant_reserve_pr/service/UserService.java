package com.tech.restaurant_reserve_pr.service;

import com.tech.restaurant_reserve_pr.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
}

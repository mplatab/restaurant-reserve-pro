package com.tech.restaurant_reserve_pr.security.services;

public interface TokenBlacklist {
    void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}

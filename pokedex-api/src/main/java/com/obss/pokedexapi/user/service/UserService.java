package com.obss.pokedexapi.user.service;

import com.obss.pokedexapi.user.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
}

package com.obss.pokedexapi.user.service.impl;

import com.obss.pokedexapi.user.model.User;
import com.obss.pokedexapi.user.repository.UserRepository;
import com.obss.pokedexapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

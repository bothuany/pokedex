package com.obss.pokedexapi.user.rules;

import com.obss.pokedexapi.exception.AlreadyExistsException;
import com.obss.pokedexapi.exception.NotExistsException;
import com.obss.pokedexapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserBusinessRules {
    private final UserRepository userRepository;

    public void checkIfEmailExists(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("Email already exists");
        }
    }

    public void checkIfUsernameExists(String username) {
        if (this.userRepository.existsByUsername(username)) {
            throw new AlreadyExistsException("Username already exists");
        }
    }

    public void checkIfUserExists(String id) {
        if (!this.userRepository.existsById(id)) {
            throw new NotExistsException("User does not exist");
        }
    }
}

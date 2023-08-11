package com.obss.pokedexapi.pokemon.rules;

import com.obss.pokedexapi.exception.AlreadyExistsException;
import com.obss.pokedexapi.exception.NotExistsException;
import com.obss.pokedexapi.pokemon.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TypeBusinessRules {
    private final TypeRepository typeRepository;

    public void checkIfTypeExists(String id) {
        if (!this.typeRepository.existsById(id)) {
            throw new NotExistsException("Type does not exist");
        }
    }

    public void checkIfNameExists(String name) {
        if (this.typeRepository.existsByName(name)) {
            throw new AlreadyExistsException("Type already exists");
        }
    }

    public void checkIfNameNotExists(String name) {
        if (!this.typeRepository.existsByName(name)) {
            throw new NotExistsException("Type does not exist");
        }
    }


}

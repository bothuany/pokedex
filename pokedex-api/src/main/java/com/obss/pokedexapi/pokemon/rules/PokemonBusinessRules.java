package com.obss.pokedexapi.pokemon.rules;

import com.obss.pokedexapi.exception.AlreadyExistsException;
import com.obss.pokedexapi.exception.BusinessException;
import com.obss.pokedexapi.exception.NotExistsException;
import com.obss.pokedexapi.pokemon.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PokemonBusinessRules {
    private final PokemonRepository pokemonRepository;

    public void checkIfNameExists(String name) {
        if (this.pokemonRepository.existsByName(name)) {
            throw new AlreadyExistsException("Pokemon already exists");
        }
    }

    public void checkIfPokemonExists(String id) {
        if (!this.pokemonRepository.existsById(id)) {
            throw new NotExistsException("Pokemon does not exist");
        }
    }

    public void checkIfTypesAreSame(String typeId, String typeId2) {
        if (typeId.equals(typeId2)) {
            throw new BusinessException("Given types are same");
        }
    }
}

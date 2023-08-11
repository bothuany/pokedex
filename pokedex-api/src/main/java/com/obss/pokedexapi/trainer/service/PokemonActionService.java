package com.obss.pokedexapi.trainer.service;

import com.obss.pokedexapi.pokemon.model.Pokemon;
import org.springframework.data.domain.Page;

public interface PokemonActionService {
    void catchPokemon(String pokemonId, String trainerId);
    void releasePokemon(String pokemonId, String trainerId);
    void wishPokemon(String pokemonId, String trainerId);
    void dislikePokemon(String pokemonId, String trainerId);
    void catchPokemonByName(String pokemonName, String trainerUsername);
    void releasePokemonByName(String pokemonName, String trainerUsername);
    void wishPokemonByName(String pokemonName, String trainerUsername);
    void dislikePokemonByName(String pokemonName, String trainerUsername);

    Page<Pokemon> getAllPokemonsOfTrainerCatchList(String trainerId, Integer page, Integer size);

    Page<Pokemon> getAllPokemonsOfTrainerWishList(String trainerId, Integer page, Integer size);
}

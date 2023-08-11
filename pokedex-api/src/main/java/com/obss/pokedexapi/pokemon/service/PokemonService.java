package com.obss.pokedexapi.pokemon.service;

import com.obss.pokedexapi.pokemon.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PokemonService {
    Pokemon addPokemon(Pokemon pokemon);

    Page<Pokemon> getAllPokemons(Integer page, Integer size);

    Pokemon getPokemonById(String pokemonId);

    Page<Pokemon> searchPokemonsByNameAndType(String name,String type, Integer page, Integer size);

    Pokemon updatePokemon(String pokemonId, Pokemon pokemon);

    void deletePokemon(String pokemonId);

    Pokemon addPokemon(Pokemon pokemon, String type1Name, String type2Name);

    Pokemon updatePokemon(String pokemonId, Pokemon pokemon, String type1Name, String type2Name, String trainerId);

    Page<Pokemon> searchPokemonsByFilters(String name, String type, Integer page, Integer size,Boolean isAll, boolean isCaught, String sortBy, String sortDirection);


}

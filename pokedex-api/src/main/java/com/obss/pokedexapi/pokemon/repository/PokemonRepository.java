package com.obss.pokedexapi.pokemon.repository;

import com.obss.pokedexapi.pokemon.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String> {

    Optional<Pokemon> findPokemonById(String pokemonId);

    Page<Pokemon> findAllByNameContaining(String name, Pageable pageable);

    boolean existsByName(String name);

    Optional<Pokemon> findPokemonByName(String pokemonName);

    Page<Pokemon> findAllByNameContainingAndType1_NameOrType2_Name(String name, String type, String type1, Pageable pageable);

}

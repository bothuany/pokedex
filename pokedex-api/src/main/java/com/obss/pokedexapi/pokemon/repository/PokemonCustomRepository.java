package com.obss.pokedexapi.pokemon.repository;

import com.obss.pokedexapi.common.sort.SortCriteria;
import com.obss.pokedexapi.pokemon.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PokemonCustomRepository {
    Page<Pokemon> searchPokemonsByFilter(String name, String type, Pageable pageable, Boolean isAll, boolean isCaught, SortCriteria sortCriteria);
}

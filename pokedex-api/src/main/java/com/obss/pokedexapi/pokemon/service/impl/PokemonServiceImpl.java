package com.obss.pokedexapi.pokemon.service.impl;

import com.obss.pokedexapi.image.service.CloudinaryService;
import com.obss.pokedexapi.common.sort.SortCriteria;
import com.obss.pokedexapi.common.sort.SortDirection;
import com.obss.pokedexapi.pokemon.model.Pokemon;
import com.obss.pokedexapi.pokemon.repository.PokemonCustomRepository;
import com.obss.pokedexapi.pokemon.repository.PokemonRepository;
import com.obss.pokedexapi.pokemon.rules.PokemonBusinessRules;
import com.obss.pokedexapi.pokemon.rules.TypeBusinessRules;
import com.obss.pokedexapi.pokemon.service.PokemonService;
import com.obss.pokedexapi.pokemon.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static com.obss.pokedexapi.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;
    private final PokemonCustomRepository pokemonCustomRepository;
    private final PokemonBusinessRules pokemonBusinessRules;
    private final TypeBusinessRules typeBusinessRules;
    private final TypeService typeService;
    private final CloudinaryService cloudinaryService;


    @Override
    public Pokemon addPokemon(Pokemon pokemon) {
        this.pokemonBusinessRules.checkIfNameExists(pokemon.getName());
        this.pokemonBusinessRules.checkIfTypesAreSame(pokemon.getType1().getId(), pokemon.getType2().getId());

        pokemon.setIsCaught(false);
        pokemon.setTrainer(null);

        return this.pokemonRepository.save(pokemon);
    }

    @Override
    public Page<Pokemon> getAllPokemons(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.pokemonRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Pokemon getPokemonById(String pokemonId) {
        return this.pokemonRepository.findPokemonById(pokemonId).orElseThrow();
    }

    @Override
    public Page<Pokemon> searchPokemonsByNameAndType(String name, String type, Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        Pageable pageable = PageRequest.of(page, size);

        if (Objects.isNull(type) || type.isEmpty()) {
            return this.pokemonRepository.findAllByNameContaining(name, pageable);
        }

        return this.pokemonRepository.findAllByNameContainingAndType1_NameOrType2_Name(name, type, type, pageable);
    }

    @Override
    public Pokemon updatePokemon(String pokemonId, Pokemon pokemon) {
        this.pokemonBusinessRules.checkIfPokemonExists(pokemonId);
        this.pokemonBusinessRules.checkIfTypesAreSame(pokemon.getType1().getId(), pokemon.getType2().getId());
        
        Pokemon existingPokemon = this.pokemonRepository.findById(pokemonId).orElseThrow();
        Set<String> nullPropertyNames = getNullPropertyNames(pokemon);

        BeanUtils.copyProperties(pokemon, existingPokemon, nullPropertyNames.toArray(new String[0]));

        this.pokemonRepository.save(existingPokemon);
        return existingPokemon;
    }

    @Override
    public void deletePokemon(String pokemonId) {
        Pokemon pokemon = this.pokemonRepository.findById(pokemonId).orElseThrow();
        cloudinaryService.deleteImage(pokemon.getCloudinaryPublicId());
        this.pokemonRepository.deleteById(pokemonId);
    }

    @Override
    public Pokemon addPokemon(Pokemon pokemon, String type1Name, String type2Name) {
        this.pokemonBusinessRules.checkIfNameExists(pokemon.getName());
        this.typeBusinessRules.checkIfNameNotExists(type1Name);

        if(Objects.nonNull(type2Name) && !type2Name.isEmpty()) {
            this.typeBusinessRules.checkIfNameNotExists(type2Name);
            pokemon.setType2(this.typeService.getTypeByName(type2Name));
        }
        else {
            pokemon.setType2(null);
        }
        pokemon.setIsCaught(false);
        pokemon.setTrainer(null);
        pokemon.setType1(this.typeService.getTypeByName(type1Name));

        return this.pokemonRepository.save(pokemon);
    }

    @Override
    public Pokemon updatePokemon(String pokemonId, Pokemon pokemon, String type1Name, String type2Name, String trainerId) {
        this.pokemonBusinessRules.checkIfPokemonExists(pokemonId);

        Pokemon existingPokemon = this.pokemonRepository.findById(pokemonId).orElseThrow();
        String oldImageId = existingPokemon.getCloudinaryPublicId();

        Set<String> nullPropertyNames = getNullPropertyNames(pokemon);

        BeanUtils.copyProperties(pokemon, existingPokemon, nullPropertyNames.toArray(new String[0]));
        this.typeBusinessRules.checkIfNameNotExists(type1Name);

        if(Objects.nonNull(type2Name) && !type2Name.isEmpty()) {
            this.typeBusinessRules.checkIfNameNotExists(type2Name);
            pokemon.setType2(this.typeService.getTypeByName(type2Name));
        }
        else {
            pokemon.setType2(null);
        }
        existingPokemon.setType1(this.typeService.getTypeByName(type1Name));

        cloudinaryService.updateImage(oldImageId, pokemon.getCloudinaryPublicId());

        this.pokemonRepository.save(existingPokemon);
        return existingPokemon;
    }

    @Override
    public Page<Pokemon> searchPokemonsByFilters(String name, String type, Integer page, Integer size,Boolean isAll, boolean isCaught, String sortBy, String sortDirection) {
        if(Objects.isNull(page) || Objects.isNull(size)){
            page = 0;
            size = Integer.MAX_VALUE;
        }
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.isNull(sortBy) || sortBy.isEmpty()) {
            sortBy = "name";
        }

        SortDirection so;
        if (Objects.nonNull(sortDirection) && sortDirection.equals("ASCENDING")) {
            so = SortDirection.ASCENDING;
        } else {
            so = SortDirection.DESCENDING;
        }
        SortCriteria sortCriteria = new SortCriteria(sortBy, so);
        return this.pokemonCustomRepository.searchPokemonsByFilter(name, type, pageable ,isAll, isCaught,sortCriteria);
    }
}

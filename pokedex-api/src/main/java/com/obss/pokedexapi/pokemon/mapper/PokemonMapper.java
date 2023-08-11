package com.obss.pokedexapi.pokemon.mapper;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.obss.pokedexapi.pokemon.dto.CreatePokemonRequestDto;
import com.obss.pokedexapi.pokemon.dto.PokemonResponseDto;
import com.obss.pokedexapi.pokemon.dto.UpdatePokemonRequestDto;
import com.obss.pokedexapi.pokemon.model.Pokemon;
import com.obss.pokedexapi.trainer.dto.TrainerResponseDto;
import com.obss.pokedexapi.trainer.model.Trainer;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PokemonMapper {
    PokemonMapper INSTANCE = Mappers.getMapper(PokemonMapper.class);

    List<PokemonResponseDto> pokemonToPokemonResponseDto(List<Pokemon> pokemons);



    PokemonResponseDto pokemonToPokemonResponseDto(Pokemon pokemon);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stats", source = "stats")
    @Mapping(target = "description", source = "description")
    Pokemon createPokemonRequestDtoToPokemon(CreatePokemonRequestDto pokemonRequestDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stats", source = "stats")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "isCaught", source = "isCaught")
    Pokemon updatePokemonRequestDtoToPokemon(UpdatePokemonRequestDto pokemonRequestDto);

    default Page<PokemonResponseDto> pokemonToPokemonResponseDto(Page<Pokemon> pokemonPage) {
        List<PokemonResponseDto> responseDtoList = pokemonToPokemonResponseDto(pokemonPage.getContent());
        return new PageImpl<>(responseDtoList, pokemonPage.getPageable(), pokemonPage.getTotalElements());
    }
}

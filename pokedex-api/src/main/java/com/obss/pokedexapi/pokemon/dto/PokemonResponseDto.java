package com.obss.pokedexapi.pokemon.dto;

import com.obss.pokedexapi.pokemon.model.Stats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonResponseDto {
    private String id;
    private String name;
    private double price;
    private Stats stats;
    private String description;
    private Boolean isCaught;
    private TypeDto type1;
    private TypeDto type2;
    private TrainerWithPokemonDto trainer;
    private String cloudinaryPublicId ;
}

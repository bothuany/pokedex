package com.obss.pokedexapi.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerWithPokemonDto {
    private String id;
    private String username;
}

package com.obss.pokedexapi.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonWithTrainerDto {
    private String id;
    private String name;
}

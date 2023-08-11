package com.obss.pokedexapi.trainer.dto;

import com.obss.pokedexapi.pokemon.model.Pokemon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponseDto {
    private String id;
    private String name;
    private String username;
    private String email;
    private List<PokemonWithTrainerDto> catchList;
    private List<PokemonWithTrainerDto> wishList;
    private double balance;
}

package com.obss.pokedexapi.pokemon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto {
    private String id;
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    private String color="#000000";
}

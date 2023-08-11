package com.obss.pokedexapi.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDto {
    private String id;
    private String name;
    private String username;
    private String email;
}

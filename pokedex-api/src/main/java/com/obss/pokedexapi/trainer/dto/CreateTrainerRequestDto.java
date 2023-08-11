package com.obss.pokedexapi.trainer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainerRequestDto {
    @Size(min = 2, max = 30)
    @NotBlank
    private String name;
    @Size(min = 2, max = 30)
    @NotBlank
    private String username;
    @Email
    private String email;
    @Size(min = 2, max = 30)
    @NotBlank
    private String password;
}
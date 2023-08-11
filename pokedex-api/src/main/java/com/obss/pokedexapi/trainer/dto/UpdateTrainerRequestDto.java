package com.obss.pokedexapi.trainer.dto;

import com.obss.pokedexapi.common.Constants;
import com.obss.pokedexapi.pokemon.model.Pokemon;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainerRequestDto {
    @Size(min = 2, max = 30)
    private String name;
    @Size(min = 2, max = 30)
    private String username;
    @Email
    private String email;
    @Size(min = 2, max = 30)
    private String password;
    @Min(0)
    private double balance = Constants.ILLEGAL_BALANCE;
}
package com.obss.pokedexapi.trainer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainerMyselfRequestDto {
    @Size(min = 2, max = 30)
    private String name;
    @Size(min = 2, max = 30)
    private String username;
    @Email
    private String email;
    @Size(min = 2, max = 30)
    private String password;
}
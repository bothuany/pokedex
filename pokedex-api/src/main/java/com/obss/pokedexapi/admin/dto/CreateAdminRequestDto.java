package com.obss.pokedexapi.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminRequestDto {
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
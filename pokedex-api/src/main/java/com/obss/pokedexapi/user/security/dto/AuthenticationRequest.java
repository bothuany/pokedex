package com.obss.pokedexapi.user.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    private String username;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 35)
    String password;
}

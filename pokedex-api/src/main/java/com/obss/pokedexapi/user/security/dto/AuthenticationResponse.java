package com.obss.pokedexapi.user.security.dto;

import com.obss.pokedexapi.user.security.UserAuthDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
   // private UserAuthDetails user;
}
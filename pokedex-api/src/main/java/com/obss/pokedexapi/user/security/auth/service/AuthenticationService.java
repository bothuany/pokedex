package com.obss.pokedexapi.user.security.auth.service;

import com.obss.pokedexapi.user.repository.UserRepository;
import com.obss.pokedexapi.user.security.UserAuthDetails;
import com.obss.pokedexapi.user.security.auth.service.JwtService;
import com.obss.pokedexapi.user.security.dto.AuthenticationRequest;
import com.obss.pokedexapi.user.security.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserAuthDetails user = new UserAuthDetails(repository.findByUsername(request.getUsername()).orElseThrow());
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public UserAuthDetails activeUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserAuthDetails) authentication.getPrincipal();
    }
}
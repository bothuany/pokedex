package com.obss.pokedexapi.user.controller;

import com.obss.pokedexapi.user.dto.UserDto;
import com.obss.pokedexapi.user.mapper.UserMapper;
import com.obss.pokedexapi.user.security.auth.service.UserAuthService;
import com.obss.pokedexapi.user.security.dto.AuthenticationRequest;
import com.obss.pokedexapi.user.security.dto.AuthenticationResponse;
import com.obss.pokedexapi.user.security.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserAuthService userAuthService;
    private final AuthenticationService authenticationService;

    @GetMapping("/active")
    @Operation(summary = "Get active user")
    public ResponseEntity<UserDto> activeUser(){
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserDto(userAuthService.getActiveUser()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}

package com.obss.pokedexapi.user.security.auth.service;

import com.obss.pokedexapi.user.security.UserAuthDetails;
import com.obss.pokedexapi.user.model.User;
import com.obss.pokedexapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserAuthDetails(user);
    }

    public UserAuthDetails getActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserAuthDetails) authentication.getPrincipal();
    }

}
package com.obss.pokedexapi.user.service;

import com.obss.pokedexapi.user.model.Role;

import java.util.List;

public interface RoleService {
    void checkAndCreateRoles(List<String> roleNames);
    Role findRoleByName(String name);
}

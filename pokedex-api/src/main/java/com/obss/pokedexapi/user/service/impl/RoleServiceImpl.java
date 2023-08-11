package com.obss.pokedexapi.user.service.impl;

import com.obss.pokedexapi.user.model.Role;
import com.obss.pokedexapi.user.repository.RoleRepository;
import com.obss.pokedexapi.user.service.RoleService;
import com.obss.pokedexapi.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void checkAndCreateRoles(List<String> roleNames) {
        if (CollectionUtils.isEmpty(roleNames)) {
            return;
        }

        roleNames.forEach(roleName -> {
            roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
        });
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow();
    }
}

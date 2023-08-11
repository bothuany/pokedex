package com.obss.pokedexapi.common;

import com.obss.pokedexapi.admin.service.AdminService;
import com.obss.pokedexapi.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final RoleService roleService;
    private final AdminService adminService;

    @Override
    public void run(ApplicationArguments args) {
        roleService.checkAndCreateRoles(List.of(Constants.Roles.ADMIN, Constants.Roles.TRAINER));
        adminService.checkAndCreateAdminUser();
    }
}

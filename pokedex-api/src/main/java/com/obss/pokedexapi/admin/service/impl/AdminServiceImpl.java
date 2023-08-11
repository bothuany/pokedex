package com.obss.pokedexapi.admin.service.impl;

import com.obss.pokedexapi.admin.model.Admin;
import com.obss.pokedexapi.admin.repository.AdminRepository;
import com.obss.pokedexapi.admin.service.AdminService;
import com.obss.pokedexapi.common.Constants;
import com.obss.pokedexapi.user.model.Role;
import com.obss.pokedexapi.user.rules.UserBusinessRules;
import com.obss.pokedexapi.user.security.UserAuthDetails;
import com.obss.pokedexapi.user.security.auth.service.JwtService;
import com.obss.pokedexapi.user.security.auth.service.UserAuthService;
import com.obss.pokedexapi.user.security.dto.AuthenticationResponse;
import com.obss.pokedexapi.user.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.obss.pokedexapi.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final RoleService roleService;
    private final UserBusinessRules userBusinessRules;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserAuthService userAuthService;

    @Transactional
    @Override
    public void checkAndCreateAdminUser() {
        this.adminRepository.findAdminByUsername("sysadmin").orElseGet(() -> {
            Role adminRole = roleService.findRoleByName(Constants.Roles.ADMIN);

            final Admin admin = Admin.builder()
                    .name("sysadmin")
                    .username("sysadmin")
                    .password("sysadmin")
                    .email("sysadmin@mail.com")
                    .role(adminRole)
                    .build();

            adminRole.getUsers().add(admin);
            addAdmin(admin);
            return admin;
        });
    }

    @Override
    public AuthenticationResponse addAdmin(Admin admin) {
        this.userBusinessRules.checkIfEmailExists(admin.getEmail());
        this.userBusinessRules.checkIfUsernameExists(admin.getUsername());

        Role adminRole = roleService.findRoleByName(Constants.Roles.ADMIN);

        admin.setRole(adminRole);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        this.adminRepository.save(admin);
        UserAuthDetails userAuthDetails = new UserAuthDetails(admin);
        var jwtToken = jwtService.generateToken(userAuthDetails);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public Page<Admin> getAllAdmins(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        return this.adminRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Admin getAdminById(String adminId) {
        return this.adminRepository.findAdminById(adminId).orElseThrow();
    }

    @Override
    public Admin getAdminByUsername(String adminUsername) {
        return this.adminRepository.findAdminByUsername(adminUsername).orElseThrow();
    }

    @Override
    public Page<Admin> searchAdminsByUsername(String username, Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        return this.adminRepository.findAllByUsernameContaining(username, PageRequest.of(page, size));
    }

    @Override
    public void updateAdmin(String adminId, Admin admin) {
        this.userBusinessRules.checkIfUserExists(adminId);

        Admin existingAdmin = this.adminRepository.findById(adminId).orElseThrow();
        Set<String> nullPropertyNames = getNullPropertyNames(admin);

        BeanUtils.copyProperties(admin, existingAdmin, nullPropertyNames.toArray(new String[0]));

        if (Objects.nonNull(admin.getPassword())) {
            existingAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }

        this.adminRepository.save(existingAdmin);
    }

    @Override
    @Transactional
    public void updateAdminByUsername(String adminUsername, Admin admin) {
        String adminId = this.adminRepository.findAdminByUsername(adminUsername).orElseThrow().getId();

        updateAdmin(adminId, admin);
    }

    @Override
    @Transactional
    public void deleteAdmin(String adminId) {
        this.adminRepository.deleteById(adminId);
    }

    @Override
    public void deleteAdminByUsername(String adminUsername) {
        this.adminRepository.deleteAdminByUsername(adminUsername);
    }

    @Override
    public void deleteYourselfAsAdmin() {
        String adminId = this.userAuthService.getActiveUser().getId();
        this.deleteAdmin(adminId);
    }

    @Override
    public void updateYourselfAsAdmin(Admin admin) {
        String adminId = this.userAuthService.getActiveUser().getId();
        this.updateAdmin(adminId, admin);
    }
}

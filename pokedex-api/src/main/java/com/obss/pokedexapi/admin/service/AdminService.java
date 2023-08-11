package com.obss.pokedexapi.admin.service;

import com.obss.pokedexapi.admin.model.Admin;
import com.obss.pokedexapi.user.security.dto.AuthenticationResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    void checkAndCreateAdminUser();

    AuthenticationResponse addAdmin(Admin admin);

    Page<Admin> getAllAdmins(Integer page, Integer size);

    Admin getAdminById(String adminId);
    Admin getAdminByUsername(String adminUsername);

    Page<Admin> searchAdminsByUsername(String username, Integer page, Integer size);

    void updateAdmin(String adminId, Admin admin);
    void updateAdminByUsername(String adminUsername, Admin admin);

    void deleteAdmin(String adminId);
    void deleteAdminByUsername(String adminUsername);

    void deleteYourselfAsAdmin();

    void updateYourselfAsAdmin(Admin admin);
}

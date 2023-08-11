package com.obss.pokedexapi.admin.repository;

import com.obss.pokedexapi.admin.model.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findAdminByUsername(String username);

    Optional<Admin> findAdminById(String adminId);

    Page<Admin> findAllByUsernameContaining(String username, PageRequest pageRequest);


    void deleteAdminByUsername(String adminUsername);
}

package com.obss.pokedexapi.pokemon.repository;

import com.obss.pokedexapi.pokemon.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, String> {
    boolean existsByName(String name);

    Optional<Type> findTypeById(String typeId);

    Page<Type> findAllByNameContaining(String name, PageRequest pageRequest);

    Optional<Type> findTypeByName(String name);

}

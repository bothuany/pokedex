package com.obss.pokedexapi.pokemon.service;

import com.obss.pokedexapi.pokemon.model.Type;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TypeService {
    Type addType(Type type);

    Page<Type> getAllTypes(Integer page, Integer size);

    Type getTypeById(String typeId);
    Type getTypeByName(String name);

    Page<Type> searchTypesByName(String name, Integer page, Integer size);

    Type updateType(String typeId, Type type);

    void deleteType(String typeId);

    Type updateTypeByName(String typeName, Type type);

    void deleteTypeByName(String typeName);
}

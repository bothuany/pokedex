package com.obss.pokedexapi.pokemon.service.impl;

import com.obss.pokedexapi.pokemon.model.Pokemon;
import com.obss.pokedexapi.pokemon.model.Type;
import com.obss.pokedexapi.pokemon.repository.TypeRepository;
import com.obss.pokedexapi.pokemon.rules.TypeBusinessRules;
import com.obss.pokedexapi.pokemon.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final TypeBusinessRules typeBusinessRules;

    @Override
    public Type addType(Type type) {
        this.typeBusinessRules.checkIfNameExists(type.getName());
        return this.typeRepository.save(type);
    }

    @Override
    public Page<Type> getAllTypes(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.typeRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Type getTypeById(String typeId) {
        return this.typeRepository.findTypeById(typeId).orElseThrow();
    }

    @Override
    public Type getTypeByName(String name) {
        return this.typeRepository.findTypeByName(name).orElseThrow();
    }

    @Override
    public Page<Type> searchTypesByName(String name, Integer page, Integer size) {
       boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

         if (!isPageable) {
              page = 0;
             size = Integer.MAX_VALUE;
         }

        return this.typeRepository.findAllByNameContaining(name, PageRequest.of(page, size));
    }

    @Override
    public Type updateType(String typeId, Type type) {
        Type typeToUpdate = this.typeRepository.findTypeById(typeId).orElseThrow();
        typeToUpdate.setName(type.getName());
        return this.typeRepository.save(typeToUpdate);
    }


    @Override
    public void deleteType(String typeId) {
        Type type = this.typeRepository.findTypeById(typeId).orElseThrow();

        type.getPokemons1().forEach(pokemon -> pokemon.setType1(null));
        type.getPokemons2().forEach(pokemon -> pokemon.setType2(null));

        this.typeRepository.deleteById(typeId);
    }

    @Override
    public Type updateTypeByName(String typeName, Type type) {
        Type typeToUpdate = this.typeRepository.findTypeByName(typeName).orElseThrow();
        typeToUpdate.setName(type.getName());
        return this.typeRepository.save(typeToUpdate);
    }

    @Override
    public void deleteTypeByName(String typeName) {
        deleteType(this.typeRepository.findTypeByName(typeName).orElseThrow().getId());
    }
}

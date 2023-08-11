package com.obss.pokedexapi.pokemon.mapper;

import com.obss.pokedexapi.pokemon.dto.TypeDto;
import com.obss.pokedexapi.pokemon.model.Type;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TypeMapper {
    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    List<TypeDto> typeToTypeDto(List<Type> types);

    TypeDto typeToTypeDto(Type type);

    Type typeDtoToType(TypeDto typeDto);

    default Page<TypeDto> typeToTypeDto(Page<Type> typePage) {
        List<TypeDto> responseDtoList = typeToTypeDto(typePage.getContent());
        return new PageImpl<>(responseDtoList, typePage.getPageable(), typePage.getTotalElements());
    }
}

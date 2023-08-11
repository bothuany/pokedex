package com.obss.pokedexapi.user.mapper;

import com.obss.pokedexapi.user.dto.UserDto;
import com.obss.pokedexapi.user.security.UserAuthDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "mapAuthorities")
    UserDto userToUserDto(UserAuthDetails userAuthDetails);

    @Named("mapAuthorities")
    List<GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities);

}


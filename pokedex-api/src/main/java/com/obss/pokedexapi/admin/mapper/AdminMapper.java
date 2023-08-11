package com.obss.pokedexapi.admin.mapper;

import com.obss.pokedexapi.admin.dto.AdminResponseDto;
import com.obss.pokedexapi.admin.dto.CreateAdminRequestDto;
import com.obss.pokedexapi.admin.dto.UpdateAdminRequestDto;
import com.obss.pokedexapi.admin.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);
    List<AdminResponseDto> adminToAdminResponseDto(List<Admin> admins);

    AdminResponseDto adminToAdminResponseDto(Admin admin);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    Admin createAdminRequestDtoToAdmin(CreateAdminRequestDto adminRequestDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    Admin updateAdminRequestDtoToAdmin(UpdateAdminRequestDto adminRequestDto);

    default Page<AdminResponseDto> adminToAdminResponseDto(Page<Admin> adminPage) {
        List<AdminResponseDto> responseDtoList = adminToAdminResponseDto(adminPage.getContent());
        return new PageImpl<>(responseDtoList, adminPage.getPageable(), adminPage.getTotalElements());
    }
}

package com.obss.pokedexapi.trainer.mapper;

import com.obss.pokedexapi.trainer.dto.TrainerResponseDto;
import com.obss.pokedexapi.trainer.dto.CreateTrainerRequestDto;
import com.obss.pokedexapi.trainer.dto.UpdateTrainerMyselfRequestDto;
import com.obss.pokedexapi.trainer.dto.UpdateTrainerRequestDto;
import com.obss.pokedexapi.trainer.model.Trainer;
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
public interface TrainerMapper {
    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);
    List<TrainerResponseDto> trainerToTrainerResponseDto(List<Trainer> trainers);

    TrainerResponseDto trainerToTrainerResponseDto(Trainer trainer);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    Trainer createTrainerRequestDtoToTrainer(CreateTrainerRequestDto trainerRequestDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "balance", source = "balance")
    Trainer updateTrainerRequestDtoToTrainer(UpdateTrainerRequestDto trainerRequestDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    Trainer updateTrainerMyselfRequestDtoToTrainer(UpdateTrainerMyselfRequestDto trainerMyselfRequestDto);

    default Page<TrainerResponseDto> trainerToTrainerResponseDto(Page<Trainer> trainerPage) {
        List<TrainerResponseDto> responseDtoList = trainerToTrainerResponseDto(trainerPage.getContent());
        return new PageImpl<>(responseDtoList, trainerPage.getPageable(), trainerPage.getTotalElements());
    }
}

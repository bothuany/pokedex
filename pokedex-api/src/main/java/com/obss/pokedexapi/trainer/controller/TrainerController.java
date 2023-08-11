package com.obss.pokedexapi.trainer.controller;

import com.obss.pokedexapi.pokemon.dto.PokemonResponseDto;
import com.obss.pokedexapi.pokemon.mapper.PokemonMapper;
import com.obss.pokedexapi.trainer.dto.CreateTrainerRequestDto;
import com.obss.pokedexapi.trainer.dto.TrainerResponseDto;
import com.obss.pokedexapi.trainer.dto.UpdateTrainerMyselfRequestDto;
import com.obss.pokedexapi.trainer.dto.UpdateTrainerRequestDto;
import com.obss.pokedexapi.trainer.mapper.TrainerMapper;
import com.obss.pokedexapi.trainer.model.Trainer;
import com.obss.pokedexapi.trainer.service.PokemonActionService;
import com.obss.pokedexapi.trainer.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/trainers")
@RestController
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    private final PokemonActionService pokemonActionService;

    @GetMapping
    @Operation(summary = "Get all trainers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<TrainerResponseDto>> getAllTrainers(@RequestParam(name = "page", required = false) Integer page,
                                                                   @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(TrainerMapper.INSTANCE.trainerToTrainerResponseDto(trainerService.getAllTrainers(page,size)), HttpStatus.OK);
    }

    @GetMapping("/username/{trainerUsername}")
    @Operation(summary = "Get trainer by username")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #trainerUsername")
    public ResponseEntity<TrainerResponseDto> getTrainerByUsername(@PathVariable("trainerUsername") String trainerUsername){
        return new ResponseEntity<>(TrainerMapper.INSTANCE.trainerToTrainerResponseDto(trainerService.getTrainerByUsername(trainerUsername)), HttpStatus.OK);
    }
    @GetMapping("/{trainerId}")
    @Operation(summary = "Get trainer by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TrainerResponseDto> getTrainerById(@PathVariable("trainerId") String trainerId){
        return new ResponseEntity<>(TrainerMapper.INSTANCE.trainerToTrainerResponseDto(trainerService.getTrainerById(trainerId)), HttpStatus.OK);
    }


    @GetMapping("/search")
    @Operation(summary = "Search trainer by username")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<TrainerResponseDto>> searchTrainerByUsername(@RequestParam(name = "username") String username,
                                                                        @RequestParam(name = "page", required = false) Integer page,
                                                                        @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(TrainerMapper.INSTANCE.trainerToTrainerResponseDto(trainerService.searchTrainersByUsername(username,page,size)), HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Add new trainer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreateTrainerRequestDto> addTrainer(@RequestBody @Valid CreateTrainerRequestDto addTrainerRequest){
        Trainer trainer = TrainerMapper.INSTANCE.createTrainerRequestDtoToTrainer(addTrainerRequest);
        trainerService.addTrainer(trainer);
        return new ResponseEntity<>(addTrainerRequest, HttpStatus.OK);
    }


    @PutMapping("/{trainerUsername}")
    @Operation(summary = "Update trainer as admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UpdateTrainerRequestDto> updateTrainer(@PathVariable(name = "trainerUsername") String trainerUsername,
                                                                 @RequestBody @Valid UpdateTrainerRequestDto updateTrainerRequest){
        Trainer trainer = TrainerMapper.INSTANCE.updateTrainerRequestDtoToTrainer(updateTrainerRequest);
        trainerService.updateTrainerByUsername(trainerUsername,trainer);
        return new ResponseEntity<>(updateTrainerRequest, HttpStatus.OK);
    }

    @PutMapping()
    @Operation(summary = "Update yourself as trainer")
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public ResponseEntity<UpdateTrainerMyselfRequestDto> updateTrainerMyself(@RequestBody @Valid UpdateTrainerMyselfRequestDto updateTrainerMyselfRequestDto){
        Trainer trainer = TrainerMapper.INSTANCE.updateTrainerMyselfRequestDtoToTrainer(updateTrainerMyselfRequestDto);
        trainerService.updateTrainerMyself(trainer);
        return new ResponseEntity<>(updateTrainerMyselfRequestDto, HttpStatus.OK);
    }


    @DeleteMapping("/{trainerUsername}")
    @Operation(summary = "Delete trainer")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #trainerUsername")
    public ResponseEntity<HttpStatus> deleteTrainer(@PathVariable(name = "trainerUsername") String trainerUsername){
        trainerService.deleteTrainerByUsername(trainerUsername);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    @Operation(summary = "Delete yourself as trainer")
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public ResponseEntity<HttpStatus> deleteTrainerMyself(){
        trainerService.deleteTrainerMyself();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{trainerUsername}/catch-pokemon/{pokemonName}")
    @Operation(summary = "Catch pokemon")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #trainerUsername")
    public ResponseEntity<HttpStatus> catchPokemon(@PathVariable(name = "trainerUsername") String trainerUsername,
                                                   @PathVariable(name = "pokemonName") String pokemonName){
        pokemonActionService.catchPokemonByName(pokemonName,trainerUsername);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{trainerUsername}/release-pokemon/{pokemonName}")
    @Operation(summary = "Release pokemon")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #trainerUsername")
    public ResponseEntity<HttpStatus> releasePokemon(@PathVariable(name = "trainerUsername") String trainerUsername,
                                                     @PathVariable(name = "pokemonName") String pokemonName){
        pokemonActionService.releasePokemonByName(pokemonName,trainerUsername);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{trainerUsername}/wish-pokemon/{pokemonName}")
    @Operation(summary = "Wish pokemon")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #trainerUsername")
    public ResponseEntity<HttpStatus> wishPokemon(@PathVariable(name = "trainerUsername") String trainerUsername,
                                                  @PathVariable(name = "pokemonName") String pokemonName){
        pokemonActionService.wishPokemonByName(pokemonName,trainerUsername);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{trainerUsername}/dislike-pokemon/{pokemonName}")
    @Operation(summary = "Dislike pokemon")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #trainerUsername")
    public ResponseEntity<HttpStatus> dislikePokemon(@PathVariable(name = "trainerUsername") String trainerUsername,
                                                     @PathVariable(name = "pokemonName") String pokemonName){
        pokemonActionService.dislikePokemonByName(pokemonName,trainerUsername);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{trainerId}/catch-list")
    @Operation(summary = "Get all pokemons of a trainers catch list")
    public ResponseEntity<Page<PokemonResponseDto>> getAllPokemonsOfTrainer(@PathVariable("trainerId") String trainerId,
                                                                            @RequestParam(name = "page", required = false) Integer page,
                                                                            @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(PokemonMapper.INSTANCE.pokemonToPokemonResponseDto(pokemonActionService.getAllPokemonsOfTrainerCatchList(trainerId,page,size)), HttpStatus.OK);
    }

    @GetMapping("/{trainerId}/wish-list")
    @Operation(summary = "Get all pokemons of a trainers wish list")
    public ResponseEntity<Page<PokemonResponseDto>> getAllPokemonsOfTrainerWishList(@PathVariable("trainerId") String trainerId,
                                                                                    @RequestParam(name = "page", required = false) Integer page,
                                                                                    @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(PokemonMapper.INSTANCE.pokemonToPokemonResponseDto(pokemonActionService.getAllPokemonsOfTrainerWishList(trainerId,page,size)), HttpStatus.OK);
    }
}

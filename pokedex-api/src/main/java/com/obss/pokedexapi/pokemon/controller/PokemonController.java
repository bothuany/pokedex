package com.obss.pokedexapi.pokemon.controller;

import com.obss.pokedexapi.pokemon.dto.CreatePokemonRequestDto;
import com.obss.pokedexapi.pokemon.dto.PokemonResponseDto;
import com.obss.pokedexapi.pokemon.dto.UpdatePokemonRequestDto;
import com.obss.pokedexapi.pokemon.mapper.PokemonMapper;
import com.obss.pokedexapi.pokemon.model.Pokemon;
import com.obss.pokedexapi.pokemon.service.PokemonService;
import com.obss.pokedexapi.trainer.service.PokemonActionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/pokemons")
@RestController
@RequiredArgsConstructor
public class PokemonController {
    private final PokemonService pokemonService;

    @GetMapping
    @Operation(summary = "Get all pokemons")
    public ResponseEntity<Page<PokemonResponseDto>> getAllPokemons(@RequestParam(name = "page", required = false) Integer page,
                                                                   @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(PokemonMapper.INSTANCE.pokemonToPokemonResponseDto(pokemonService.getAllPokemons(page,size)), HttpStatus.OK);
    }

    @GetMapping("/{pokemonId}")
    @Operation(summary = "Get pokemon by id")
    public ResponseEntity<PokemonResponseDto> getPokemonById(@PathVariable("pokemonId") String pokemonId){
        return new ResponseEntity<>(PokemonMapper.INSTANCE.pokemonToPokemonResponseDto(pokemonService.getPokemonById(pokemonId)), HttpStatus.OK);
    }

    /*
    @GetMapping("/search")
    @Operation(summary = "Search pokemon by name")
    public ResponseEntity<Page<PokemonResponseDto>> searchPokemonByName(@RequestParam(name = "name") String name,
                                                                        @RequestParam(name = "type", required = false) String type,
                                                                            @RequestParam(name = "page", required = false) Integer page,
                                                                            @RequestParam(name = "size", required = false) Integer size
    ){
        return new ResponseEntity<>(PokemonMapper.INSTANCE.pokemonToPokemonResponseDto(pokemonService.searchPokemonsByNameAndType(name,type,page,size)), HttpStatus.OK);
    }*/
    @GetMapping("/search")
    @Operation(summary = "Search pokemon filters")
    public ResponseEntity<Page<PokemonResponseDto>> searchTestPokemonByName(@RequestParam(name = "name") String name,
                                                                            @RequestParam(name = "type", required = false) String type,
                                                                            @RequestParam(name = "page", required = false) Integer page,
                                                                            @RequestParam(name = "size", required = false) Integer size,
                                                                            @RequestParam(name = "isAll", required = false) Boolean isAll,
                                                                            @RequestParam(name = "isCaught", required = false) boolean isCaught,
                                                                            @RequestParam(name = "sortBy", required = false) String sortBy,
                                                                            @RequestParam(name = "sortDirection", required = false) String sortDirection
    ){
        return new ResponseEntity<>(PokemonMapper.INSTANCE.pokemonToPokemonResponseDto(pokemonService.searchPokemonsByFilters(name,type,page,size,isAll,isCaught,sortBy,sortDirection)), HttpStatus.OK);
    }

   @PostMapping
    @Operation(summary = "Add new pokemon")
    public ResponseEntity<CreatePokemonRequestDto> addPokemon(@RequestBody @Valid CreatePokemonRequestDto addPokemonRequest){
        Pokemon pokemon = PokemonMapper.INSTANCE.createPokemonRequestDtoToPokemon(addPokemonRequest);
        pokemonService.addPokemon(pokemon,addPokemonRequest.getType1Name(),addPokemonRequest.getType2Name());
        return new ResponseEntity<>(addPokemonRequest, HttpStatus.OK);
    }

    @PutMapping("/{pokemonId}")
    @Operation(summary = "Update pokemon")
    public ResponseEntity<UpdatePokemonRequestDto> updatePokemon(@PathVariable(name = "pokemonId") String pokemonId,
                                                                 @RequestBody @Valid UpdatePokemonRequestDto updatePokemonRequest){
        Pokemon pokemon = PokemonMapper.INSTANCE.updatePokemonRequestDtoToPokemon(updatePokemonRequest);
        pokemonService.updatePokemon(pokemonId,pokemon,updatePokemonRequest.getType1Name(),updatePokemonRequest.getType2Name(),updatePokemonRequest.getTrainerId());
        return new ResponseEntity<>(updatePokemonRequest, HttpStatus.OK);
    }


    @DeleteMapping("/{pokemonId}")
    @Operation(summary = "Delete pokemon")
    public ResponseEntity<HttpStatus> deletePokemon(@PathVariable(name = "pokemonId") String pokemonId){
        pokemonService.deletePokemon(pokemonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}

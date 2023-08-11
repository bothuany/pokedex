package com.obss.pokedexapi.pokemon.controller;

import com.obss.pokedexapi.pokemon.dto.TypeDto;
import com.obss.pokedexapi.pokemon.mapper.TypeMapper;
import com.obss.pokedexapi.pokemon.model.Type;
import com.obss.pokedexapi.pokemon.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/types")
@RestController
@RequiredArgsConstructor
public class TypeController {
    private final TypeService typeService;

    @GetMapping
    @Operation(summary = "Get all types")
    public ResponseEntity<Page<TypeDto>> getAllTypes(@RequestParam(name = "page", required = false) Integer page,
                                                     @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(TypeMapper.INSTANCE.typeToTypeDto(typeService.getAllTypes(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{typeId}")
    @Operation(summary = "Get type by id")
    public ResponseEntity<TypeDto> getTypeById(@PathVariable String typeId) {
        return new ResponseEntity<>(TypeMapper.INSTANCE.typeToTypeDto(typeService.getTypeById(typeId)), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search type by name")
    public ResponseEntity<Page<TypeDto>> searchTypeByName(@RequestParam(name = "name") String name,
                                                          @RequestParam(name = "page", required = false) Integer page,
                                                          @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(TypeMapper.INSTANCE.typeToTypeDto(typeService.searchTypesByName(name, page, size)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new type")
    public ResponseEntity<TypeDto> addType(@RequestBody TypeDto typeDto) {
        Type type = TypeMapper.INSTANCE.typeDtoToType(typeDto);
        typeService.addType(type);
        return new ResponseEntity<>(typeDto, HttpStatus.OK);
    }

    @PutMapping("/{typeId}")
    @Operation(summary = "Update type")
    public ResponseEntity<TypeDto> updateType(@PathVariable(name = "typeId") String typeId,
                                              @RequestBody TypeDto typeDto) {
        Type type = TypeMapper.INSTANCE.typeDtoToType(typeDto);
        typeService.updateType(typeId, type);
        return new ResponseEntity<>(typeDto, HttpStatus.OK);
    }

    @PutMapping("/name/{typeName}")
    @Operation(summary = "Update type by name")
    public ResponseEntity<TypeDto> updateTypeByName(@PathVariable(name = "typeName") String typeName,
                                              @RequestBody TypeDto typeDto) {
        Type type = TypeMapper.INSTANCE.typeDtoToType(typeDto);
        typeService.updateTypeByName(typeName, type);
        return new ResponseEntity<>(typeDto, HttpStatus.OK);
    }


    @DeleteMapping("/{typeId}")
    @Operation(summary = "Delete type")
    public ResponseEntity<HttpStatus> deleteType(@PathVariable(name = "typeId") String typeId) {
        typeService.deleteType(typeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/name/{typeName}")
    @Operation(summary = "Delete type by name")
    public ResponseEntity<HttpStatus> deleteTypeByName(@PathVariable(name = "typeName") String typeName) {
        typeService.deleteTypeByName(typeName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

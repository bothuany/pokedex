package com.obss.pokedexapi.pokemon.model;

import com.obss.pokedexapi.model.BaseEntity;
import com.obss.pokedexapi.pokemon.model.Stats;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
public class Type extends BaseEntity {
    @Column(unique = true)
    private String name;

    private String color;
    @OneToMany(mappedBy = "type1",fetch = FetchType.LAZY)
    private List<Pokemon> pokemons1 = new ArrayList<>();

    @OneToMany(mappedBy = "type2",fetch = FetchType.LAZY)
    private List<Pokemon> pokemons2 = new ArrayList<>();
}

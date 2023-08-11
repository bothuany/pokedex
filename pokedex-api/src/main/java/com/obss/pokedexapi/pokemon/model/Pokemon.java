package com.obss.pokedexapi.pokemon.model;

import com.fasterxml.jackson.annotation.*;
import com.obss.pokedexapi.model.BaseEntity;
import com.obss.pokedexapi.trainer.model.Trainer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Pokemon extends BaseEntity {
    private String name;
    private String cloudinaryPublicId;
    private double price;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("pokemon")
    private Stats stats;

    private String description;
    private Boolean isCaught;

    @JsonIgnoreProperties("pokemons")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type1_id")
    private Type type1;

    @JsonIgnoreProperties("pokemons")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type2_id")
    private Type type2;

    @ManyToOne
    @JsonIgnoreProperties("catchList")
    private Trainer trainer;

    @ManyToMany(mappedBy = "wishList")
    @JsonIgnoreProperties("wishList")
    private List<Trainer> wisherTrainers;
}

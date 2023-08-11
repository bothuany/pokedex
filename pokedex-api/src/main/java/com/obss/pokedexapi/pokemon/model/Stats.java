package com.obss.pokedexapi.pokemon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.obss.pokedexapi.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Stats extends BaseEntity {
    @OneToOne(mappedBy = "stats")
    @JsonIgnore
    private Pokemon pokemon;

    @Min(1)
    @Max(255)
    private int hp;
    @Min(1)
    @Max(255)
    private int attack;
    @Min(1)
    @Max(255)
    private int defense;
    @Min(1)
    @Max(255)
    private int specialAttack;
    @Min(1)
    @Max(255)
    private int specialDefense;
    @Min(1)
    @Max(255)
    private int speed;
    private int total = 0;

    public void calculateTotal(){
        this.total = this.hp + this.attack + this.defense + this.specialAttack + this.specialDefense + this.speed;
    }

    @Override
    public void onPrePersist() {
        super.onPrePersist();
        calculateTotal();
    }

    @Override
    public void onPreUpdate() {
        super.onPreUpdate();
        calculateTotal();
    }
}

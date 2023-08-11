package com.obss.pokedexapi.pokemon.dto;

import com.obss.pokedexapi.pokemon.model.Stats;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePokemonRequestDto {
    @Size(min = 2, max = 30)
    private String name;
    @Min(0)
    private Double price;
    private Stats stats;
    @Size(min = 2, max = 100)
    private String description;
    private Boolean isCaught;
    private String type1Name;
    private String type2Name;
    private String trainerId;
    private String cloudinaryPublicId = "public_id_" + name ;

    @PrePersist
    public void onPrePersist() {
        setCloudinaryPublicId("public_id_"+name);
    }
}

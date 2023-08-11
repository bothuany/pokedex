package com.obss.pokedexapi.pokemon.dto;

import com.obss.pokedexapi.pokemon.model.Stats;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePokemonRequestDto {
    @Size(min = 2, max = 30)
    @NotBlank
    private String name;
    @Min(0)
    @NotNull
    private Double price;
    @NotNull
    private Stats stats;
    @Size(min = 2, max = 100)
    private String description;
    private String type1Name;
    private String type2Name;
    private String cloudinaryPublicId = "public_id_" + name ;

    @PrePersist
    public void onPrePersist() {
        setCloudinaryPublicId("public_id_"+name);
    }
}

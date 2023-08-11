package com.obss.pokedexapi.admin.model;

import com.obss.pokedexapi.user.model.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@DiscriminatorValue("admin")
@NoArgsConstructor
@SuperBuilder
public class Admin extends User {
}

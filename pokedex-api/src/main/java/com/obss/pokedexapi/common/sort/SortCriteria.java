package com.obss.pokedexapi.common.sort;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortCriteria {
    private String sortBy;
    private SortDirection sortDirection;

}
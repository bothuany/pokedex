package com.obss.pokedexapi.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static class Roles{
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String TRAINER = "ROLE_TRAINER";
    }

    public static final double INITIAL_BALANCE = 10000;
    public static final double ILLEGAL_BALANCE = 3.14159;
}

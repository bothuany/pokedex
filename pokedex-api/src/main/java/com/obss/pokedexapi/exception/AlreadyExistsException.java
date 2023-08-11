package com.obss.pokedexapi.exception;

import com.obss.pokedexapi.exception.BusinessException;

public class AlreadyExistsException extends BusinessException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

package com.obss.pokedexapi.exception;

public class NotEnoughBalanceException extends BusinessException{
    public NotEnoughBalanceException(String message) {
        super(message);
    }
}

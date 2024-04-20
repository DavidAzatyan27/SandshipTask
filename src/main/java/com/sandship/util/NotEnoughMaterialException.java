package com.sandship.util;

public class NotEnoughMaterialException extends RuntimeException{
    private final String message;

    public NotEnoughMaterialException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

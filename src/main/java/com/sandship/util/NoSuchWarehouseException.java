package com.sandship.util;

public class NoSuchWarehouseException extends RuntimeException {
    private String message;

    public NoSuchWarehouseException(){
    }

    public NoSuchWarehouseException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.sandship.util;

public class WarehouseFullException extends RuntimeException {
    private String message;

    public WarehouseFullException(final String message) {
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

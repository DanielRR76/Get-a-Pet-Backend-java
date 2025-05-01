package com.danfy.get_a_pet.exceptions.auth;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("Token expirado");
    }
    public ExpiredTokenException(String message) {
        super(message);
    }
}

package com.danfy.get_a_pet.exceptions.auth;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("User unauthorized");
    }
    public UnauthorizedException(String message) {
        super(message);
    }

}

package com.danfy.get_a_pet.exceptions.auth;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("Senha incorreta");
    }
    public IncorrectPasswordException(String message) {
        super(message);
    }

}

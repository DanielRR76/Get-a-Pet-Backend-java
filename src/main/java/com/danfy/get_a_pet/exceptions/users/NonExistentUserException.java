package com.danfy.get_a_pet.exceptions.users;

public class NonExistentUserException extends RuntimeException{
    public NonExistentUserException() {
        super("Usuário não encontrado");
    }
    public NonExistentUserException(String message) {
        super(message);
    }

}

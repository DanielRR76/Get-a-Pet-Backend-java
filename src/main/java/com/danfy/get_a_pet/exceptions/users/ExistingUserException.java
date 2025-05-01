package com.danfy.get_a_pet.exceptions.users;

public class ExistingUserException extends RuntimeException{
    public ExistingUserException() {
        super("Usuário já cadastrado");
    }
    public ExistingUserException(String message) {
        super(message);
    }

}

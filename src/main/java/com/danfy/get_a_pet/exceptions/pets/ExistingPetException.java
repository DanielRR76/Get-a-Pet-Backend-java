package com.danfy.get_a_pet.exceptions.pets;

public class ExistingPetException extends RuntimeException{
    public ExistingPetException() {
        super("Pet ja cadastrado");
    }
    public ExistingPetException(String message) {
        super(message);
    }
}

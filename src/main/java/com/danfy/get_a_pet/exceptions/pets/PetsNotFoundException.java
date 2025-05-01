package com.danfy.get_a_pet.exceptions.pets;

public class PetsNotFoundException extends RuntimeException{
    public PetsNotFoundException() {
        super("No pets found");
    }
    public PetsNotFoundException(String message) {
        super(message);
    }

}

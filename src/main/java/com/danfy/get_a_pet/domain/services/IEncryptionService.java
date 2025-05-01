package com.danfy.get_a_pet.domain.services;

public interface IEncryptionService {
    String encrypt(String password);
    boolean check(String password, String hashedPassword);
}

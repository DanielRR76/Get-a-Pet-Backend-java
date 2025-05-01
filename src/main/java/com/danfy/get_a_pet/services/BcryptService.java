package com.danfy.get_a_pet.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danfy.get_a_pet.domain.services.IEncryptionService;

@Service
public class BcryptService implements IEncryptionService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BcryptService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public String encrypt(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean check(String password, String hashedPassword) {
        return bCryptPasswordEncoder.matches(password, hashedPassword);
    }

}

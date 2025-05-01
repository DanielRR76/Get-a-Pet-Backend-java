package com.danfy.get_a_pet.domain.repositories;

import org.springframework.security.core.userdetails.UserDetails;

import com.danfy.get_a_pet.domain.entities.models.User;

public interface IUserRepository extends IDatabaseRepository<User, String> {
    UserDetails findByEmail(String email);
}

package com.danfy.get_a_pet.domain.services;

import com.danfy.get_a_pet.domain.entities.models.User;
import com.danfy.get_a_pet.dtos.DecodedTokenDTO;

public interface ITokenService {
    String create(User user);
    DecodedTokenDTO validate(String token);
}

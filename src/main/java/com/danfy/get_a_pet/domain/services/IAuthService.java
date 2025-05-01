package com.danfy.get_a_pet.domain.services;

import com.danfy.get_a_pet.domain.entities.models.User;
import com.danfy.get_a_pet.dtos.RestSuccessMessage;

public interface IAuthService {
    RestSuccessMessage register(User user);

    RestSuccessMessage login(User existingUser);
    User getAuthenticatedUser();
}

package com.danfy.get_a_pet.domain.services;

import com.danfy.get_a_pet.dtos.RestSuccessMessage;
import com.danfy.get_a_pet.dtos.UserCreateRequestDTO;
import com.danfy.get_a_pet.dtos.UserLoginRequestDTO;
import com.danfy.get_a_pet.dtos.UserUpdateRequestDTO;

public interface IUserService {
    public RestSuccessMessage create(UserCreateRequestDTO user);

    public RestSuccessMessage login(UserLoginRequestDTO user);

    public RestSuccessMessage check();

    public RestSuccessMessage get(String id);

    public RestSuccessMessage update(String id, UserUpdateRequestDTO user);

    public RestSuccessMessage delete(String id);
}

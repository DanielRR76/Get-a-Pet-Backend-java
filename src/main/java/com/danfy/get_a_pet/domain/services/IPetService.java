package com.danfy.get_a_pet.domain.services;

import com.danfy.get_a_pet.dtos.PetCreateRequestDTO;
import com.danfy.get_a_pet.dtos.RestSuccessMessage;

public interface IPetService {
    RestSuccessMessage create(PetCreateRequestDTO dto);

    RestSuccessMessage getAll();

    RestSuccessMessage getAllByOwner();

    RestSuccessMessage getAllByAdoper();

    RestSuccessMessage get(String id);

    RestSuccessMessage delete(String id);

    RestSuccessMessage update(String id, PetCreateRequestDTO dto);
}

package com.danfy.get_a_pet.domain.repositories;

import java.util.List;

import com.danfy.get_a_pet.domain.entities.models.Pet;

public interface IPetRepository extends IDatabaseRepository<Pet, String> {
    Pet findFirstByNameAndOwnerIdAndAge(String name, String ownerId, int age);
    List<Pet> findAllByOwnerId(String ownerId);
    List<Pet> findAllByAdopterId(String adopterId);
}

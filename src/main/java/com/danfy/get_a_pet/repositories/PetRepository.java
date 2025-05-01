package com.danfy.get_a_pet.repositories;

import com.danfy.get_a_pet.domain.entities.models.Pet;
import com.danfy.get_a_pet.domain.entities.models.User;
import com.danfy.get_a_pet.domain.repositories.IPetRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends MongoRepository<Pet, String>, IPetRepository {
    Pet findByOwner(User owner);
    Pet findByAdopter(User adopter);
}

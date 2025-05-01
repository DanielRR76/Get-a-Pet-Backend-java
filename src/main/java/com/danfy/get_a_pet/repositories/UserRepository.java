package com.danfy.get_a_pet.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.danfy.get_a_pet.domain.entities.models.User;
import com.danfy.get_a_pet.domain.repositories.IUserRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String>, IUserRepository {


}

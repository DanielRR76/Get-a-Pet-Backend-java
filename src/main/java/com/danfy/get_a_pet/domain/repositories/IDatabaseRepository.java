package com.danfy.get_a_pet.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface IDatabaseRepository <T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}

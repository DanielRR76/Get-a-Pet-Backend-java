package com.danfy.get_a_pet.dtos;

import com.danfy.get_a_pet.domain.entities.models.User;

public record UserResponseDTO(String id, String name, String email, String image, String role) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getImage(), user.getRole().getRole());
    }
}

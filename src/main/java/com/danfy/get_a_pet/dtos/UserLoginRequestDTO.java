package com.danfy.get_a_pet.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(
    @NotBlank(message = "O email é obrigatorio") String email,
    @NotBlank(message = "A senha é obrigatoria") String password) {

}

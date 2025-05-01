package com.danfy.get_a_pet.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequestDTO(
    @NotBlank(message = "O nome é obrigatorio") String name,
    @NotBlank(message = "O email é obrigatorio") @Email String email,
    @Pattern(regexp = "^\\d{10,15}$", message = "O telefone deve conter entre 10 e 15 digitos") String phone,
    MultipartFile image,
    @NotBlank(message = "A senha é obrigatoria") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "A senha deve conter pelo menos 8 caracteres, uma letra e um numero") String password,
    @NotBlank(message = "A confirmacao de senha é obrigatoria") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "A confirmacao de senha deve conter pelo menos 8 caracteres, uma letra e um numero") String confirmPassword) {

}

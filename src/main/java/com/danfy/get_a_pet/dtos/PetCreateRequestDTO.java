package com.danfy.get_a_pet.dtos;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PetCreateRequestDTO(
        @NotBlank(message = "O nome é obrigatorio") String name,
        @NotNull(message = "A idade é obrigatória") Integer age,
        @NotNull(message = "O peso é obrigatorio") Double weight,
        @NotBlank(message = "A cor é obrigatoria")
        @Pattern(
            regexp = "^(?i)(BRANCO|CINZA|MARROM|PRETO|CARAMELO|MALHADO)$",
            message = "A cor do pet deve ser uma das seguintes: branco, cinza, marrom, preto, caramelo ou malhado"
        ) String color,
        @NotNull(message = "A imagem não pode ser nula.")
        @NotEmpty(message = "É necessário enviar pelo menos uma imagem.")
        List<MultipartFile> files) {
        public PetCreateRequestDTO(String name, String age, String weight, String color, List<MultipartFile> files) {
            this(
                name,
                (age == null || age.isBlank()) ? null : Integer.parseInt(age),
                Double.parseDouble(weight),
                (color == null) ? null: color.toUpperCase(),
                files
            );
        }
}

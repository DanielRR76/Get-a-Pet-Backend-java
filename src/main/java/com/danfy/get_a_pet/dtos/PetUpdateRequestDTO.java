package com.danfy.get_a_pet.dtos;

import jakarta.validation.constraints.Pattern;

public record PetUpdateRequestDTO(
        String name,
        int age,
        Double weight,

        @Pattern(regexp = "^(BRANCO|CINZA|MARROM|PRETO|CARAMELO|MALHADO)$",
                message = "A cor do pet deve ser uma das seguintes: BRANCO, CINZA, MARROM, PRETO, CARAMELO, MALHADO")
        String color,

        Boolean available
) {
    public PetUpdateRequestDTO {
        if (color != null) {
            color = color.toUpperCase();
        }
    }
}


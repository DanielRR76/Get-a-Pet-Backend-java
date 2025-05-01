package com.danfy.get_a_pet.dtos;

import org.springframework.web.multipart.MultipartFile;

public record FileDTO(MultipartFile file, String folder) {

}

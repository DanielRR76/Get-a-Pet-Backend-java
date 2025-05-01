package com.danfy.get_a_pet.domain.entities.models;

import java.time.Instant;
import java.util.List;

import com.danfy.get_a_pet.dtos.PetCreateRequestDTO;
import com.danfy.get_a_pet.dtos.PetUpdateRequestDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Pet")
@Getter
@Setter
@NoArgsConstructor
public class Pet {
    @Id
    private String id;
    private String name;
    private int age;
    private Double weight;
    private String color;
    private List<String> images;
    private Boolean available;
    private User owner;
    private User adopter;
    private Instant createdAt;
    private Instant updatedAt;

    public Pet(PetCreateRequestDTO dto, List<String> images, User owner) {
        this.name = dto.name();
        this.age = dto.age();
        this.weight = dto.weight();
        this.color = dto.color().toUpperCase();
        this.images = images;
        this.available = true;
        this.createdAt = Instant.now();
        this.owner = owner;
    }
    public void update(PetUpdateRequestDTO payload, String image){
        this.name = payload.name();
        this.age = payload.age();
        this.weight = payload.weight();
        this.color = payload.color();
        this.updatedAt = Instant.now();
    }
}

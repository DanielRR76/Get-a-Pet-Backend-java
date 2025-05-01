package com.danfy.get_a_pet.controllers;

import com.danfy.get_a_pet.domain.services.IPetService;
import com.danfy.get_a_pet.dtos.PetCreateRequestDTO;
import com.danfy.get_a_pet.dtos.RestSuccessMessage;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/pets")
public class PetController {
    private final IPetService petService;

    public PetController(IPetService petService) {
        this.petService = petService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestSuccessMessage> create (@ModelAttribute @Valid PetCreateRequestDTO dto) {
        return ResponseEntity.ok(petService.create(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<RestSuccessMessage> getAll() {
        return ResponseEntity.ok(petService.getAll());
    }

    @GetMapping("/all-by-owner")
    public ResponseEntity<RestSuccessMessage> getAllByOwner() {
        return ResponseEntity.ok(petService.getAllByOwner());
    }

    @GetMapping("/all-by-adopter")
    public ResponseEntity<RestSuccessMessage> getAllByAdoper() {
        return ResponseEntity.ok(petService.getAllByAdoper());
    }

    @GetMapping("{id}")
    public ResponseEntity<RestSuccessMessage> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(petService.get(id));
    }
    @PatchMapping("{id}")
    public ResponseEntity<RestSuccessMessage> update(@PathVariable("id") String id, @ModelAttribute @Valid PetCreateRequestDTO dto) {
        return ResponseEntity.ok(petService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestSuccessMessage> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(petService.delete(id));
    }

}

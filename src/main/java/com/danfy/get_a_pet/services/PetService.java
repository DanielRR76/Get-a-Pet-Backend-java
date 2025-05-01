package com.danfy.get_a_pet.services;

import com.danfy.get_a_pet.dtos.FileDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.danfy.get_a_pet.domain.entities.models.Pet;
import com.danfy.get_a_pet.domain.entities.models.User;
import com.danfy.get_a_pet.domain.repositories.IPetRepository;
import com.danfy.get_a_pet.domain.services.IAuthService;
import com.danfy.get_a_pet.domain.services.IMediaService;
import com.danfy.get_a_pet.domain.services.IPetService;
import com.danfy.get_a_pet.dtos.PetCreateRequestDTO;
import com.danfy.get_a_pet.dtos.RestSuccessMessage;
import com.danfy.get_a_pet.exceptions.auth.UnauthorizedException;
import com.danfy.get_a_pet.exceptions.pets.ExistingPetException;
import com.danfy.get_a_pet.exceptions.pets.PetsNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService implements IPetService {
    private final IAuthService authService;
    private final IMediaService mediaService;
    private final IPetRepository petRepository;
    PetService(IAuthService authService, IMediaService mediaService, IPetRepository petRepository) {
        this.authService = authService;
        this.mediaService = mediaService;
        this.petRepository = petRepository;
    }
    @Override
    public RestSuccessMessage create(PetCreateRequestDTO dto) {
        User authenticatedUser = authService.getAuthenticatedUser();
        Pet ExistingPet = petRepository.findFirstByNameAndOwnerIdAndAge(dto.name(), authenticatedUser.getId(), dto.age());
        if (ExistingPet != null) {
            throw new ExistingPetException();
        }
        List<FileDTO> files = dto.files().stream()
                                    .map(file -> new FileDTO(file, "pets"))
                                    .toList();
        List<String> imagesUrls = mediaService.getFiles(files);
        Pet pet = new Pet(dto,imagesUrls, authenticatedUser);
        petRepository.save(pet);

        return new RestSuccessMessage(HttpStatus.OK, "Pet criado com sucesso!");
    }
    @Override
    public RestSuccessMessage getAll() {
        List<Pet> pets = petRepository.findAll();
        ArrayList<Object> payload = new ArrayList<>();
        for (Pet pet : pets) {
            payload.add(pet);
        }
        return new RestSuccessMessage(HttpStatus.OK, "Pets encontrados com sucesso!", payload);
    }
    @Override
    public RestSuccessMessage getAllByOwner() {
        User authenticatedUser = authService.getAuthenticatedUser();
        List<Pet> pets = petRepository.findAllByOwnerId(authenticatedUser.getId());
        if (pets.isEmpty()) {
            throw new PetsNotFoundException();
        }
        ArrayList<Object> payload = new ArrayList<>();
        for (Pet pet : pets) {
            payload.add(pet);
        }
        return new RestSuccessMessage(HttpStatus.OK, "Pets encontrados com sucesso!", payload);
    }
    @Override
    public RestSuccessMessage getAllByAdoper() {
        User authenticatedUser = authService.getAuthenticatedUser();
        List<Pet> pets = petRepository.findAllByAdopterId(authenticatedUser.getId());
        if (pets.isEmpty()) {
            throw new PetsNotFoundException();
        }
        ArrayList<Object> payload = new ArrayList<>();
        for (Pet pet : pets) {
            payload.add(pet);
        }
        return new RestSuccessMessage(HttpStatus.OK, "Pets encontrados com sucesso!", payload);
    }
    @Override
    public RestSuccessMessage get(String id) {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new PetsNotFoundException();
        }
        ArrayList<Object> payload = new ArrayList<>();
        payload.add(pet.get());
        return new RestSuccessMessage(HttpStatus.OK, "Pet encontrado com sucesso!", payload);
    }
    @Override
    public RestSuccessMessage delete(String id) {
        User authenticatedUser = authService.getAuthenticatedUser();
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new PetsNotFoundException();
        }
        if (!pet.get().getOwner().getId().equals(authenticatedUser.getId())) {
            throw new UnauthorizedException();
        }
        mediaService.deleteFiles(pet.get().getImages());
        petRepository.deleteById(id);
        return new RestSuccessMessage(HttpStatus.OK, "Pet deletado com sucesso!");
    }
    @Override
    public RestSuccessMessage update(String id, PetCreateRequestDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}

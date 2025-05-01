package com.danfy.get_a_pet.services;

import java.util.ArrayList;

import com.danfy.get_a_pet.domain.services.IMediaService;
import com.danfy.get_a_pet.exceptions.auth.IncorrectPasswordException;
import com.danfy.get_a_pet.exceptions.auth.UnauthorizedException;
import com.danfy.get_a_pet.exceptions.users.ExistingUserException;
import com.danfy.get_a_pet.exceptions.users.NonExistentUserException;
import com.danfy.get_a_pet.exceptions.users.PwdNotEqualConfirmPwdException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.danfy.get_a_pet.domain.entities.models.User;
import com.danfy.get_a_pet.domain.repositories.IUserRepository;
import com.danfy.get_a_pet.domain.services.IAuthService;
import com.danfy.get_a_pet.domain.services.IEncryptionService;
import com.danfy.get_a_pet.domain.services.IUserService;
import com.danfy.get_a_pet.dtos.FileDTO;
import com.danfy.get_a_pet.dtos.RestSuccessMessage;
import com.danfy.get_a_pet.dtos.UserCreateRequestDTO;
import com.danfy.get_a_pet.dtos.UserLoginRequestDTO;
import com.danfy.get_a_pet.dtos.UserResponseDTO;
import com.danfy.get_a_pet.dtos.UserUpdateRequestDTO;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IEncryptionService encryptionService;
    private final IAuthService authService;
    private final IMediaService mediaService;

    public UserService(IUserRepository userRepository, IEncryptionService encryptionService, IAuthService authService, IMediaService mediaService) {
        this.encryptionService = encryptionService;
        this.userRepository = userRepository;
        this.authService = authService;
        this.mediaService = mediaService;
    }
    @Override
    public RestSuccessMessage create(UserCreateRequestDTO user) {

        if (!user.password().equals(user.confirmPassword())) {
            throw new PwdNotEqualConfirmPwdException();
        }
        var ExistingUser = userRepository.findByEmail(user.email());
        if (ExistingUser != null) {
            throw new ExistingUserException();
        }

        var encryptedPassword = encryptionService.encrypt(user.password());
        var newUser = new User(user, encryptedPassword);
        userRepository.save(newUser);

        return authService.register(newUser);
    }
    @Override
    public RestSuccessMessage login(UserLoginRequestDTO user) {
        var ExistingUser = userRepository.findByEmail(user.email());
        if (ExistingUser == null) {
            throw new NonExistentUserException();
        }
        var isPasswordCorrect = encryptionService.check(user.password(), ExistingUser.getPassword());
        if (!isPasswordCorrect) {
            throw new IncorrectPasswordException();
        }
        User loginUser = (User) ExistingUser;
        return authService.login(loginUser);
    }
    @Override
    public RestSuccessMessage check() {
        User authenticatedUser = authService.getAuthenticatedUser();
        UserResponseDTO user = new UserResponseDTO(authenticatedUser);
        ArrayList<Object> payload = new ArrayList<>();
        payload.add(user);
        return new RestSuccessMessage(HttpStatus.OK, "Usuario checado com sucesso!", payload);
    }
    @Override
    public RestSuccessMessage get(String id) {
        User authenticatedUser = authService.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UnauthorizedException();
        }
        UserResponseDTO user = new UserResponseDTO(authenticatedUser);
        ArrayList<Object> payload = new ArrayList<>();
        payload.add(user);
        return new RestSuccessMessage(HttpStatus.OK, "Usuario obtido com sucesso!", payload);
    }
    @Override
    public RestSuccessMessage update(String id, UserUpdateRequestDTO dto) {
        User authenticatedUser = authService.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UnauthorizedException();
        }
        if (!dto.password().equals(dto.confirmPassword())) {
            throw new PwdNotEqualConfirmPwdException();
        }
        var ExistingUser = (User) userRepository.findByEmail(dto.email());
        if (ExistingUser != null && !ExistingUser.getEmail().equals(dto.email())) {
            throw new ExistingUserException();
        }
        var encryptedPassword = encryptionService.encrypt(dto.password());
        var user = userRepository.findById(authenticatedUser.getId()).orElseThrow();
        user.update(dto, encryptedPassword);
        String imageUrl = user.getImage();
        if (dto.image() != null && !dto.image().isEmpty()) {
            FileDTO file = new FileDTO(dto.image(), "users");
            imageUrl = mediaService.getFile(file, imageUrl);
            user.setImage(imageUrl);
        }
        userRepository.save(user);

        return new RestSuccessMessage(HttpStatus.OK, "Usuario atualizado com sucesso!");
    }
    @Override
    public RestSuccessMessage delete(String id) {
        User authenticatedUser = authService.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UnauthorizedException();
        }
        if(authenticatedUser.getImage() != null) {
            mediaService.deleteFile(authenticatedUser.getImage());
        }
        userRepository.deleteById(id);
        return new RestSuccessMessage(HttpStatus.OK, "Usuario deletado com sucesso!");
    }

}

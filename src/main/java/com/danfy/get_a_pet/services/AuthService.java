package com.danfy.get_a_pet.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.danfy.get_a_pet.domain.entities.models.User;
import com.danfy.get_a_pet.domain.services.IAuthService;
import com.danfy.get_a_pet.domain.services.ITokenService;
import com.danfy.get_a_pet.dtos.RestSuccessMessage;
import com.danfy.get_a_pet.exceptions.auth.UnauthorizedException;

@Service
public class AuthService implements IAuthService {
    private final ITokenService tokenService;

    public AuthService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Override
    public RestSuccessMessage register(User user) {
        var token = tokenService.create(user);
        return new RestSuccessMessage(HttpStatus.CREATED,"Usuario criado com sucesso!", token);
    }
    @Override
    public RestSuccessMessage login(User existingUser) {
        var token = tokenService.create(existingUser);
        return new RestSuccessMessage(HttpStatus.OK,"Usuario logado com sucesso!", token);
    }
    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        User authenticatedUser = (User) authentication.getPrincipal();
        return authenticatedUser;
    }

}

package com.danfy.get_a_pet.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.danfy.get_a_pet.domain.repositories.IUserRepository;
import com.danfy.get_a_pet.domain.services.IUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements IUserDetailsService {
    private final IUserRepository userRepository;

    public AuthUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

}

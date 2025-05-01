package com.danfy.get_a_pet.domain.entities.models;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.danfy.get_a_pet.domain.enums.Role;
import com.danfy.get_a_pet.dtos.UserCreateRequestDTO;
import com.danfy.get_a_pet.dtos.UserUpdateRequestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "User")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String phone;
    private String password;
    private String image;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;

    public User(UserCreateRequestDTO user, String encryptedPassword) {
        this.name = user.name();
        this.email = user.email();
        this.phone = user.phone();
        this.password = encryptedPassword;
        this.role = Role.USER;
        this.createdAt = Instant.now();
    }

    public void update(UserUpdateRequestDTO payload, String encryptedPassword) {
        this.name = payload.name();
        this.email = payload.email();
        this.phone = payload.phone();
        this.password = encryptedPassword;
        this.updatedAt = Instant.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }
}

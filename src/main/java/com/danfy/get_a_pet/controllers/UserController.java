package com.danfy.get_a_pet.controllers;

import org.springframework.web.bind.annotation.*;

import com.danfy.get_a_pet.domain.services.IUserService;
import com.danfy.get_a_pet.dtos.RestSuccessMessage;
import com.danfy.get_a_pet.dtos.UserCreateRequestDTO;
import com.danfy.get_a_pet.dtos.UserLoginRequestDTO;
import com.danfy.get_a_pet.dtos.UserUpdateRequestDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<RestSuccessMessage> create(@RequestBody @Valid UserCreateRequestDTO user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PostMapping("/signin")
    public ResponseEntity<RestSuccessMessage> login(@RequestBody @Valid UserLoginRequestDTO user) {
        return ResponseEntity.ok(userService.login(user));
    }

    @GetMapping("/check")
    public ResponseEntity<RestSuccessMessage> check() {
        return ResponseEntity.ok(userService.check());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> update(@PathVariable ("id") String id, @ModelAttribute @Valid UserUpdateRequestDTO user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> delete(@PathVariable ("id") String id) {
        return ResponseEntity.ok(userService.delete(id));
    }

}

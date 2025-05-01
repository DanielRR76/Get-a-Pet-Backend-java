package com.danfy.get_a_pet.dtos;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestSuccessMessage(HttpStatus status, String message, String token, ArrayList<Object> payload) {

    public RestSuccessMessage(HttpStatus status, String message, String token) {
        this(status, message, token, null);
    }
    public RestSuccessMessage(HttpStatus status, String message, ArrayList<Object> payload) {
        this(status, message, null, payload);
    }
    public RestSuccessMessage(HttpStatus status, String message) {
        this(status, message, null, null);
    }
}

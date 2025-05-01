package com.danfy.get_a_pet.exceptions;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestErrorMessage(
    HttpStatus status,
    String message,
    ErrorField[] errors) {

    public RestErrorMessage(HttpStatus badRequest, String errorMessage) {
        this(badRequest, errorMessage, null);
    }

}

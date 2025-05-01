package com.danfy.get_a_pet.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.danfy.get_a_pet.exceptions.auth.IncorrectPasswordException;
import com.danfy.get_a_pet.exceptions.auth.UnauthorizedException;
import com.danfy.get_a_pet.exceptions.media.ErrorDeleteFileException;
import com.danfy.get_a_pet.exceptions.media.ErrorUploadFileException;
import com.danfy.get_a_pet.exceptions.pets.ExistingPetException;
import com.danfy.get_a_pet.exceptions.pets.PetsNotFoundException;
import com.danfy.get_a_pet.exceptions.users.ExistingUserException;
import com.danfy.get_a_pet.exceptions.users.NonExistentUserException;
import com.danfy.get_a_pet.exceptions.users.PwdNotEqualConfirmPwdException;

import jakarta.servlet.ServletException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<RestErrorMessage> buildErrorResponse(HttpStatus status, String message, ErrorField[] errors) {
        RestErrorMessage response = new RestErrorMessage(status, message, errors);
        return ResponseEntity.status(status).body(response);
    }
    private ResponseEntity<RestErrorMessage> buildErrorResponse(HttpStatus status, String message) {
        RestErrorMessage response = new RestErrorMessage(status, message);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorField[] errorsList = new ErrorField[ex.getBindingResult().getFieldErrors().size()];

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ErrorField errorField = new ErrorField(error.getField(), error.getDefaultMessage());
            errorsList[ex.getBindingResult().getFieldErrors().indexOf(error)] = errorField;
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Campos obrigatórios não preenchidos.", errorsList);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "O corpo da requisição está ausente ou malformado. Por favor, envie dados válidos.";
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(PwdNotEqualConfirmPwdException.class)
    public ResponseEntity<RestErrorMessage> handlePwdNotEqualConfirmPwdException(PwdNotEqualConfirmPwdException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<RestErrorMessage> handleExistingUserException(ExistingUserException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(NonExistentUserException.class)
    public ResponseEntity<RestErrorMessage> handleNonExistentUserException(NonExistentUserException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<RestErrorMessage> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<RestErrorMessage> handleJWTVerificationException(JWTVerificationException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<RestErrorMessage> handleUnauthorizedException(UnauthorizedException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ErrorUploadFileException.class)
    public  ResponseEntity<RestErrorMessage> handleErrorUploadFileException(ErrorUploadFileException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ErrorDeleteFileException.class)
    public ResponseEntity<RestErrorMessage> handleErrorDeleteFileException(ErrorDeleteFileException ex) {
        return  buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ExistingPetException.class)
    public ResponseEntity<RestErrorMessage> handleExistingPetException(ExistingPetException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(PetsNotFoundException.class)
    public ResponseEntity<RestErrorMessage> handlePetsNotFoundException(PetsNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestErrorMessage> handleRuntimeException (RuntimeException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<RestErrorMessage> handleIOException (IOException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<RestErrorMessage> handleServletException (ServletException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}

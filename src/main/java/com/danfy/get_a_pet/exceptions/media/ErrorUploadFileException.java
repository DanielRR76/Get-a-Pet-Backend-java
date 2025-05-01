package com.danfy.get_a_pet.exceptions.media;

public class ErrorUploadFileException extends RuntimeException{
    public ErrorUploadFileException(){
        super("Erro ao fazer o upload do arquivo multimídia");
    }
    public  ErrorUploadFileException(String message) {
        super(message);
    }
}

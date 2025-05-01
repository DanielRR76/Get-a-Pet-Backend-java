package com.danfy.get_a_pet.exceptions.media;

public class ErrorDeleteFileException  extends RuntimeException{
    public ErrorDeleteFileException() {
        super("Erro ao deletar o arquivo multimídia");
    }
    public ErrorDeleteFileException(String message) {
        super(message);
    }
}

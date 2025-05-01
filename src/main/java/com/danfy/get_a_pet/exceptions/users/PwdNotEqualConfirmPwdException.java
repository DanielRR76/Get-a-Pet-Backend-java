package com.danfy.get_a_pet.exceptions.users;

public class PwdNotEqualConfirmPwdException extends RuntimeException {
    public PwdNotEqualConfirmPwdException() {
        super("A senha e a confirmação de senha devem ser iguais");
    }
    public PwdNotEqualConfirmPwdException(String message) {
        super(message);
    }
}

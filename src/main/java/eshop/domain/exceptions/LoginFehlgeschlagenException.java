package eshop.domain.exceptions;

public class LoginFehlgeschlagenException extends Exception {
    public LoginFehlgeschlagenException(String zusatzMsg) {
        super(zusatzMsg);
    }
}

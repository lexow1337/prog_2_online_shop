package eshop.domain.exceptions;

public class LoginFehlgeschlagenException extends EShopException {
    public LoginFehlgeschlagenException(String zusatzMsg) {
        super(zusatzMsg);
    }
}

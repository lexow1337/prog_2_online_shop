package eshop.domain.exceptions;

public class BenutzerExistiertBereitsException extends Exception {
    public BenutzerExistiertBereitsException(String zusatzMsg) {
        super(zusatzMsg);
    }
}

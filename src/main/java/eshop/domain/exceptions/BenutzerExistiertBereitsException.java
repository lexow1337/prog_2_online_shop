package eshop.domain.exceptions;

public class BenutzerExistiertBereitsException extends EShopException {
    public BenutzerExistiertBereitsException(String zusatzMsg) {
        super(zusatzMsg);
    }
}

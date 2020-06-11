package eshop.domain.exceptions;

import eshop.valueobjects.Artikel;

public class ArtikelNichtVerfuegbarException extends EShopException{
    private Artikel artikel;

    public ArtikelNichtVerfuegbarException(Artikel artikel, String zusatzMsg) {
        super("Artikel mit Bezeichnung " + artikel.getBezeichnung() + " und der Nummer " +artikel.getArtikelNummer() + " ist leider nicht verfuegbar " + zusatzMsg);
        this.artikel=artikel;
    }
}

package eshop.domain.exceptions;

import eshop.valueobjects.Artikel;

public class ArtikelExistiertBereitsException extends EShopException {

    private Artikel artikel;

    public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
        super("Artikel mit Bezeichnung " + artikel.getBezeichnung() + " und Nummer " + artikel.getNummer()
                + "existiert bereits" + zusatzMsg);
        this.artikel = artikel;
    }

    public Artikel getArtikel() { return artikel; }
}

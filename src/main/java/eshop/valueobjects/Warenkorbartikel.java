package eshop.valueobjects;

import java.util.Date;
import java.util.Vector;

public class Warenkorbartikel {

    private Nutzer nutzer;
    private Artikel artikel;
    private Date date;
    private int idate;

    public Warenkorbartikel(Artikel _artikel, Nutzer _nutzer) {
        this.artikel = _artikel;
        this.nutzer = _nutzer;
    }

    public String toString() {
        return artikel.toString();
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }
}

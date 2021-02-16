package eshop.valueobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ereignis {

    String typ;
    String benutzerName;
    int artikelNr;
    int artikelanzahl;
    int benutzerNr;
    private String datum;

    public Ereignis(String typ, int artikelNr, int artikelanzahl, int benutzerNr, String benutzerName) {
        this.typ = typ;
        this.artikelNr = artikelNr;
        this.artikelanzahl = artikelanzahl;
        this.benutzerNr = benutzerNr;
        this.datum = getDatum();
        this.benutzerName = benutzerName;
    }
    public Ereignis(String typ, int artikelNr, int artikelanzahl, int benutzerNr, String benutzerName, String datum) {
        this.typ = typ;
        this.artikelNr = artikelNr;
        this.artikelanzahl = artikelanzahl;
        this.benutzerNr = benutzerNr;
        this.datum = datum;
        this.benutzerName = benutzerName;
    }

    public String getTyp() {
        return typ;
    }

    public int getArtikelNr() {
        return artikelNr;
    }

    public int getArtikelanzahl() {
        return artikelanzahl;
    }

    public int getBenutzerNr() {
        return benutzerNr;
    }

    public String getZeitpunkt() {
        return datum;
    }

    public String getDatum() {
        return new SimpleDateFormat("dd.MM.yyy HH:mm").format(new Date());
    }
}

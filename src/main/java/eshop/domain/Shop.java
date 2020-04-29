package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.valueobjects.Artikel;

import java.io.IOException;
import java.util.Vector;

public class Shop {

    private String datei = "";
    private ShopVerwaltung meineArtikel;

    public Shop(String datei) throws IOException {
        this.datei = datei;
        meineArtikel = new ShopVerwaltung();
        meineArtikel.liesDaten(datei+"_S.txt");

//		// Kundenkartei aus Datei einlesen
//		meineKunden = new KundenVerwaltung();
//		meineKunden.liesDaten(datei+"_K.txt");
//		meineKunden.schreibeDaten(datei+"_K.txt");
    }

    public Vector gibAlleArtikel() {
        return meineArtikel.getArtikelBestand();
    }

    public int artikelMenge() {
        return meineArtikel.getArtikelBestand().size();
    }

    public Vector sucheNachBezeichnung(String bezeichnung) {
        return meineArtikel.sucheArtikel(bezeichnung);
    }

    public Artikel fuegeArtikelEin(String bezeichnung, String marke, int bestand, int nummer) throws ArtikelExistiertBereitsException {
        Artikel a = new Artikel(nummer, bestand, bezeichnung, marke);
        meineArtikel.einfuegen(a);
        return a;
    }

    public void loescheArtikel(String bezeichnung, String marke, int bestand, int nummer) {
        Artikel a = new Artikel(nummer, bestand, bezeichnung, marke);
        meineArtikel.loeschen(a);
    }

    public void schreibeArtikel() throws IOException {
        meineArtikel.schreibeDaten(datei+"_S.txt");
    }

}

package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.BenutzerExistiertBereitsException;
import eshop.domain.exceptions.LoginFehlgeschlagenException;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import java.io.IOException;
import java.util.Vector;

public class Shop {

    private String datei = "";
    private ShopVerwaltung meineArtikel;
    private NutzerVerwaltung meineNutzer;

    public Shop(String datei) throws IOException {
        this.datei = datei;
        meineArtikel = new ShopVerwaltung();
        meineArtikel.liesDaten(datei+"_S.txt");

        meineNutzer = new NutzerVerwaltung();
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

    //Anzahl der Mitarbeiter zurückgeben
    public int mitarbeiterAnzahl() {
        return meineNutzer.alleMitarbeiter.size();
    }

    //Anzahl der Kunden zurückgeben
    public int kundenAnzahl() {
        return meineNutzer.alleKunden.size();
    }

    //Kunde registrieren
    public void registrieren(String vorname, String nachname, String login, String passwort, int nummer) throws BenutzerExistiertBereitsException {
        meineNutzer.registrieren(vorname, nachname, login, passwort, nummer);
    }

    //Mitarbeiter registrieren
    public void registrieren(String vorname, String nachname, String login, String passwort, int nummer, boolean isMitarbeiter) throws BenutzerExistiertBereitsException {
        meineNutzer.registrieren(vorname, nachname, login, passwort, nummer, isMitarbeiter);
    }

    //Kunde einloggen
    public Kunde einloggen(String login, String passwort) throws LoginFehlgeschlagenException {
        return meineNutzer.einloggen(login, passwort);
    }

    //Mitarbeiter einloggen
    public Mitarbeiter einloggen(String login, String passwort, boolean isMitarbeiter) throws LoginFehlgeschlagenException {
        return meineNutzer.einloggen(login, passwort, true);
    }

}

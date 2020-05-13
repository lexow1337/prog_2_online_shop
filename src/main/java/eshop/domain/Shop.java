package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelNichtVerfuegbarException;
import eshop.domain.exceptions.BenutzerExistiertBereitsException;
import eshop.domain.exceptions.LoginFehlgeschlagenException;
import eshop.valueobjects.*;

import java.io.IOException;
import java.util.Vector;

public class Shop {

    private String datei = "";
    private ShopVerwaltung meineArtikel;
    private WarenkorbVerwaltung meinWarenkorb;
    private NutzerVerwaltung meineNutzer;
    private Nutzer eingeloggterNutzer;

    public Shop(String datei) throws IOException {
        this.datei = datei;
        meineArtikel = new ShopVerwaltung();
        meineArtikel.liesDaten(datei+"_S.txt");

        meineNutzer = new NutzerVerwaltung();
        meinWarenkorb = new WarenkorbVerwaltung(meineNutzer, meineArtikel);
    }

    public Nutzer getEingeloggterNutzer(){
        return eingeloggterNutzer;
    }

    public Vector gibAlleArtikel() {
        return meineArtikel.getArtikelBestand();
    }

    public int artikelMenge() {
        return meineArtikel.getArtikelBestand().size();
    }

    public Vector<Artikel> sucheNachBezeichnung(String bezeichnung) {
        return meineArtikel.sucheArtikel(bezeichnung);
    }

    public Artikel fuegeArtikelEin(String bezeichnung, String marke, int bestand) throws ArtikelExistiertBereitsException {
        int nummer = artikelMenge() + 1;
        Artikel a = new Artikel(nummer, bestand, bezeichnung, marke);
        meineArtikel.einfuegen(a);
        return a;
    }

    public Artikel getArtikel(String bezeichnung) {
        return null;
    }

    public void loescheArtikel(String bezeichnung, String marke, int bestand, int nummer) {
        Artikel a = new Artikel(nummer, bestand, bezeichnung, marke);
        meineArtikel.loeschen(a);
    }

    public void schreibeArtikel() throws IOException {
        meineArtikel.schreibeDaten(datei+"_S.txt");
    }

    //Anzahl der Mitarbeiter zurückgeben
    public int nutzerAnzahl() {
        return meineNutzer.alleNutzer.size();
    }

    public void registrieren(Nutzer nutzer) throws BenutzerExistiertBereitsException{
        meineNutzer.registrieren(nutzer);
    }

    //returniert bei Erfolg Kunden oder Mitarbeiter
    public Nutzer einloggen(String login, String passwort) throws LoginFehlgeschlagenException{
        return (eingeloggterNutzer = meineNutzer.einloggen(login, passwort));
    }

    public void ausloggen(){
        eingeloggterNutzer = null;
    }

    public Warenkorbartikel hinzufuegen(Artikel a, Nutzer n) throws ArtikelNichtVerfuegbarException, ArtikelExistiertBereitsException {
        return meinWarenkorb.hinzufuegen(a, n);
    }

    public Vector gibWarenkorbArtikel() {
        return meinWarenkorb.getWarenkorb();
    }

}

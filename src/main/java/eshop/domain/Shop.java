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
    private NutzerVerwaltung meineNutzer;
    private Nutzer eingeloggterNutzer;

    public Shop(String datei) throws IOException {
        this.datei = datei;
        meineArtikel = new ShopVerwaltung();
        meineArtikel.liesDaten(datei+"_S.txt");
        meineNutzer = new NutzerVerwaltung();
    }

    /**
     * Gibt eingeloggten Nutzer wieder.
     * @return eingeloggterNutzer
     */
    public Nutzer getEingeloggterNutzer(){
        return eingeloggterNutzer;
    }

    /**
     * Gibt alle Artikel wieder.
     * @return meineArtikel.getArtikelBestand()
     */
    public Vector gibAlleArtikel() {
        return meineArtikel.getArtikelBestand();
    }

    /**
     * Gibt Menge der Artikel im Shop wieder.
     * @return meineArtikel.getArtikelBestand().size()
     */
    public int artikelMenge() {
        return meineArtikel.getArtikelBestand().size();
    }

    /**
     * Sucht nach Artikelbezeichnung und gibt entsprechende Artikel in einem Vector zurueck.
     * @param bezeichnung
     * @return Vector<Artikel>
     */
    public Vector<Artikel> sucheNachBezeichnung(String bezeichnung) {
        return meineArtikel.sucheArtikel(bezeichnung);
    }

    /**
     * Fuegt Artikel im Shop hinzu.
     * @param bezeichnung
     * @param marke
     * @param bestand
     * @param preis
     * @return Artikel
     * @throws ArtikelExistiertBereitsException
     */
    public Artikel fuegeArtikelEin(String bezeichnung, String marke, int bestand, float preis) throws ArtikelExistiertBereitsException {
        boolean verfuegbar;
        int nummer = artikelMenge() + 1;
        Artikel a = new Artikel(bezeichnung, marke, nummer, preis, bestand);
        if(bestand >= 1) { a.setVerfuegbar(true); }
        meineArtikel.einfuegen(a);
        return a;
    }

    /**
     * Loescht Artikel aus meineArtikel.
     * @param nummer
     */
    public void loescheArtikel(int nummer) {
        Artikel a = meineArtikel.sucheArtikelNummer(nummer);
        if (a != null) {
            meineArtikel.loeschen(a);
        }
        //TODO: Ereignisverwaltung
    }

    /**
     * Speichert Artikel in .txt Datein
     * @throws IOException
     */
    public void schreibeArtikel() throws IOException {
        meineArtikel.schreibeDaten(datei+"_S.txt");
    }

    /**
     * Gibt Anzahl der Nutzer zurueck.
     * @return int Nutzeranzahl
     */
    public int nutzerAnzahl() {
        return meineNutzer.alleNutzer.size();
    }

    /**
     * Nutzer ueber NutzerVerwaltung registrieren.
     * @param nutzer
     * @throws BenutzerExistiertBereitsException
     */
    public void registrieren(Nutzer nutzer) throws BenutzerExistiertBereitsException{
        meineNutzer.registrieren(nutzer);
    }

    /**
     * Gibt Nutzer als eingeloggten Nutzer wieder.
     * @param login
     * @param passwort
     * @return
     * @throws LoginFehlgeschlagenException
     */
    public Nutzer einloggen(String login, String passwort) throws LoginFehlgeschlagenException{
        return (eingeloggterNutzer = meineNutzer.einloggen(login, passwort));
    }

    /**
     * Nutzer wird ausgeloggt.
     * eingeloggterNutzer = null;
     */
    public void ausloggen(){
        eingeloggterNutzer = null;
    }

}

package eshop.domain;

import eshop.domain.exceptions.*;
import eshop.valueobjects.*;

import java.io.IOException;
import java.util.List;


public class Shop {

    private String datei = "";
    private ShopVerwaltung meineArtikel;
    private NutzerVerwaltung meineNutzer;
    private WarenkorbVerwaltung meinWarenkorb;
    private EreignisVerwaltung ereignisVw;
    private Nutzer eingeloggterNutzer;

    /**
     * Delegiert Aufgaben an ShopVerwaltung, NutzerVerwaltung und WarenkorbVerwaltung.
     * @param datei
     * @throws IOException
     */
    public Shop(String datei) throws IOException {
        this.datei = datei;

        meineArtikel = new ShopVerwaltung();
        meineArtikel.liesDaten(datei+"_S.txt");

        meinWarenkorb = new WarenkorbVerwaltung(meineArtikel);

        meineNutzer = new NutzerVerwaltung();
        meineNutzer.liesDaten(datei+"_BENUTZER.txt");

        ereignisVw = new EreignisVerwaltung();
        ereignisVw.liesDaten(datei+"_EREIGNIS.txt");

    }

    // Ab hier: Funktionen fuer Nutzer

    /**
     * Nutzer ueber NutzerVerwaltung registrieren.
     * @param nutzer
     * @throws BenutzerExistiertBereitsException
     */
    public void registrieren(Nutzer nutzer) throws BenutzerExistiertBereitsException, IOException {
        meineNutzer.registrieren(nutzer);
        schreibeNutzer();
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

    /**
     * Gibt eingeloggten Nutzer wieder.
     * @return eingeloggterNutzer
     */
    public Nutzer getEingeloggterNutzer(){
        return eingeloggterNutzer;
    }

    /**
     * Gibt Anzahl der Nutzer zurueck.
     * @return int Nutzeranzahl
     */
    public int nutzerAnzahl() {
        return meineNutzer.alleNutzer.size();
    }

    public void schreibeNutzer() throws IOException {
        meineNutzer.schreibeNutzer(datei + "_BENUTZER.txt");
    }

    // Ab hier: Funktionen fuer Artikel

    /**
     * Gibt alle Artikel wieder.
     * @return meineArtikel.getArtikelBestand()
     */
    public List<Artikel> gibAlleArtikel() {
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
     * Sucht nach Artikelbezeichnung und gibt entsprechende Artikel in einer Liste zurueck.
     * @param bezeichnung
     * @return List<Artikel>
     */
    public List<Artikel> sucheNachBezeichnung(String bezeichnung) {
        return meineArtikel.sucheArtikel(bezeichnung);
    }

    /**
     * Gibt Artikel mit bestimmter Artikelnummer zurueck.
     * @param nr
     * @return Artikel
     */
    public Artikel sucheNachNummer(int nr) {
        return meineArtikel.sucheArtikelNummer(nr);
    }

    public void aendereBestand(int nummer, int menge) {
        meineArtikel.sucheArtikelNummer(nummer).setBestand(menge);
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
    public Artikel fuegeArtikelEin(String bezeichnung, String marke, int bestand, float preis) throws ArtikelExistiertBereitsException, IOException {
        boolean verfuegbar;
        int nummer = artikelMenge() + 1;
        Artikel a = new Artikel(bezeichnung, marke, nummer, preis, bestand);
        if(bestand >= 1) { a.setVerfuegbar(true); }
        meineArtikel.einfuegen(a);
        String typ = "artikeleinfuegen";
        ereignisVw.erstelleEreignis(eingeloggterNutzer, a, bestand, typ);
        schreibeEreignis();
        return a;
    }

    /**
     * Loescht Artikel aus meineArtikel.
     * @param nummer
     */
    public void loescheArtikel(int nummer) throws IOException {
        Artikel a = meineArtikel.sucheArtikelNummer(nummer);
        if (a != null) {
            meineArtikel.loeschen(a);
            int menge = 0;
            String typ = "artikelloeschen";
            ereignisVw.erstelleEreignis(eingeloggterNutzer, a, menge, typ);
            schreibeEreignis();
        }
    }

    /**
     * Speichert Artikel in .txt Datein
     * @throws IOException
     */
    public void schreibeArtikel() throws IOException {
        meineArtikel.schreibeDaten(datei+"_S.txt");
    }

    //Ab hier: Funktionen fuer Warenkorb

    /**
     * Gibt Warenkorb von Kunde wieder.
     * @return Liste<Artikel> mit Artikeln im Warenkorb.
     */
    public List<Artikel> warenkorbAnzeigen() {
        if (eingeloggterNutzer instanceof Kunde) {
            return meinWarenkorb.zeigeWarenkorb(((Kunde) eingeloggterNutzer).getWarenkorb());
        } else {
            System.out.println("Kein Warenkorb vorhanden!");
            return null;
        }
    }

    /**
     *Fuegt Artikel mit Nummer und Menge im Warenkorb hinzu.
     * @param nummer
     * @param menge
     * @throws ArtikelExistiertNichtException
     * @throws ArtikelBestandZuWenigException
     */
    public void artikelInWarenkorb(int nummer, int menge) throws ArtikelExistiertNichtException, ArtikelBestandZuWenigException {
        Artikel artikel = meineArtikel.sucheArtikelNummer(nummer);
        if (artikel == null) {
            throw new ArtikelExistiertNichtException(nummer);
        } else if (artikel.getBestand() < menge+((Kunde) eingeloggterNutzer).getWarenkorb().getMenge(nummer)) {
            throw new ArtikelBestandZuWenigException();
        } else
            meinWarenkorb.artikelInWarenkorb(((Kunde) eingeloggterNutzer).getWarenkorb(), artikel.getArtikelNummer(), menge);
    }

    public void bestandImWarenkorbAendern(int nummer, int menge) throws ArtikelExistiertNichtException, ArtikelBestandZuWenigException {
        Artikel artikel = meineArtikel.sucheArtikelNummer(nummer);
        if (artikel == null) {
            throw new ArtikelExistiertNichtException(nummer);
        } else {
            if (artikel.getBestand() < menge + ((Kunde) eingeloggterNutzer).getWarenkorb().getMenge(nummer)) {
                throw new ArtikelBestandZuWenigException();
            } else {
                meinWarenkorb.bestandImWarenkorb(((Kunde) eingeloggterNutzer).getWarenkorb(), artikel.getArtikelNummer(), menge);

                if (((Kunde) eingeloggterNutzer).getWarenkorb().getMenge(nummer) <= 0) {
                    artikelAusWarenkorbNehmen(nummer);
                }
            }
        }
    }

    /**
     * Artikel aus Warenkorb loeschen
     * @param nummer
     */
    public void artikelAusWarenkorbNehmen(int nummer) {
        meineArtikel.sucheArtikelNummer(nummer);
        meinWarenkorb.artikelAusWarenkorb(((Kunde) eingeloggterNutzer).getWarenkorb(), nummer);
    }

    public void warenkorbKaufen() throws IOException {
        meinWarenkorb.warenkorbKaufen(((Kunde) eingeloggterNutzer).getWarenkorb(), ((Kunde) eingeloggterNutzer));
    }

    /**
     * Loescht den Warenkorb
     */
    public void warenKorbLoeschen() {
        meinWarenkorb.warenkorbLoeschen(((Kunde) eingeloggterNutzer).getWarenkorb());
    }

    //Ab hier: Ereignisse

    public void schreibeEreignis() throws IOException {
        ereignisVw.schreibeEreignisse(datei+"_EREIGNIS.txt", eingeloggterNutzer);
    }

    public List<Ereignis> gibEreignisListe(){
        return ereignisVw.gibEreignisListe();
    }

}

package eshop.ui.cui;

import eshop.domain.Shop;
import eshop.domain.exceptions.ArtikelExistiertNichtException;
import eshop.domain.exceptions.ArtikelNichtVerfuegbarException;
import eshop.domain.exceptions.EShopException;

import eshop.valueobjects.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

public class ShopCUI {

    private Shop shop;
    private BufferedReader in;

    public ShopCUI(String datei) throws IOException {
        shop = new Shop(datei);
        // Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    private Nutzer eingeloggterNutzer(){
        return shop.getEingeloggterNutzer();
    }

    private void gibMenueAus() {
        if (eingeloggterNutzer() == null) {
            gibStartMenueAus();
        }else{
            gibNutzerMenueAus(eingeloggterNutzer());
        }
    }

    private void gibNutzerMenueAus(Nutzer nutzer){
        if (nutzer instanceof Mitarbeiter){
            gibNutzerMenueAus((Mitarbeiter)nutzer);
        }else if (nutzer instanceof Kunde){
            gibNutzerMenueAus((Kunde)nutzer);
        }else{
            throw new RuntimeException("Unbekannte Art von Nutzer");
        }
    }

    private void gibNutzerMenueAus(Mitarbeiter nutzer){
        System.out.print("\n Befehle: ");
        System.out.print("         \n  ---------------------");
        System.out.print("         \n  Artikel anzeigen:  'a'");
        System.out.print("         \n  Artikel löschen:  'd'");
        System.out.print("         \n  Artikel einfügen: 'e'");
        System.out.print("         \n  Artikel suchen:  'f'");
        System.out.print("         \n  Artikelmenge ausgeben: 'm'");
        System.out.print("         \n  Mitarbeiter erstellen:  't'");
        System.out.print("         \n  Daten sichern:  's'");
        System.out.print("         \n  ---------------------");
        System.out.print("         \n  Ausloggen:  'x'");
        System.out.println("         \n  Beenden:        'q'");
        System.out.flush(); // ohne NL ausgeben
    }

    private void gibNutzerMenueAus(Kunde nutzer) {
        System.out.print("\n Befehle: ");
        System.out.print("         \n  ---------------------");
        System.out.print("         \n  Artikel anzeigen:  'a'");
        System.out.print("         \n  Artikel suchen:  'f'");
        System.out.print("         \n  Artikel in Warenkorb:  'w'");
        System.out.print("         \n  Warenkorb anzeigen: 'm'");
        System.out.print("         \n  Artikel aus Warenkorb nehmen: 'd'");
        System.out.print("         \n  Warenkorb loeschen: 'l'");
        System.out.print("         \n  Bezahlen:  's'");
        System.out.print("         \n  ---------------------");
        System.out.print("         \n  Ausloggen:  'x'");
        System.out.println("         \n  Beenden:        'q'");
        System.out.flush(); // ohne NL ausgeben
    }

    private void gibStartMenueAus() {
        System.out.print("\n Befehle: \n   Login: 'l'");
        System.out.print("         \n   Registrieren: 'r'");
        System.out.print("         \n  ---------------------");
        System.out.println("         \n  Beenden:        'q'");
        System.out.flush(); // ohne NL ausgeben
    }

    private String liesEingabe(String promptText) throws IOException {
        // einlesen von Konsole
        String input = "";
        while (input.length() == 0){
            System.out.print(promptText+" > ");
            input = in.readLine().trim();
        }
        return input;
    }

    private int liesEingabeGanzzahl(String promptText) throws IOException {
        // einlesen von Konsole
        int nummer = -1;
        while (true){
            try{
                nummer = Integer.parseInt(liesEingabe(promptText));
                if (nummer >= 0){
                    break;
                }
            } catch (NumberFormatException e){
                //nothing to do here
            }
            System.out.println("Bitte eine positive Ganzzahl eingeben...");
        }
        return nummer;
    }

    protected void verarbeiteEingabe(String line) throws EShopException, IOException {
        if (eingeloggterNutzer() == null) {
            verarbeiteStartMenueEingabe(line);
        } else {
            verarbeiteNutzerEingabe(line, eingeloggterNutzer());
        }
    }

    private void verarbeiteNutzerEingabe(String line, Nutzer nutzer) throws EShopException, IOException {
        if (nutzer instanceof Mitarbeiter){
            verarbeiteNutzerEingabe(line,(Mitarbeiter)nutzer);
        }else if (nutzer instanceof Kunde){
            verarbeiteNutzerEingabe(line,(Kunde)nutzer);
        }else{
            throw new RuntimeException("Unbekannte Art von Nutzer");
        }
    }

    //Mitarbeiter Eingabe
    private void verarbeiteNutzerEingabe(String line, Mitarbeiter nutzer) throws EShopException, IOException {
        int nummer;
        int bestand;
        int preis;
        List<Artikel> liste;
        String marke;
        String bezeichnung;
        String login;
        String passwort;

        // Eingabe bearbeiten:
        switch (line) {
            case "a":
                // Artikel ausgeben
                liste = shop.gibAlleArtikel();
                gibArtikellisteAus(liste);
                break;
            case "m":
                // Artikelmenge ausgeben
                System.out.print(shop.artikelMenge() + "\n");
                break;
            case "d":
                // Artikel loeschen Eingabe
                nummer = liesEingabeGanzzahl("Artikelnummer");
                shop.loescheArtikel(nummer);
                break;
            case "e":
                // Artikel einfuegen Eingabe
                bezeichnung = liesEingabe("Artikelbezeichnung");
                marke = liesEingabe("Marke");
                bestand = liesEingabeGanzzahl("Bestand");
                preis = liesEingabeGanzzahl("Preis");
                shop.fuegeArtikelEin(bezeichnung, marke, bestand, preis);
                System.out.println("Einfügen ok");
                break;
            case "f":
                // Artikel suchen und ausgeben
                bezeichnung = liesEingabe("Artikelbezeichnung");
                liste = shop.sucheNachBezeichnung(bezeichnung);
                gibArtikellisteAus(liste);
                break;
            case "s":
                shop.schreibeArtikel();
                shop.schreibeNutzer();
                shop.schreibeEreignis();
                System.out.println("Die Artikel und Nutzer wurden gespeichert.");
                break;
            case "t":
                String vorname = liesEingabe("Vorname");
                String nachname = liesEingabe("Nachname");
                login = liesEingabe("Benutzername");
                passwort = liesEingabe("Passwort");
                //Mitarbeiter registrieren
                shop.registrieren(new Mitarbeiter(vorname, nachname, login, passwort));
                break;
            case "x":
                shop.ausloggen();
                break;
            case "q":
                shop.schreibeNutzer();
                shop.schreibeArtikel();
                shop.schreibeEreignis();
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }

    //Kunden Eingabe
    private void verarbeiteNutzerEingabe(String line, Kunde nutzer) throws EShopException, IOException {
        List<Artikel> liste;
        String bezeichnung;
        int nummer;
        int menge;

        switch (line){
            case "a":
                // Artikel ausgeben
                liste = shop.gibAlleArtikel();
                gibArtikellisteAus(liste);
                break;
            case "f":
                // Artikel suchen und ausgeben
                bezeichnung = liesEingabe("Artikelbezeichnung");
                liste = shop.sucheNachBezeichnung(bezeichnung);
                gibArtikellisteAus(liste);
                break;
            case "w":
                //Artikel in den Warenkorb
                System.out.println("Artikel in den Warenkorb");
                nummer = liesEingabeGanzzahl("Artikelnummer");
                menge = liesEingabeGanzzahl("Menge");
                try {
                    shop.artikelInWarenkorb(nummer, menge);
                } catch(ArtikelExistiertNichtException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "m":
                //Warenkorb ausgeben
                System.out.println("Im Warenkorb: ");
                liste = shop.warenkorbAnzeigen();
                gibArtikellisteAus(liste);
                break;
            case "d":
                //Artikel aus Warenkorb nehmen
                nummer = liesEingabeGanzzahl("Artikelnummer: ");
                shop.artikelAusWarenkorbNehmen(nummer);
                break;
            case "l":
                //Warenkorb loeschen
                shop.warenKorbLoeschen();
                break;
            case "s":
                //Warenkorb kaufen
                shop.warenkorbKaufen();
                break;
            case "x":
                shop.ausloggen();
                break;
            case "q":
                shop.schreibeNutzer();
                shop.schreibeArtikel();
                shop.schreibeEreignis();
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }

    private void verarbeiteStartMenueEingabe(String line) throws EShopException, IOException {
        String login;
        String passwort;
        String adresse;

        switch (line) {
            //Login verarbeiten
            case "l":
                login = liesEingabe("Benutzername");
                passwort = liesEingabe("Passwort");
                Nutzer nutzer = shop.einloggen(login, passwort);
                System.out.print(nutzer.getLogin() + " wurde eingeloggt.");
                break;
            //Registrierung verarbeiten
            case "r":
                String vorname = liesEingabe("Vorname");
                String nachname = liesEingabe("Nachname");
                login = liesEingabe("Benutzername");
                passwort = liesEingabe("Passwort");
                adresse = liesEingabe("Adresse");
                //Kunde registrieren
                shop.registrieren(new Kunde(vorname,nachname,login,passwort,adresse));
                shop.schreibeNutzer();
                System.out.println("Nutzer wurde erstellt.");
                break;
            case "q":
                shop.schreibeNutzer();
                shop.schreibeArtikel();
                shop.schreibeEreignis();
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }

    private void gibArtikellisteAus(List liste) {
        if (liste.isEmpty()) {
            System.out.println("Liste ist leer.");
        } else {
            for (Object artikel : liste) {
                System.out.println(artikel);
            }
        }
    }

    public void run() {
        // Variable für Eingaben von der Konsole
        String input = "";

        // Hauptschleife der Benutzungsschnittstelle
        do {

            gibMenueAus();

            try {

                input = liesEingabe("");
                verarbeiteEingabe(input);

            } catch (EShopException eshopException){

                System.out.println("\n[!] " + eshopException.getMessage() + "\n");

            } catch (IOException ioException) {

                ioException.printStackTrace();

            }

        } while (!input.equals("q"));
    }

    public static void main(String[] args) {
        ShopCUI cui;

        try {
            cui = new ShopCUI("SHOP");
            cui.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

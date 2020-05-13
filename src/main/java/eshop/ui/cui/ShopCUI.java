package eshop.ui.cui;

import eshop.domain.Shop;
import eshop.domain.exceptions.ArtikelNichtVerfuegbarException;
import eshop.domain.exceptions.EShopException;

import eshop.valueobjects.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ShopCUI {

    private Shop shop;
    private Warenkorbartikel meinWarenkorbartikel;
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
        System.out.print("\n Befehle: \n  Artikel ausgeben:  'a'");
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
        System.out.print("\n Befehle: \n  Artikel anzeigen:  'a'");
        System.out.print("         \n  Artikel in Warenkorb:  'w'");
        System.out.print("         \n  Warenkorb ausgeben: 'm'");
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

    private void verarbeiteNutzerEingabe(String line, Mitarbeiter nutzer) throws EShopException, IOException {
        int nummer;
        int bestand;
        Vector liste;
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
                bestand = liesEingabeGanzzahl("Bestand");
                bezeichnung = liesEingabe("Artikelbezeichnung");
                marke = liesEingabe("Marke");
                // Die Bibliothek das Buch löschen lassen:
                shop.loescheArtikel(bezeichnung, marke, bestand, nummer);
                break;
            case "e":
                // Artikel einfuegen Eingabe
                bezeichnung = liesEingabe("Artikelbezeichnung");
                marke = liesEingabe("Marke");
                bestand = liesEingabeGanzzahl("Bestand");
                shop.fuegeArtikelEin(bezeichnung, marke, bestand);
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
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }

    private void verarbeiteNutzerEingabe(String line, Kunde nutzer) throws EShopException, IOException {
        Vector<Artikel> liste;
        String bezeichnung;
        int menge;

        switch (line){
            case "a":
                // Artikel ausgeben
                liste = shop.gibAlleArtikel();
                gibArtikellisteAus(liste);
                break;
            case "w":
                //Artikel in den Warenkorb
                System.out.println("Artikel in den Warenkorb");
                bezeichnung = liesEingabe("Artikelbezeichnung");
                liste = shop.sucheNachBezeichnung(bezeichnung);
                try {
                    Warenkorbartikel warenkorbartikel = shop.hinzufuegen(liste.get(0), eingeloggterNutzer());
                    System.out.println("Der Artikel " + warenkorbartikel.getArtikel() + " wurde von " + warenkorbartikel.getNutzer().getLogin() + " hinzugefuegt.");
                } catch(ArtikelNichtVerfuegbarException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "m":
                //Warenkorb ausgeben
                System.out.println("Im Warenkorb: ");
                liste = shop.gibWarenkorbArtikel();
                gibArtikellisteAus(liste);
                break;
            case "s":
                System.out.println("Funktion noch nicht implementiert. Sorry...");
                break;
            case "x":
                shop.ausloggen();
                break;
            case "q":
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }

    private void verarbeiteStartMenueEingabe(String line) throws EShopException, IOException {
        String login;
        String passwort;
        int nummer;

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
                //Kunde registrieren
                shop.registrieren(new Kunde(vorname,nachname,login,passwort));
                break;
            case "q":
                break;
            default:
                System.out.println("Ungültige Eingabe");
        }

    }

    private void gibArtikellisteAus(Vector liste) {
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

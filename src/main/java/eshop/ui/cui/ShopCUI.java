package eshop.ui.cui;

import eshop.domain.NutzerVerwaltung;
import eshop.domain.Shop;
import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.BenutzerExistiertBereitsException;
import eshop.domain.exceptions.LoginFehlgeschlagenException;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ShopCUI {

    private Shop shop;
    private BufferedReader in;

    private Nutzer eingeloggterNutzer;
    private Mitarbeiter eingeloggterMitarbeiter;
    private Kunde eingeloggterKunde;

    public ShopCUI(String datei) throws IOException {
        // die Bib-Verwaltung erledigt die Aufgaben,
        // die nichts mit Ein-/Ausgabe zu tun haben
        shop = new Shop(datei);

        // Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    private void gibMenueAus() {
        if(eingeloggterNutzer == null) {
            gibAusgeloggtesMenueAus();
        }
        else if(eingeloggterNutzer instanceof Mitarbeiter) {
            gibMitarbeiterMenueAus();
        } else {
            gibShopMenueAus();
        }
    }

    private void gibMitarbeiterMenueAus() {
        System.out.print("\n Befehle: \n  Artikel ausgeben:  'a'");
        System.out.print("         \n  Artikel löschen:  'd'");
        System.out.print("         \n  Artikel einfügen: 'e'");
        System.out.print("         \n  Artikel suchen:  'f'");
        System.out.print("         \n  Artikelmenge ausgeben: 'm'");
        System.out.print("         \n  Mitarbeiter erstellen:  't'");
        System.out.print("         \n  Daten sichern:  's'");
        System.out.print("         \n  ---------------------");
        System.out.println("         \n  Beenden:        'q'");
        System.out.print("> "); // Prompt
        System.out.flush(); // ohne NL ausgeben
    }

    private void gibShopMenueAus() {
        System.out.print("\n Befehle: \n  Artikel anzeigen:  'a'");
        System.out.print("         \n  Artikel in Warenkorb:  'w'");
        System.out.print("         \n  Warenkorb ausgeben: 'm'");
        System.out.print("         \n  Bezahlen:  's'");
        System.out.print("         \n  ---------------------");
        System.out.println("         \n  Beenden:        'q'");
        System.out.print("> "); // Prompt
        System.out.flush(); // ohne NL ausgeben
    }

    private void gibAusgeloggtesMenueAus() {
        System.out.print("\n Befehle: \n   Login: 'l'");
        System.out.print("         \n   Registrieren: 'r'");
        System.out.print("         \n  ---------------------");
        System.out.println("         \n  Beenden:        'q'");
        System.out.print("> "); // Prompt
        System.out.flush(); // ohne NL ausgeben
    }

    private String liesEingabe() throws IOException {
        // einlesen von Konsole
        return in.readLine();
    }

    private void verarbeiteEingeloggteEingabe(String line) throws IOException {
        // TODO: Eingabeverarbeitung
    }

    private void verarbeiteAusgeloggteEingabe(String line) throws IOException {
        String login;
        String passwort;
        int nummer;

        switch (line) {
            //Login verarbeiten
            case "l":
                System.out.print("Benutzername > ");
                login = liesEingabe();
                System.out.print("Passwort > ");
                passwort = liesEingabe();
                try {
                    eingeloggterKunde = shop.einloggen(login, passwort);
                    System.out.print(eingeloggterKunde.getLogin() + "wurde eingeloggt.");
                } catch (LoginFehlgeschlagenException e) {
                    System.out.print(e.getMessage());
                }
                try {
                    eingeloggterMitarbeiter = shop.einloggen(login, passwort, true);
                    System.out.print("Der Mitarbeiter " + eingeloggterMitarbeiter.getLogin() + "wurde eingeloggt.");
                } catch (LoginFehlgeschlagenException e) {
                    System.out.print(e.getMessage());
                }
                break;
            //Registrierung verarbeiten
            case "r":
                System.out.print("Vorname > ");
                String vorname = liesEingabe();
                System.out.print("Nachname > ");
                String nachname = liesEingabe();
                System.out.print("Benutzername > ");
                login = liesEingabe();
                System.out.print("Passwort > ");
                passwort = liesEingabe();
                nummer = shop.kundenAnzahl() + 1;
                try {
                    //Kunde registrieren
                    shop.registrieren(vorname, nachname, login, passwort, nummer);
                } catch (BenutzerExistiertBereitsException e) {
                    System.out.println(e.getMessage());
                }
        }
    }

    private void verarbeiteMitarbeiterEingabe(String line) throws IOException {
        String nummer;
        int nr;
        String bestand;
        int best;
        String marke;
        String bezeichnung;
        Vector liste;
        String login;
        String passwort;
        boolean isMitarbeiter;

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
                System.out.print("Artikelnummer > ");
                nummer = liesEingabe();
                nr = Integer.parseInt(nummer);
                System.out.print("Bestand > ");
                bestand = liesEingabe();
                best = Integer.parseInt(bestand);
                System.out.print("Artikelbezeichnung  > ");
                bezeichnung = liesEingabe();
                System.out.print("Marke > ");
                marke = liesEingabe();
                // Die Bibliothek das Buch löschen lassen:
                shop.loescheArtikel(bezeichnung, marke, best, nr);
                break;
            case "e":
                // Artikel einfuegen Eingabe
                nr = shop.artikelMenge() + 1; // Artikelnummer wird anhand der Menge autom. eingefuegt.
                System.out.print("Artikelbezeichnung  > ");
                bezeichnung = liesEingabe();
                System.out.print("Marke > ");
                marke = liesEingabe();
                System.out.print("Bestand > ");
                bestand = liesEingabe();
                best = Integer.parseInt(bestand);
                try {
                    shop.fuegeArtikelEin(bezeichnung, marke, best, nr);
                    System.out.println("Einfügen ok");
                } catch (ArtikelExistiertBereitsException e) {
                    // Hier Fehlerbehandlung...
                    System.out.println("Fehler beim Einfügen");
                    e.printStackTrace();
                }
                break;
            case "f":
                // Artikel suchen und ausgeben
                System.out.print("Artikelbezeichnung  > ");
                bezeichnung = liesEingabe();
                liste = shop.sucheNachBezeichnung(bezeichnung);
                gibArtikellisteAus(liste);
                break;
            case "s":
                shop.schreibeArtikel();
                break;
            case "t":
                System.out.print("Vorname > ");
                String vorname = liesEingabe();
                System.out.print("Nachname > ");
                String nachname = liesEingabe();
                System.out.print("Benutzername > ");
                login = liesEingabe();
                System.out.print("Passwort > ");
                passwort = liesEingabe();
                nr = shop.mitarbeiterAnzahl() + 1;
                isMitarbeiter = true;
                try {
                    //Kunde registrieren
                    shop.registrieren(vorname, nachname, login, passwort, nr, isMitarbeiter);
                } catch (BenutzerExistiertBereitsException e) {
                    System.out.println(e.getMessage());
                }
        }
    }

    public void verarbeiteEingabe(String line) throws IOException {
        if (eingeloggterNutzer == null) {
            verarbeiteAusgeloggteEingabe(line);
        } else if (eingeloggterNutzer instanceof Mitarbeiter) {
            verarbeiteMitarbeiterEingabe(line);
        } else {
            verarbeiteEingeloggteEingabe(line);
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
                input = liesEingabe();
                verarbeiteEingabe(input);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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

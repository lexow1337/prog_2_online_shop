package eshop.ui.cui;

import eshop.domain.Shop;
import eshop.domain.exceptions.ArtikelExistiertBereitsException;
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
            // TODO: gib Mitarbeitermenue aus
        } else {
            // TODO: gib Shop-Menue aus
        }
    }

    private void gibEingeloggtesMenueAus() {
        System.out.print("Befehle: \n  Artikel ausgeben:  'a'");
        System.out.print("         \n  Artikel löschen:  'd'");
        System.out.print("         \n  Artikel einfügen: 'e'");
        System.out.print("         \n  Artikel suchen:  'f'");
        System.out.print("         \n  Artikelmenge ausgeben: 'm'");
        System.out.print("         \n  Daten sichern:  's'");
        System.out.print("         \n  ---------------------");
        System.out.println("         \n  Beenden:        'q'");
        System.out.print("> "); // Prompt
        System.out.flush(); // ohne NL ausgeben
    }

    private void gibAusgeloggtesMenueAus() {
        System.out.print("Befehle: \n   Login: 'l'");
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

    private void verarbeiteEingeloggteEingabe() throws IOException {
        // TODO: Eingabeverarbeitung
    }

    private void verarbeiteAusgeloggteEingabe() throws IOException {
        // TODO: Eingabeverarbeitung
    }

    private void verarbeiteEingabe(String line) throws IOException {
        String nummer;
        int nr;
        String bestand;
        int best;
        String marke;
        String bezeichnung;
        Vector liste;

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

package eshop.ui.cui;

import eshop.domain.Shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShopCUI {

    private Shop shop;
    private BufferedReader br;

    public ShopCUI(String datei) throws IOException {
        // die Bib-Verwaltung erledigt die Aufgaben,
        // die nichts mit Ein-/Ausgabe zu tun haben
        shop = new Shop(datei);

        // Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    private void gibMenueAus() {
        System.out.print("Befehle: \n  Artikel ausgeben:  'a'");
        System.out.print("         \n  Artikel löschen:  'd'");
        System.out.print("         \n  Artikel einfügen: 'e'");
        System.out.print("         \n  Artikel suchen:  'f'");
        System.out.print("         \n  Daten sichern:  's'");
        System.out.print("         \n  ---------------------");
        System.out.println("         \n  Beenden:        'q'");
        System.out.print("> "); // Prompt
        System.out.flush(); // ohne NL ausgeben
    }

    public static void main(String[] args) {
        ShopCUI cui;
    }
}

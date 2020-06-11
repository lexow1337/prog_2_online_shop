package eshop.valueobjects;

import eshop.domain.ShopVerwaltung;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Rechnung {
    private Kunde kunde;
    private Warenkorb warenkorb;
    private ArrayList<Artikel> artikels;
    private String date;
    private Date dt = new Date();
    ShopVerwaltung meineArtikel;

    public Rechnung(Kunde kunde, Warenkorb warenkorb, ShopVerwaltung meineArtikel) {
        this.kunde = kunde;
        this.warenkorb = warenkorb;
        this.meineArtikel = meineArtikel;
    }

    public void rechnungAusgeben() {
        String rechnung = "";
        for (Map.Entry<Integer, Integer> entry : warenkorb.getList().entrySet()) {
            Artikel artikel = meineArtikel.sucheArtikelNummer(entry.getKey());
            rechnung += "Nummer: " + artikel.getArtikelNummer()
                    + "\t Bezeichnung: " + artikel.getBezeichnung()
                    + "\t Anzahl im Warenkorb: " + warenkorb.countArtikel(artikel)
                    + "\t Anzahl im Bestand: " + artikel.getBestand() + "\n";
        }
        System.out.println(rechnung);
        JOptionPane.showMessageDialog(null, "Sie haben folgende Artikel gekauft: " + rechnung);
    }

}

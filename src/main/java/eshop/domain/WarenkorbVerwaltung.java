package eshop.domain;

import eshop.valueobjects.Artikel;
import eshop.valueobjects.Warenkorb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarenkorbVerwaltung {

    private ShopVerwaltung meineArtikel;

    public WarenkorbVerwaltung(ShopVerwaltung meineArtikel) {
        this.meineArtikel = meineArtikel;
    }

    /**
     * Artikel aus Warenkorb entfernen.
     * @param warenkorb
     * @param nummer
     * @return true / false
     */
    public boolean artikelAusWarenkorb(Warenkorb warenkorb, int nummer) {
        return warenkorb.deleteArtikel(nummer);
    }

    /**
     * Artikel in Warenkorb einfuegen.
     * @param warenkorb
     * @param artikel
     * @param anzahl
     */
    public void artikelInWarenkorb(Warenkorb warenkorb, Artikel artikel, int anzahl) {
        warenkorb.addArtikel(artikel, anzahl);
    }

    /**
     * Bestand im Warenkorb aendern.
     * @param warenkorb
     * @param nummer
     * @param anzahl
     */
    public void bestandImWarenkorb(Warenkorb warenkorb, int nummer, int anzahl) {
        int menge = warenkorb.getMenge(nummer);
        System.out.println(menge);
        menge = anzahl;
        warenkorb.setArtikel(nummer, anzahl);
    }

    /**
     * Gibt eine Kopie des Warenkorb zurueck.
     * @param warenkorb
     * @return List<Artikel> warenkorbArtikelListe
     */
    public List<Artikel> zeigeWarenkorb(Warenkorb warenkorb) {
        HashMap<Integer, Integer> warenkorbArtikel = warenkorb.getList();
        List<Artikel> warenkorbArtikelListe = new ArrayList<>();

        for (int nummer : warenkorbArtikel.keySet()) {
            Artikel artikel = meineArtikel.sucheArtikelNummer(nummer);
            if (artikel == null) {
                continue;
            }
            int menge = warenkorbArtikel.get(nummer);
            Artikel artikelcopy = new Artikel(artikel.getBezeichnung(), artikel.getMarke(), artikel.getArtikelNummer(), artikel.getPreis()*menge, menge);
            warenkorbArtikelListe.add(artikelcopy);
        }
        return warenkorbArtikelListe;
    }

    public void warenkorbLoeschen(Warenkorb warenkorb) {
        warenkorb.clearWarenkorb();
    }

}
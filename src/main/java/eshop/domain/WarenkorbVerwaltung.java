package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertNichtException;
import eshop.domain.exceptions.EShopException;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Rechnung;
import eshop.valueobjects.Warenkorb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Artikel in Warenkorb legen.
     * @param warenkorb
     * @param anzahl
     */
    public void artikelInWarenkorb(Warenkorb warenkorb, int nummer, int anzahl) {
        warenkorb.setArtikel(nummer, anzahl);
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

    /**
     * Warenkorb Kaufvorgang.
     * Artikelliste wird aus Warenkorb geladen.
     * Es wird geprueft, ob der Warenkorb leer ist.
     * Wenn Artikel im Warenkorb vorhanden sind, wird die WarenkorbArtikelListe iteriert.
     * Der Artikelbestand im Shop wird um Warenkorbbestand verringert.
     * Rechnung wird erstellt und ausgegeben.
     * Warenkorb clear.
     * @param warenkorb
     * @param kunde
     * @throws IOException
     */
    public void warenkorbKaufen(Warenkorb warenkorb, Kunde kunde) throws IOException {
        HashMap<Integer, Integer> warenkorbArtikelListe = warenkorb.getList();

        if (warenkorbArtikelListe.isEmpty()) {
            System.out.println("Es befinden sich keine Artikel im Warenkorb");
        } else {
            for (HashMap.Entry<Integer, Integer> entry : warenkorbArtikelListe.entrySet()) {
                Artikel artikel = meineArtikel.sucheArtikelNummer(entry.getKey());
                int bestand = artikel.getBestand();
                int warenkorbBestand = entry.getValue();
                artikel.setBestand(bestand -= warenkorbBestand);
                if (artikel.getBestand() <= 0) {
                    try {
                        meineArtikel.veraendereBestand(artikel, 0);
                    } catch (ArtikelExistiertNichtException e) {
                        e.printStackTrace();
                    }
                }
            }
            Rechnung r = new Rechnung(kunde, warenkorb, meineArtikel);
            r.rechnungAusgeben();
            warenkorb.clearWarenkorb();
            System.out.println("Der Warenkorb wurde gekauft.");
        }
    }

    /**
     * Loescht den Warenkorb
     * @param warenkorb
     */
    public void warenkorbLoeschen(Warenkorb warenkorb) {
        warenkorb.clearWarenkorb();
    }

}
package eshop.valueobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Warenkorb {

    private List<Artikel> artikelListe = new Vector<>();

    HashMap<Integer, Integer> list = new HashMap<>();

    /**
     * Fuegt Artikel mit Anzahl im Warenkorb hinzu.
     * Der Preis fuer mehrere Artikel wird errechnet.
     * @param artikel
     * @param anzahl
     */
    public void addArtikel(Artikel artikel, int anzahl) {
        Artikel artikelWk = new Artikel (artikel.getBezeichnung(), artikel.getMarke(), artikel.getArtikelNummer(), artikel.getPreis(), anzahl);
        artikelWk.setPreis(artikel.getPreis() * anzahl);
        artikelListe.add(artikelWk);
    }

    /**
     * Loescht Artikel anhand der Artikelnummer aus dem Warenkorb.
     * @param artikelnummer
     * @return true / false
     */
    public boolean deleteArtikel(int artikelnummer) {
        if (list.containsKey(artikelnummer)) {
            list.remove(artikelnummer);
            return true;
        }
        return false;
    }

    /**
     * Gibt Menge eines Artikels wieder.
     * @param nummer
     * @return menge
     */
    public int getMenge(int nummer) {
        return list.getOrDefault(nummer, 0);
    }

    /**
     * Put Artikel in HashMap.
     * @param nummer
     * @param menge
     */
    public void setArtikel(int nummer, int menge) {
        list.put(nummer, list.getOrDefault(nummer, 0)+menge);
    }

    public HashMap<Integer, Integer> getList() { return list; }

    /**
     * Entfernt alle Objekte aus dem Warenkorb.
     */
    public void clearWarenkorb() {
        artikelListe.clear();
        list.clear();
    }

}

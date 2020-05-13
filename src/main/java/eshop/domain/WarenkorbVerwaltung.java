package eshop.domain;

import eshop.domain.exceptions.ArtikelNichtVerfuegbarException;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Nutzer;
import eshop.valueobjects.Warenkorbartikel;

import java.util.Vector;

public class WarenkorbVerwaltung {

    Vector<Warenkorbartikel> warenkorbArtikel = new Vector();

    private NutzerVerwaltung meineNutzer;
    private ShopVerwaltung meineArtikel;

    public WarenkorbVerwaltung(NutzerVerwaltung _meineNutzer, ShopVerwaltung _meineArtikel) {
        this.meineNutzer = _meineNutzer;
        this.meineArtikel = _meineArtikel;
    }

    public Warenkorbartikel hinzufuegen(Artikel a, Nutzer n) throws ArtikelNichtVerfuegbarException /*ArtikelExistiertBereitsException*/ {

        if (!a.isVerfuegbar()) { throw new ArtikelNichtVerfuegbarException(a, ""); }

        //lif (warenkorbArtikel.contains(a)) { throw new ArtikelExistiertBereitsException(a, " - Bereits im Warenkorb"); }

        Warenkorbartikel neuerWarenkorbartikelArtikel = new Warenkorbartikel(a, n);
        warenkorbArtikel.add(neuerWarenkorbartikelArtikel);

        //TODO: Decrease Artikelbestand
        return neuerWarenkorbartikelArtikel;
    }

    public void entfernen(Artikel einArtikel) {
        warenkorbArtikel.remove(einArtikel);
    }

    public Vector getWarenkorb() {
        return new Vector(warenkorbArtikel);
    }

}

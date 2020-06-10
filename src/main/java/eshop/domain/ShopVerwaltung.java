package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelNichtVerfuegbarException;
import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;
import eshop.valueobjects.Artikel;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ShopVerwaltung {

    private List<Artikel> artikelBestand = new Vector();
    private PersistenceManager pm = new FilePersistenceManager();

    /**
     * Liest Daten aus Datei ueber PersistenzManager.
     * Fuegt Artikel im artikelBestand ein.
     * @param datei
     * @throws IOException
     */
    public void liesDaten(String datei) throws IOException {
        pm.openForReading(datei);
        Artikel einArtikel;
        do {
            einArtikel = pm.ladeArtikel();
            if (einArtikel != null) {
                try {
                    einfuegen(einArtikel);
                } catch (ArtikelExistiertBereitsException e1) {}
            }
        } while (einArtikel != null);
        pm.close();
    }

    /**
     * Schreibt Daten in Datei ueber PersistenzManager.
     * @param datei
     * @throws IOException
     */
    public void schreibeDaten(String datei) throws IOException {
        pm.openForWriting(datei);

        if (!artikelBestand.isEmpty()) {
            Iterator iter = artikelBestand.iterator();
            while (iter.hasNext()) {
                Artikel a = (Artikel) iter.next();
                pm.speichereArtikel(a);
            }
        }
        pm.close();
    }

    /**
     * Artikel in artikelBestand einfuegen.
     * @param einArtikel
     * @throws ArtikelExistiertBereitsException
     */
    public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
        if (artikelBestand.contains(einArtikel)) {
            throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
        }

        artikelBestand.add(einArtikel);
    }

    /**
     * Artikel aus artikelBestand entfernen.
     * @param einArtikel
     */
    public void loeschen(Artikel einArtikel) {
        artikelBestand.remove(einArtikel);
    }

    /**
     * Gibt eine Liste mit Artikeln zurueck, die der Bezeichnung entsprechen.
     * @param bezeichnung
     * @return List<Artikel>
     */
    public List<Artikel> sucheArtikel(String bezeichnung) {
        List<Artikel> suchErg = new Vector();
        Iterator<Artikel> it = artikelBestand.iterator();

        while (it.hasNext()) {
            Artikel artikel = it.next();
            if (artikel.getBezeichnung().equals(bezeichnung)) {
                suchErg.add(artikel);
            }
        }
        return suchErg;
    }

    /**
     * Gibt Artikel anhand Artikelnummer zurueck.
     * @param nummer
     * @return Artikel
     */
    public Artikel sucheArtikelNummer(int nummer) {
        for (Artikel artikel : artikelBestand) {
            if (artikel.getArtikelNummer() == nummer) {
                return artikel;
            }
        }
        return null;
    }

    /**
     * Veraendert Menge eines Artikels im artikelBestand.
     * @param artikel
     * @param menge
     * @throws ArtikelNichtVerfuegbarException
     */
    public void veraendereBestand(Artikel artikel, int menge) throws ArtikelNichtVerfuegbarException {
        if (!artikelBestand.contains(artikel)) {
            throw new ArtikelNichtVerfuegbarException(artikel, "");
        }
        artikel.setBestand(menge);
    }

    /**
     * Verringert Menge eines Artikels im artikelBestand
     * @param artikel
     * @param menge
     * @throws ArtikelNichtVerfuegbarException
     */
    public void verringereBestand(Artikel artikel, int menge) throws ArtikelNichtVerfuegbarException {
        if (!artikelBestand.contains(artikel)) {
            throw new ArtikelNichtVerfuegbarException(artikel, "");
        }
        artikel.setBestand(artikel.getBestand() - menge);
    }

    /**
     * Erhoeht Menge eines Artikels im artikelBestand.
     * @param artikel
     * @param menge
     * @throws ArtikelNichtVerfuegbarException
     */
    public void bestandErhoehen(Artikel artikel, int menge) throws ArtikelNichtVerfuegbarException {
        if (!artikelBestand.contains(artikel)) {
            throw new ArtikelNichtVerfuegbarException(artikel, "");
        }
        artikel.setBestand(artikel.getBestand() - menge);
    }

    /**
     * Gibt eine neue Liste des artikelBestand zurueck.
     * @return Vector(artikelBestand)
     */
    public List<Artikel> getArtikelBestand() {
        return new Vector(artikelBestand);
    }

}

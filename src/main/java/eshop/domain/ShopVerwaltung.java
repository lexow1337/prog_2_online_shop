package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;
import eshop.valueobjects.Artikel;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class ShopVerwaltung {

    private Vector<Artikel> artikelBestand = new Vector();
    private PersistenceManager pm = new FilePersistenceManager();

    public void liesDaten(String datei) throws IOException {
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading(datei);

        Artikel einArtikel;
        do {
            // Artikel-Objekt einlesen
            einArtikel = pm.ladeArtikel();
            if (einArtikel != null) {
                // Artikel in Liste einfügen
                try {
                    einfuegen(einArtikel);
                } catch (ArtikelExistiertBereitsException e1) {
                    // Kann hier eigentlich nicht auftreten,
                    // daher auch keine Fehlerbehandlung...
                }
            }
        } while (einArtikel != null);

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

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

    public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
        if (artikelBestand.contains(einArtikel)) {
            throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
        }

        artikelBestand.add(einArtikel);
    }

    public void loeschen(Artikel einArtikel) {
        artikelBestand.remove(einArtikel);
    }

    public Vector<Artikel> sucheArtikel(String bezeichnung) {

        Vector<Artikel> suchErg = new Vector();

        Iterator<Artikel> it = artikelBestand.iterator();

        while (it.hasNext()) {
            Artikel artikel = it.next();
            if (artikel.getBezeichnung().equals(bezeichnung)) {
                suchErg.add(artikel);
            }
        }
        return suchErg;
    }

    public Vector getArtikelBestand() {
        return new Vector(artikelBestand);
    }

}

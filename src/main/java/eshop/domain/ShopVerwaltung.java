package eshop.domain;

import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;

import java.io.IOException;
import java.util.Vector;

public class ShopVerwaltung {

    private Vector artikelBestand = new Vector();
    private PersistenceManager pm = new FilePersistenceManager();

    public void liesDaten(String datei) throws IOException {
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading(datei);

        Buch einBuch;
        do {
            // Buch-Objekt einlesen
            einBuch = pm.ladeArtikel();
            if (einBuch != null) {
                // Buch in Liste einfügen
                try {
                    einfuegen(einBuch);
                } catch (ArtikelExistiertBereitsException e1) {
                    // Kann hier eigentlich nicht auftreten,
                    // daher auch keine Fehlerbehandlung...
                }
            }
        } while (einBuch != null);

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    public void artikelEinfuegen(Vector artikelBestand) {

    }
}

package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Ereignis;
import eshop.valueobjects.Nutzer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class EreignisVerwaltung {
    private List<Ereignis> ereignisBestand = new Vector<Ereignis>();

    private PersistenceManager pm = new FilePersistenceManager();

    public void liesDaten(String datei) throws IOException {
        pm.openForReading(datei);
        Ereignis einEreignis;
        do {
            // Ereignis-Objekt einlesen
            einEreignis = pm.ladeEreignis();
            if (einEreignis != null) {
                try {
                    erstelle(einEreignis);
                } catch (ArtikelExistiertBereitsException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } while (einEreignis != null);

        pm.close();
    }

    public void erstelle(Ereignis einEreignis) throws ArtikelExistiertBereitsException, IOException {
        ereignisBestand.add(einEreignis);
    }

    public void schreibeEreignisse(String datei, Nutzer nutzer) throws IOException  {
        pm.openForWriting(datei);

        if (!ereignisBestand.isEmpty()) {
            Iterator<Ereignis> iter = ereignisBestand.iterator();
            while (iter.hasNext()) {
                Ereignis b = iter.next();
                pm.speichereEreignis(b, nutzer );
            }
        }
        pm.close();
    }

    public void erstelleEreignis(Nutzer nutzer, Artikel artikel, int menge, String typ ) {
        String benutzerName = nutzer.getLogin();
        int benutzerNr = nutzer.getNummer();
        int artikelNr = artikel.getArtikelNummer();
        Ereignis ereignis = new Ereignis(typ, artikelNr, menge, benutzerNr, benutzerName);
        ereignisBestand.add(ereignis);
        System.out.println("Ereignis erstellt.");
    }

    public List<Ereignis> gibEreignisListe(){
        return ereignisBestand;
    }

}

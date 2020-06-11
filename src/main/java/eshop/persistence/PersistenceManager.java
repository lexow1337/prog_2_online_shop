package eshop.persistence;

import eshop.valueobjects.Artikel;
import eshop.valueobjects.Ereignis;
import eshop.valueobjects.Nutzer;

import java.io.IOException;

public interface PersistenceManager {

    public void openForReading(String datenquelle) throws IOException;
    public void openForWriting(String datenquelle) throws IOException;
    public boolean close();

    public Artikel ladeArtikel() throws IOException;
    public boolean speichereArtikel(Artikel a) throws IOException;

    public Nutzer ladeNutzer() throws IOException;
    public boolean speichereNutzer(Nutzer n) throws IOException;

    public Ereignis ladeEreignis() throws IOException;
    public boolean speichereEreignis(Ereignis b, Nutzer n) throws IOException;

}

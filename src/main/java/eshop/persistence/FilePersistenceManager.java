package eshop.persistence;

import eshop.valueobjects.Artikel;

import java.io.*;
import java.nio.Buffer;

public class FilePersistenceManager implements PersistenceManager {

    BufferedReader reader = null;
    PrintWriter writer = null;

    public void openForReading(String datei) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(datei));
    }

    /**
     * Wadawdawda
     * awdawd
     * @param datei
     * @throws IOException
     */
    public void openForWriting(String datei) throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
    }

    public boolean close() {
        if (writer != null)
            writer.close();

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                return false;
            }
        }

        return true;
    }

    public Artikel ladeArtikel() throws IOException {
        String bezeichnung = liesZeile();
        if (bezeichnung == null) { return null; }

        String marke = liesZeile();
        if (bezeichnung == null) { return null; }

        String stringNummer = liesZeile();
        int nummer = Integer.parseInt(stringNummer);

        String stringBestand = liesZeile();
        int bestand = Integer.parseInt(stringBestand);

        String verfuegbarCode = liesZeile();
        boolean verfuegbar = verfuegbarCode.equals("t") ? true : false;

        return new Artikel(nummer, bestand, bezeichnung, marke, verfuegbar);

    }

    public boolean speichereArtikel(Artikel a) throws IOException {
        schreibeZeile(a.getBezeichnung());
        schreibeZeile(a.getMarke());
        schreibeZeile(a.getNummer() + "");
        schreibeZeile(a.getBestand() + "");

        if (a.isVerfuegbar()) {
            schreibeZeile("t");
        }
        else {
            schreibeZeile("f");
        }
        return true;
    }

    private String liesZeile() throws IOException {
        if (reader != null)
            return reader.readLine();
        else
            return "";
    }

    private void schreibeZeile(String daten) {
        if (writer != null)
            writer.println(daten);
    }
}

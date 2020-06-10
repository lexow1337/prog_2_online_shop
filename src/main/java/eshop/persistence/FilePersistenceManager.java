package eshop.persistence;

import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import java.io.*;
import java.nio.Buffer;

public class FilePersistenceManager implements PersistenceManager {

    BufferedReader reader = null;
    PrintWriter writer = null;

    public void openForReading(String datei) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(datei));
    }

    /**
     * Oeffnet Datei zum Schreiben.
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

        String stringPreis = liesZeile();
        float preis = Float.parseFloat(stringPreis);

        //String verfuegbarCode = liesZeile();
        //boolean verfuegbar = verfuegbarCode.equals("t") ? true : false;

        return new Artikel(bezeichnung, marke, nummer, preis, bestand);

    }

    public boolean speichereArtikel(Artikel a) throws IOException {
        schreibeZeile(a.getBezeichnung());
        schreibeZeile(a.getMarke());
        schreibeZeile(a.getArtikelNummer() + "");
        schreibeZeile(a.getBestand() + "");
        schreibeZeile(Float.toString(a.getPreis()));
        return true;
    }

    /**
     * Nutzer laden aus .txt Datei.
     * @return
     * @throws IOException
     */
    public Nutzer ladeNutzer() throws IOException {
        String vorname = liesZeile();
        if (vorname == null) { return null; }
        String nachname = liesZeile();
        if (nachname == null) { return null; }
        String login = liesZeile();
        if (login == null) { return null; }
        String passwort = liesZeile();
        if (passwort == null) { return null; }
        String adresse = liesZeile();
        if (adresse == null) { return null; }
        String typ = liesZeile();
        if (typ == null) { return null; }
        String strNummer = liesZeile();
        int nummer = Integer.parseInt(strNummer);

        if (typ.equals("k")) {
            Kunde k = new Kunde(vorname, nachname, login, passwort, adresse);
            k.setNummer(nummer);
            return k;
        } else if (typ.equals("m")) {
            Mitarbeiter m = new Mitarbeiter(vorname, nachname, login, passwort);
            m.setNummer(nummer);
            return m;
        }
        return null;
    }

    public boolean speichereNutzer(Nutzer n) {
        schreibeZeile(n.getVorname());
        schreibeZeile(n.getNachname());
        schreibeZeile(n.getLogin());
        schreibeZeile(n.getPasswort());
        if (n instanceof Kunde) {
            schreibeZeile(((Kunde) n).getAdresse());
            schreibeZeile("k");
            schreibeZeile(n.getNummer() + "");
        }
        if (n instanceof Mitarbeiter) {
            schreibeZeile("-");
            schreibeZeile("m");
            schreibeZeile(n.getNummer() + "");
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

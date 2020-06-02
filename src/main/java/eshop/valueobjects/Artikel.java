package eshop.valueobjects;

public class Artikel {

    private int artikelNummer;
    private int bestand;
    private float preis;
    private String bezeichnung;
    private String marke;
    private boolean verfuegbar;

    public Artikel(String bezeichnung, String marke, int artikelNummer, float preis, int bestand){
        this.setBezeichnung(bezeichnung);
        this.setMarke(marke);
        this.setArtikelNummer(artikelNummer);
        this.setPreis(preis);
        this.setBestand(bestand);
    }

    public String toString() {
        String verfuegbarkeit = verfuegbar ? "auf Lager" : "nicht auf Lager";
        return (" / Artikel-Nr.: " + artikelNummer + "\n"
                + " / Bezeichnung: " + bezeichnung + "\n"
                + " / Marke: " + marke + "\n"
                + " / Bestand: " + bestand + "\n"
                + " / Verfügbarkeit: " + verfuegbarkeit) + "\n"
                + "---------------------------------------------";

    }

    public boolean equals(Object andererArtikel) {
        if (andererArtikel instanceof Artikel)
            return ((this.artikelNummer == ((Artikel) andererArtikel).artikelNummer)
                    && (this.bezeichnung.equals(((Artikel) andererArtikel).bezeichnung)));
        else
            return false;
    }

    //Getter-/Setter Methoden ab hier

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getArtikelNummer() {
        return artikelNummer;
    }

    public void setArtikelNummer(int artikelNummer) {
        this.artikelNummer = artikelNummer;
    }

    public float getPreis() {
        return preis;
    }

    public void setPreis(float preis) {
        this.preis = preis;
    }

    public int getBestand() {
        return bestand;
    }

    public void setBestand(int bestand) {
        this.bestand = bestand;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public void setVerfuegbar(boolean verfuegbar) {
        this.verfuegbar = verfuegbar;
    }

    public String getMarke() {
        return marke;
    }

    public boolean isVerfuegbar() {
        return verfuegbar;
    }

    public void verringereBestand(int menge) {
        this.bestand -=  menge;
    }
}

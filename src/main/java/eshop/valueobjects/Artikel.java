package eshop.valueobjects;

public class Artikel {

    private int nummer;
    private int bestand;
    private String bezeichnung;
    private String marke;
    private boolean verfuegbar;

    public Artikel(int nummer, int bestand, String bezeichnung, String marke) { this(nummer, bestand, bezeichnung, marke, true); }
    public Artikel(int nummer, int bestand, String bezeichnung, String marke, boolean verfuegbar) {
        this.nummer = nummer;
        this.bestand = bestand;
        this.bezeichnung = bezeichnung;
        this.marke = marke;
        this.verfuegbar = verfuegbar;
    }

    public String toString() {
        String verfuegbarkeit = verfuegbar ? "auf Lager" : "nicht auf Lager";
        return (" / Artikel-Nr.: " + nummer + "\n"
                + " / Bezeichnung: " + bezeichnung + "\n"
                + " / Marke: " + marke + "\n"
                + " / Bestand: " + bestand + "\n"
                + " / Verfügbarkeit: " + verfuegbarkeit) + "\n"
                + "---------------------------------------------";

    }

    public boolean equals(Object andererArtikel) {
        if (andererArtikel instanceof Artikel)
            return ((this.nummer == ((Artikel) andererArtikel).nummer)
                    && (this.bezeichnung.equals(((Artikel) andererArtikel).bezeichnung)));
        else
            return false;
    }

    //Getter-Methoden ab hier

    public int getNummer() {
        return nummer;
    }

    public int getBestand() { return bestand; }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getMarke() {
        return marke;
    }

    public boolean isVerfuegbar() {
        return verfuegbar;
    }
}

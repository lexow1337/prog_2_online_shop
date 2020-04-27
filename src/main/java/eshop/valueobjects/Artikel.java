package eshop.valueobjects;

public class Artikel {

    private int nummer;
    private String bezeichnung;
    private String marke;
    private boolean verfuegbar;

    public Artikel(int nummer, String bezeichnung, String marke) { this(nummer, bezeichnung, marke, true); }
    public Artikel(int nummer, String bezeichnung, String marke, boolean verfuegbar) {
        this.nummer = nummer;
        this.bezeichnung = bezeichnung;
        this.marke = marke;
        this.verfuegbar = verfuegbar;
    }

    public String toString() {
        String verfuegbarkeit = verfuegbar ? "auf Lager" : "nicht auf Lager";
        return ("Nr.: " + nummer
                + " / Bezeichnung: " + bezeichnung
                + " /Marke: " + marke
                + " / Verfügbarkeit: " + verfuegbarkeit);

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

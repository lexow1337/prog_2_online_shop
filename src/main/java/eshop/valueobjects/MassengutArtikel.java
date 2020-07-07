package eshop.valueobjects;

public class MassengutArtikel extends Artikel {

    private int massengut = 1;

    public MassengutArtikel(String bezeichnung, String marke, int artikelNummer, float preis, int bestand, int massengut) {
        super(bezeichnung, marke, artikelNummer, preis, bestand);
        this.massengut = massengut;

    }

    public int getMassengut() {
        return this.massengut;
    }
}

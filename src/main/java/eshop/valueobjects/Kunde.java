package eshop.valueobjects;

public class Kunde extends Nutzer {

    private String strasse;
    private String hausnr = "";
    private String plz = "";
    private String ort = "";
    Warenkorb warenkorb = new Warenkorb();

    public Kunde(String vorname, String nachname, String login, String passwort) {
        super(vorname, nachname, login, passwort);
    }

    public Kunde(String vorname, String nachname, String login, String passwort, String strasse, String hausnr, String plz, String ort) {
        super(vorname, nachname, login, passwort);
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
    }

    // Getter
    public Warenkorb getWarenkorb() { return warenkorb; }

    public String getStrasse() {
        return strasse;
    }

    public String getHausnr() {
        return hausnr;
    }

    public String getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }

    //Setter
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public void setHausnr(String hausnr) {
        this.hausnr = hausnr;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}

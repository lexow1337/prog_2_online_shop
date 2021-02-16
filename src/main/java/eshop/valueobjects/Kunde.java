package eshop.valueobjects;

public class Kunde extends Nutzer {

    private String adresse;
    Warenkorb warenkorb = new Warenkorb();

    public Kunde(String vorname, String nachname, String login, String passwort, String adresse) {
        super(vorname, nachname, login, passwort);
        this.adresse = adresse;
    }

    // Getter
    public Warenkorb getWarenkorb() { return warenkorb; }
    public String getAdresse() { return adresse; }
}

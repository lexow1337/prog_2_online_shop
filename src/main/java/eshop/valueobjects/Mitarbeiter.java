package eshop.valueobjects;

public class Mitarbeiter extends Nutzer {


    public Mitarbeiter(String vorname, String nachname, String login, String passwort, int nummer) {
        super(vorname, nachname, login, passwort, nummer);
        this.isMitarbeiter = true;
    }

    public void artikelAnlegen(Artikel a) {
        // TODO: einen neuen Artikel im Shop anlegen
    }

    public void bestandAnpassen(Artikel a) {
        // TODO: Bestand eines Artikels anpassen
    }

    @Override
    public boolean isMitarbeiter() {
        return super.isMitarbeiter();
    }
}

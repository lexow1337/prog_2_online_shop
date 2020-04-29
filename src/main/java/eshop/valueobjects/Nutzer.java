package eshop.valueobjects;

public class Nutzer {

    private String vorname;
    private String nachname;
    private String login;
    private String passwort;

    private int nummer;
    private boolean isMitarbeiter;

    public Nutzer(String vorname, String nachname, String login, String passwort, int nummer, boolean isMitarbeiter) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.login = login;
        this.passwort = passwort;
        this.nummer = nummer;
        this.isMitarbeiter = isMitarbeiter;
    }

}

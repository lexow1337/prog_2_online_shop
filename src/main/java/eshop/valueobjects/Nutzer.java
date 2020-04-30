package eshop.valueobjects;

public abstract class Nutzer {

    private String vorname;
    private String nachname;
    private String login;
    private String passwort;

    private int nummer;
    protected boolean isMitarbeiter;

    public Nutzer(String _vorname, String _nachname, String _login, String _passwort, int _nummer) {
        this.vorname = _vorname;
        this.nachname = _nachname;
        this.login = _login;
        this.passwort = _passwort;
        this.nummer = _nummer;
        this.isMitarbeiter = false;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public boolean isMitarbeiter() {
        return isMitarbeiter;
    }

    public void setMitarbeiter(boolean mitarbeiter) {
        isMitarbeiter = mitarbeiter;
    }
}

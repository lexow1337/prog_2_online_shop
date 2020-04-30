package eshop.domain;

import eshop.domain.exceptions.BenutzerExistiertBereitsException;
import eshop.domain.exceptions.LoginFehlgeschlagenException;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;

import java.util.Vector;

public class NutzerVerwaltung {

    //private Vector alleNutzer = new Vector();
    Vector<Kunde> alleKunden = new Vector();
    Vector<Mitarbeiter> alleMitarbeiter = new Vector();

    public NutzerVerwaltung() {
        try {
            registrieren("Max", "Mustermann", "m_mustermann", "12345", 1);
            registrieren("Timo", "Tischer", "t_tischer", "54321", 2, true);
        } catch (BenutzerExistiertBereitsException e) {}
    }

    //Kunde registrieren
    public void registrieren(String vorname, String nachname, String login, String passwort, int nummer) throws BenutzerExistiertBereitsException {
        try {
            Kunde kunde = sucheNachLogin(login);
            throw new BenutzerExistiertBereitsException("Benutzer mit login: " + login + "existiert bereits.");
        }
        catch (LoginFehlgeschlagenException e) {
            alleKunden.add(new Kunde(vorname, nachname, login, passwort, nummer));
        }
    }

    //Mitarbeiter registrieren
    public void registrieren(String vorname, String nachname, String login, String passwort, int nummer, boolean isMitarbeiter) throws BenutzerExistiertBereitsException {
        try {
            Mitarbeiter mitarbeiter = sucheNachLogin(login, isMitarbeiter);
            throw new BenutzerExistiertBereitsException("Benutzer mit login: " + login + "existiert bereits.");
        }
        catch (LoginFehlgeschlagenException e) {
            alleMitarbeiter.add(new Mitarbeiter(vorname, nachname, login, passwort, nummer));
        }
    }

    //Kunde einloggen
    public Kunde einloggen(String login, String passwort) throws LoginFehlgeschlagenException {
        Kunde kunde = sucheNachLogin(login);
        if (kunde.getPasswort().equals(passwort)) {
            return kunde;
        }
        throw new LoginFehlgeschlagenException("Passwort war nicht richtig.");
    }

    //Mitarbeiter einloggen
    public Mitarbeiter einloggen(String login, String passwort, boolean isMitarbeiter) throws LoginFehlgeschlagenException {
        Mitarbeiter mitarbeiter = sucheNachLogin(login, isMitarbeiter);
        if (mitarbeiter.getPasswort().equals(passwort)) {
            return mitarbeiter;
        }
        throw new LoginFehlgeschlagenException("Passwort war nicht richtig.");
    }

    //Suche nach Kunde
    private Kunde sucheNachLogin(String login) throws LoginFehlgeschlagenException {
        System.out.println(alleKunden.size());
        for (Kunde kunde : alleKunden) {
            if (kunde.getLogin().equals(login)) {
                return kunde;
            }
        }
        throw new LoginFehlgeschlagenException("Kunde mit login: " + login + " wurde nicht gefunden.");
    }

    //Suche nach Mitarbeiter
    private Mitarbeiter sucheNachLogin(String login, boolean isMitarbeiter) throws LoginFehlgeschlagenException {
        System.out.println(alleMitarbeiter.size());
        for (Mitarbeiter mitarbeiter : alleMitarbeiter) {
            if (mitarbeiter.getLogin().equals(login) && mitarbeiter.isMitarbeiter() == isMitarbeiter) {
                return mitarbeiter;
            }
        }
        throw new LoginFehlgeschlagenException("Mitarbeiter mit login: " + login + " wurde nicht gefunden.");
    }

}

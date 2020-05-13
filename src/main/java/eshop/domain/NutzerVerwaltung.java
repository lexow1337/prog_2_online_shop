package eshop.domain;

import eshop.domain.exceptions.BenutzerExistiertBereitsException;
import eshop.domain.exceptions.LoginFehlgeschlagenException;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import java.util.Vector;

public class NutzerVerwaltung {

    Vector<Nutzer> alleNutzer = new Vector();

    public NutzerVerwaltung() {
        try {
            registrieren(new Kunde("Max", "Mustermann", "m_mustermann", "12345"));
            registrieren(new Mitarbeiter("Timo", "Tischer", "t_tischer", "54321"));
        } catch (BenutzerExistiertBereitsException e) {
            e.printStackTrace();
        }
    }

    public void registrieren(Nutzer nutzer) throws BenutzerExistiertBereitsException{
        if (alleNutzer.contains(nutzer)){
            throw new BenutzerExistiertBereitsException("Benutzer mit login: " + nutzer.getLogin() + " existiert bereits.");
        }
        alleNutzer.add(nutzer);
        nutzer.setNummer(naechsteFreieNummer());
    }

    private int naechsteFreieNummer(){
        return alleNutzer.size()+1;
    }

    //Kunde einloggen
    public Nutzer einloggen(String login, String passwort) throws LoginFehlgeschlagenException {
        Nutzer nutzer = sucheNachLogin(login);
        if (nutzer.getPasswort().equals(passwort)) {
            return nutzer;
        }
        throw new LoginFehlgeschlagenException("Passwort war nicht richtig.");
    }

    //Suche nach Kunde
    private Nutzer sucheNachLogin(String login) throws LoginFehlgeschlagenException {
        System.out.println("Alle Nutzer: " + alleNutzer.size());
        for (Nutzer nutzer : alleNutzer) {
            if (nutzer.getLogin().equals(login)) {
                return nutzer;
            }
        }
        throw new LoginFehlgeschlagenException("Nutzer mit login: " + login + " wurde nicht gefunden.");
    }

}

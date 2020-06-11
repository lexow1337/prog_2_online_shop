package eshop.domain;

import eshop.domain.exceptions.BenutzerExistiertBereitsException;
import eshop.domain.exceptions.LoginFehlgeschlagenException;
import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class NutzerVerwaltung {

    List<Nutzer> alleNutzer = new Vector();

    private PersistenceManager pm = new FilePersistenceManager();

/*    public NutzerVerwaltung() {
        try {
            registrieren(new Kunde("Max", "Mustermann", "m_mustermann", "12345", "Zufallsallee 25, 28219 Bremen"));
            registrieren(new Mitarbeiter("Timo", "Tischer", "t_tischer", "54321"));
        } catch (BenutzerExistiertBereitsException e) {
            e.printStackTrace();
        }
    }*/

    public void liesDaten(String datei) throws IOException {
        pm.openForReading(datei);
        Nutzer einNutzer;
        do {
            einNutzer = pm.ladeNutzer();
            if (einNutzer != null) {
                try {
                    einfuegen(einNutzer);
                } catch (BenutzerExistiertBereitsException e) {

                }
            }
        } while (einNutzer != null);

        pm.close();
    }

    /**
     * Nutzer in Liste alleNutzer einfuegen.
     * @param nutzer
     * @throws BenutzerExistiertBereitsException
     */
    public void einfuegen(Nutzer nutzer) throws BenutzerExistiertBereitsException {
        if (alleNutzer.contains(nutzer)) {
            throw new BenutzerExistiertBereitsException(nutzer + " - in 'einfuegen()'");
        }
        alleNutzer.add(nutzer);
    }

    /**
     * Fuegt nutzer in alleNutzer hinzu, wenn noch nicht vorhanden.
     * @param nutzer
     * @throws BenutzerExistiertBereitsException
     */
    public void registrieren(Nutzer nutzer) throws BenutzerExistiertBereitsException{
        if (alleNutzer.contains(nutzer)){
            throw new BenutzerExistiertBereitsException("Benutzer mit login: " + nutzer.getLogin() + " existiert bereits.");
        }
        alleNutzer.add(nutzer);
        nutzer.setNummer(naechsteFreieNummer());
    }

    /**
     * Gibt naechste freie MA Nr.
     * @return alleNutzer.size()+1
     */
    private int naechsteFreieNummer(){
        return alleNutzer.size()+1;
    }

    /**
     * Nutzer einloggen.
     * @param login
     * @param passwort
     * @return Nutzer nutzer
     * @throws LoginFehlgeschlagenException
     */
    public Nutzer einloggen(String login, String passwort) throws LoginFehlgeschlagenException {
        Nutzer nutzer = sucheNachLogin(login);
        if (nutzer.getPasswort().equals(passwort)) {
            return nutzer;
        }
        throw new LoginFehlgeschlagenException("Passwort war nicht richtig.");
    }

    /**
     * Sucht login-Name in alleNutzer
     * @param login
     * @return Nutzer nutzer
     * @throws LoginFehlgeschlagenException
     */
    private Nutzer sucheNachLogin(String login) throws LoginFehlgeschlagenException {

        for (Nutzer nutzer : alleNutzer) {
            if (nutzer.getLogin().equals(login)) {
                return nutzer;
            }
        }
        throw new LoginFehlgeschlagenException("Nutzer mit login: " + login + " wurde nicht gefunden.");
    }

    public void schreibeNutzer(String datei) throws IOException {
        pm.openForWriting(datei);
        if (!alleNutzer.isEmpty()) {
            Iterator<Nutzer> iter = alleNutzer.iterator();
            while (iter.hasNext()) {
                Nutzer n = iter.next();
                pm.speichereNutzer(n);
            }
        }
        pm.close();
    }

}

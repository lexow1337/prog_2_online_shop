package eshop.domain.exceptions;

import javax.swing.*;

public class BenutzernameOderPasswortIstFalschGUIException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel label = new JLabel("Benutzername oder Passwort ist Falsch");


    public BenutzernameOderPasswortIstFalschGUIException() {


        JOptionPane.showMessageDialog(null, "Passwort oder Benuter Falsch!");
    }


}

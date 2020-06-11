package eshop.domain.exceptions;

import javax.swing.*;

public class MassengutZuWenigGUIException extends Exception {
    private static final long serialVersionUID = 1L;
    public MassengutZuWenigGUIException() {

        JOptionPane.showMessageDialog(null, "Bitte auf das Massengut achten!");

    }


}

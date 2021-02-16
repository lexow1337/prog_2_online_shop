package eshop.domain.exceptions;

import javax.swing.*;

public class ArtikelBestandZuWenigException extends EShopException {

    public ArtikelBestandZuWenigException() {
        super("Der Bestand des Artikels ist zu wenig");

        JOptionPane.showMessageDialog(null, getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

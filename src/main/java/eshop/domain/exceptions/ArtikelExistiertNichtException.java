package eshop.domain.exceptions;

import javax.swing.*;

public class ArtikelExistiertNichtException extends EShopException {

    public ArtikelExistiertNichtException(int nummer) {
        super("Artikel mit der Nummer " + nummer + " existiert nicht");

        JOptionPane.showMessageDialog(null, getMessage());
    }
}

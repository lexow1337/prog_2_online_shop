package eshop.domain.exceptions;

import javax.swing.*;

public class AlleFelderAusfuellenException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public AlleFelderAusfuellenException() {


        JOptionPane.showMessageDialog(null, "Bitte alle Felder ausf�llen!");

    }


}

package eshop.ui.gui.panels;

import eshop.domain.Shop;
import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddArtikelPanel extends JPanel {

    public interface AddArtikelListener {
        public void onArtikelAdded (Artikel artikel);
    }


    private Shop shop = null;
    private AddArtikelListener addListener = null;


    private JButton addButton;
    private JButton deleteButton;
    //private JTextField numberTextField = null;
    private JTextField titleTextField = null;
    private JTextField markeTextField = null;
    private JTextField preisTextField = null;
    private JTextField bestandTextField = null;
    private JTextField massengutTextField = null;
    private JTextField textField;

    public AddArtikelPanel() {
        setupUI();

        setupEvents();
    }

    public void initialize(Shop eshop, AddArtikelListener listener) {
        shop = eshop;
        addListener = listener;
    }

    private void setupUI() {
        int anzahlZeilen = 12;
        setLayout(new GridLayout(0, 2, 0, 0));
        //JLabel label_4 = new JLabel("Nummer:");
        //this.add(label_4);
        //numberTextField = new JTextField();
        //this.add(numberTextField);
        JLabel label_7 = new JLabel("Marke:");
        this.add(label_7);
        markeTextField = new JTextField();
        this.add(markeTextField);
        JLabel label_2 = new JLabel("Titel:");
        this.add(label_2);
        titleTextField = new JTextField();
        this.add(titleTextField);
        JLabel lblPreis = new JLabel("Preis:");
        this.add(lblPreis);
        preisTextField = new JTextField();
        this.add(preisTextField);
        JLabel lblBestand = new JLabel("Bestand:");
        this.add(lblBestand);
        bestandTextField = new JTextField();
        this.add(bestandTextField);
        JLabel lblMassengut = new JLabel("Massengut:");
        this.add(lblMassengut);
        massengutTextField = new JTextField();
        this.add(massengutTextField);
        addButton = new JButton("Einfuegen");
        this.add(addButton);

        JLabel label_1 = new JLabel("");
        add(label_1);

        JSeparator separator = new JSeparator();
        add(separator);

        JSeparator separator_1 = new JSeparator();
        add(separator_1);

        JLabel lblLschen = new JLabel("L\u00F6schen");
        add(lblLschen);
        Font font = new Font("Arial", Font.BOLD,12);

        JLabel label_5 = new JLabel("");
        add(label_5);

        JLabel lblNummer = new JLabel("Nummer:");
        add(lblNummer);

        textField = new JTextField();
        add(textField);
        textField.setColumns(10);

        deleteButton = new JButton("Loeschen");
        add(deleteButton);

        JLabel label_6 = new JLabel("");
        add(label_6);

        lblLschen.setFont(font);

        for (int i=6; i<anzahlZeilen; i++) {
            this.add(new JLabel());

        }

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Einfuegen"));
    }

    private void setupEvents() {
        addButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        System.out.println("Event: " + ae.getActionCommand());
                        try {
                            artikelEinfuegen();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
        deleteButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        try {
                            artikelLoeschen();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                });
    }

    private void artikelEinfuegen() throws IOException {
        //String nummer = numberTextField.getText();
        String titel = titleTextField.getText();
        String marke = markeTextField.getText();
        String preis = preisTextField.getText();
        String bestand = bestandTextField.getText();
        String massengut = massengutTextField.getText();

        if (!titel.isEmpty() && !preis.isEmpty() && !bestand.isEmpty() && !massengut.isEmpty()) {
            try {
                float preisAlsFloat = Float.parseFloat(preis);
                int bestandAlsInt = Integer.parseInt(bestand);
                int massengutAlsInt = Integer.parseInt(massengut);

                Artikel artikel = shop.fuegeArtikelEin(titel, marke, bestandAlsInt, preisAlsFloat);

                //numberTextField.setText("");
                titleTextField.setText("");
                preisTextField.setText("");
                bestandTextField.setText("");
                massengutTextField.setText("");

                // Am Ende Listener, d.h. unseren Frame benachrichtigen:
                addListener.onArtikelAdded(artikel);
            } catch (NumberFormatException nfe) {
                System.err.println("Bitte eine Zahl eingeben.");
            } catch (ArtikelExistiertBereitsException bebe) {
                System.err.println(bebe.getMessage());
            }
        }else {

            JOptionPane.showMessageDialog(this, "Bitte alle Felder ausfuellen!");
            //numberTextField.setText("");
            titleTextField.setText("");
            preisTextField.setText("");
            bestandTextField.setText("");
            massengutTextField.setText("");
        }
    }

    private void artikelLoeschen() throws IOException {
        String nummerString = textField.getText();
        int nummerAlsInt = Integer.parseInt(nummerString);

        shop.loescheArtikel(nummerAlsInt);
        textField.setText("");

        addListener.onArtikelAdded(shop.sucheNachNummer(nummerAlsInt));
    }

}

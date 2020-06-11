package eshop.ui.gui.panels;

import eshop.domain.Shop;
import eshop.domain.exceptions.ArtikelBestandZuWenigException;
import eshop.domain.exceptions.ArtikelExistiertNichtException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

//Wichtig: Das AddBookPanel _ist ein_ Panel und damit auch eine Component;
//es kann daher in das Layout eines anderen Containers
//(in unserer Anwendung des Frames) eingef�gt werden.
public class EditWarenkorbPanel extends JPanel {

    // Ueber dieses Interface uebermittelt das AddArtikelPanel
    // ein neu hinzugefuegtes Buch an einen Empfaenger.
    // In unserem Fall ist der Empfaenger die EshopGuiMitKomponenten,
    // die dieses Interface implementiert und auf ein neue hinzugefuegtes
    // Artikel reagiert, indem sie die Artikelliste aktualisiert.
    public interface EditWarenkorbListener {
        public void onWarenkorbEdited();
        public void onCheckOut();
    }


    private Shop shop = null;
    private EditWarenkorbListener editListener = null;


    private JButton editButton;
    private JButton btnCheckOut;
    private JTextField numberTextField = null;
    private JTextField mengeTextField = null;
    private JLabel mengeHelpLabel = null;

    public EditWarenkorbPanel() {
        setupUI();

        setupEvents();
    }

    public void initialize(Shop eshop, EditWarenkorbListener listener) {
        shop = eshop;
        editListener = listener;
    }

    private void setupUI() {
        // GridLayout (nicht zu verwechseln mit GridBagLayout!)
        int anzahlZeilen = 12; //fuer leere Labels als Abstandshalter
        this.setLayout(new GridLayout(anzahlZeilen, 1));

        this.add(new JLabel("Nummer:"));
        numberTextField = new JTextField(10);
        this.add(numberTextField);

        this.add(new JLabel("Menge:"));
        mengeTextField = new JTextField(10);
        this.add(mengeTextField);

        mengeHelpLabel = new JLabel("");
        this.add(mengeHelpLabel); // Abstandshalter

        editButton = new JButton("Bearbeiten");
        this.add(editButton);

        // Mit leeren Labels als Platzhalter auffuellen,
        // damit die einzelnen Zellen nicht uebermaessig gross werden
        // (bei GridLayout sind alle Zellen gleich gross!).

        for (int i=6; i<anzahlZeilen; i++) {
            this.add(new JLabel());

        }

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Bearbeiten"));

        add(new JLabel());

        btnCheckOut = new JButton("Check Out");
        add(btnCheckOut);
    }

    private void setupEvents() {
        editButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        System.out.println("Event: " + ae.getActionCommand());
                        // TODO: Artikel in Warenkorb �ndern
                        try {
                            bestandImWarenkorbaendern();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });

        btnCheckOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    warenkorbKaufen();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        mengeTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                //mengeHelpLabel.setText("Um Bestand zu Verringern, Einfach eine Negative Zahl eingeben.");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mengeHelpLabel.setText("");
            }
        });
    }
    private void bestandImWarenkorbaendern() throws IOException {
        String nummer = numberTextField.getText();
        String menge = mengeTextField.getText();
        if(!nummer.isEmpty() && !menge.isEmpty()) {
            try {
                int nummerAsInt = Integer.parseInt(nummer);
                int mengeAsInt = Integer.parseInt(menge);
                shop.bestandImWarenkorbAendern(nummerAsInt, mengeAsInt);

                editListener.onWarenkorbEdited();
            }catch(NumberFormatException nfe) {
                System.err.println("Bitte nummern benutzen!");
            } catch (ArtikelExistiertNichtException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ArtikelBestandZuWenigException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private void warenkorbKaufen() throws IOException{
        shop.warenkorbKaufen();

        editListener.onCheckOut();
    }
}

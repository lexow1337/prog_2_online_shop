package eshop.ui.gui.panels;

import eshop.domain.Shop;
import eshop.domain.exceptions.ArtikelBestandZuWenigException;
import eshop.domain.exceptions.ArtikelExistiertNichtException;
import eshop.domain.exceptions.MassengutZuWenigGUIException;
import eshop.valueobjects.Artikel;

import eshop.ui.gui.models.ArtikelTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddArtikelInWarenkorbPanel extends JPanel {

    public interface AddArtikelInWarenkorbListener {
        public void onArtikelAdded(Artikel artikel);
    }


    private Shop shop = null;
    private AddArtikelInWarenkorbListener addListener = null;

    public void setNumberTextFieldText(String numberTextField) {
        this.numberTextField.setText(numberTextField);
    }

    private JButton addButton;
    private JTextField numberTextField = null;
    private JSpinner mengeTextField = null;
    private ShowArtikelTablePanel artikelPanel;

    public AddArtikelInWarenkorbPanel() {
        setupUI();

        setupEvents();
    }

    public void initialize(Shop eshop, AddArtikelInWarenkorbListener listener) {
        shop = eshop;
        addListener = listener;
    }

    private void setupUI() {
        // GridLayout (nicht zu verwechseln mit GridBagLayout!)
        int anzahlZeilen = 12; //fuer leere Labels als Abstandshalter
        this.setLayout(new GridLayout(anzahlZeilen, 1));

        this.add(new JLabel("Nummer:"));
        numberTextField = new JTextField(10);
        this.add(numberTextField);

        this.add(new JLabel("Menge:"));
        mengeTextField = new JSpinner();
        mengeTextField.setValue(1);
        this.add(mengeTextField);

        this.add(new JLabel()); // Abstandshalter
        addButton = new JButton("Einfuegen");
        this.add(addButton);

        // Mit leeren Labels als Platzhalter auff�llen,
        // damit die einzelnen Zellen nicht �berm��ig gro� werden
        // (bei GridLayout sind alle Zellen gleich gro�!).

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
                        // TODO: Artikel in Warenkorb hinzuf�gen
                        try {
                            artikelInWarenkorbEinfuegen();
                            JOptionPane.showMessageDialog(null, "Artikel wurde in den Warenkorb gelegt");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
    }
    private void artikelInWarenkorbEinfuegen() throws IOException {
        String nummer = numberTextField.getText();
        String menge = mengeTextField.getValue().toString();
        if(!nummer.isEmpty() && !menge.isEmpty()) {
            try {
                int nummerAlsInt = Integer.parseInt(nummer);
                int mengeAlsInt = Integer.parseInt(menge);
                shop.artikelInWarenkorb(nummerAlsInt, mengeAlsInt);
                Artikel artikel = shop.sucheNachNummer(nummerAlsInt);

                addListener.onArtikelAdded(shop.sucheNachNummer(nummerAlsInt));
                numberTextField.setText("");
                mengeTextField.setValue(0);
            }catch(NumberFormatException nfe) {
                System.err.println("Bitte eine Zahl als Nummer und Menge angeben!");
            } catch (ArtikelExistiertNichtException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ArtikelBestandZuWenigException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }/* catch (MassengutZuWenigGUIException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }
    }
}

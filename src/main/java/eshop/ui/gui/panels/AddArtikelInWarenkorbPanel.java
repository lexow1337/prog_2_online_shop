package eshop.ui.gui.panels;

import eshop.domain.Shop;
import eshop.domain.exceptions.ArtikelBestandZuWenigException;
import eshop.domain.exceptions.ArtikelExistiertNichtException;
import eshop.domain.exceptions.MassengutZuWenigGUIException;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//Wichtig: Das AddBookPanel _ist ein_ Panel und damit auch eine Component;
//es kann daher in das Layout eines anderen Containers
//(in unserer Anwendung des Frames) eingef�gt werden.
public class AddArtikelInWarenkorbPanel extends JPanel {

    // �ber dieses Interface �bermittelt das AddArtikelPanel
    // ein neu hinzugef�gtes Buch an einen Empf�nger.
    // In unserem Fall ist der Empf�nger die EshopGuiMitKomponenten,
    // die dieses Interface implementiert und auf ein neue hinzugef�gtes
    // Artikel reagiert, indem sie die Artikelliste aktualisiert.
    public interface AddArtikelInWarenkorbListener {
        public void onArtikelAdded(Artikel artikel);
    }


    private Shop shop = null;
    private AddArtikelInWarenkorbListener addListener = null;


    private JButton addButton;
    private JTextField numberTextField = null;
    private JTextField mengeTextField = null;

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
        int anzahlZeilen = 12; //f�r leere Labels als Abstandshalter
        this.setLayout(new GridLayout(anzahlZeilen, 1));

        this.add(new JLabel("Nummer:"));
        numberTextField = new JTextField(10);
        this.add(numberTextField);

        this.add(new JLabel("Menge:"));
        mengeTextField = new JTextField(10);
        this.add(mengeTextField);

        this.add(new JLabel()); // Abstandshalter
        addButton = new JButton("Einf�gen");
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
        String menge = mengeTextField.getText();
        if(!nummer.isEmpty() && !menge.isEmpty()) {
            try {
                int nummerAlsInt = Integer.parseInt(nummer);
                int mengeAlsInt = Integer.parseInt(menge);
                shop.artikelInWarenkorb(nummerAlsInt, mengeAlsInt);
                Artikel artikel = shop.sucheNachNummer(nummerAlsInt);

                addListener.onArtikelAdded(shop.sucheNachNummer(nummerAlsInt));
                numberTextField.setText("");
                mengeTextField.setText("");
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

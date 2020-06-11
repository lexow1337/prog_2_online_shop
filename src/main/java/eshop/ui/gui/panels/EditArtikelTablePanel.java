package eshop.ui.gui.panels;

import eshop.ui.gui.models.ArtikelTableModel;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EditArtikelTablePanel extends JTable {

    public interface UpdateBestandListener {
        public void onEnterListArbeiter(int nummer, int menge);
    }

    UpdateBestandListener updateListener = null;

    public EditArtikelTablePanel(List<Artikel> artikel) {
        super();


        //TableModel erzeugen
        ArtikelTableModel tableModel = new ArtikelTableModel(artikel);
        //bei JTable "anmelden" und..
        setModel(tableModel);
        //Daten an Model uebergeben
        updateArtikelList(artikel);

        setupEvent();

    }

    public void updateArtikelList(java.util.List<Artikel> artikel) {
        // Sortierung (mit Lambda-Expression)
        //Collections.sort(buecher, (b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));    // Sortierung nach Titel
        Collections.sort(artikel, Comparator.comparingInt(Artikel::getArtikelNummer));    // Sortierung nach Nummer

        // TableModel von JTable holen und ...
        ArtikelTableModel tableModel = (ArtikelTableModel) getModel();
        // ... Inhalt aktualisieren
        tableModel.setBooks(artikel);
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        if (getColumnName(column) == "Bestand") {
            return true;
        } else {
            return false;
        }
    }

    public void initialize(UpdateBestandListener listener) {
        updateListener = listener;
    }

    private void setupEvent() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    //setValueAt("test", getSelectedRow(), 3);
                    JOptionPane.showMessageDialog(null, "hey");
                    JOptionPane.showMessageDialog(null, getValueAt(getSelectedRow(), 0).toString() + " und " + getValueAt(getSelectedRow(), 3).toString());
                    //updateListener.onEnterListArbeiter(Integer.parseInt(getValueAt(getSelectedRow(), 0).toString()),Integer.parseInt(getValueAt(getSelectedRow(), 3).toString()));
                }
            }
        });
    }
}

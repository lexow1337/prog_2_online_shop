package eshop.ui.gui.panels;

import eshop.ui.gui.models.ArtikelTableModel;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class WarenkorbTablePanel extends JTable {

    public WarenkorbTablePanel(List<Artikel> artikel) {
        super();

        //TableModel erzeugen
        ArtikelTableModel tableModel = new ArtikelTableModel (artikel);
        //bei JTable "anmelden" und..
        setModel(tableModel);
        //Daten an Model uebergeben
        updateArtikelList(artikel);
    }

    public void updateArtikelList(java.util.List<Artikel> artikel) {
        // Sortierung (mit Lambda-Expression)
        //Collections.sort(buecher, (b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));	// Sortierung nach Titel
        Collections.sort(artikel, (b1, b2) -> b1.getArtikelNummer() - b2.getArtikelNummer());	// Sortierung nach Nummer

        // TableModel von JTable holen und ...
        ArtikelTableModel tableModel = (ArtikelTableModel) getModel();
        // ... Inhalt aktualisieren
        tableModel.setBooks(artikel);
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        if(getColumnName(column) == "Bestand") {
            return true;
        }else {
            return false;
        }
    }
}

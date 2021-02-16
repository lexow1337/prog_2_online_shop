package eshop.ui.gui.models;

import eshop.valueobjects.Ereignis;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class EreignisTableModel extends AbstractTableModel {
    private List<Ereignis> ereignis;
    private String[] spaltenNamen = { "Ereignis","Artikelnummer", "Menge", "Benutzer", "Datum" };


    public EreignisTableModel(List<Ereignis> ereignisse) {
        super();
        // Ich erstelle eine Kopie der B�cherliste,
        // damit beim Aktualisieren (siehe Methode setBooks())
        // keine unerwarteten Seiteneffekte entstehen.
        ereignis = new Vector<Ereignis>();
        ereignis.addAll(ereignisse);
    }

    public void setBooks(List<Ereignis> aktuelleBuecher){
        ereignis.clear();
        ereignis.addAll(aktuelleBuecher);
        fireTableDataChanged();
    }

    /*
     * Ab hier �berschriebene Methoden mit Informationen,
     * die eine JTable von jedem TableModel erwartet:
     * - Anzahl der Zeilen
     * - Anzahl der Spalten
     * - Benennung der Spalten
     * - Wert einer Zelle in Zeile / Spalte
     */

    @Override
    public int getRowCount() {
        if(ereignis != null) {
            return ereignis.size();
        }else {
            return 0;
        }
    }


    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Ereignis gewaehltesEreignis = ereignis.get(row);
        switch (col) {
            case 0:
                return gewaehltesEreignis.getTyp();
            case 1:
                return gewaehltesEreignis.getArtikelNr();
            case 2:
                return gewaehltesEreignis.getArtikelanzahl();
            case 3:
                return gewaehltesEreignis.getBenutzerNr();
            case 4:
                return gewaehltesEreignis.getZeitpunkt();
            default:
                return null;
        }
    }
}

package eshop.ui.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import eshop.domain.Shop;
import eshop.ui.gui.menus.FileMenu;
import eshop.ui.gui.menus.HelpMenu;
import eshop.ui.gui.panels.AddArtikelPanel;
import eshop.ui.gui.panels.EditArtikelTablePanel;
import eshop.ui.gui.panels.EditArtikelTablePanel.UpdateBestandListener;
import eshop.ui.gui.panels.EreignissTablePanel;
import eshop.ui.gui.panels.SearchArtikelPanel;
import eshop.ui.gui.panels.AddArtikelPanel.AddArtikelListener;
import eshop.ui.gui.panels.SearchArtikelPanel.SearchResultListener;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Ereignis;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class ArbeiterFrame extends JFrame implements SearchResultListener, AddArtikelListener, UpdateBestandListener {

    private JPanel contentPane;
    private SearchArtikelPanel searchPanel;
    private AddArtikelPanel addPanel;
    private EditArtikelTablePanel artikelPanel;
    private Shop eshop;
    private JTabbedPane tabbedPane;
    private JTable ereignisPanel;

    /**
     * Create the frame.
     */
    public ArbeiterFrame(Shop eshop) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        this.eshop = eshop;

        //Menu definieren
        setupMenu();


        //Klick auf Kreu / roten Kreis (Fenster schlie�en)
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Layout des Frames : BorderLayout
        getContentPane().setLayout(new BorderLayout());

        //north
        searchPanel = new SearchArtikelPanel();
        searchPanel.initialize(eshop, this);

        //west
        addPanel = new AddArtikelPanel();
        addPanel.initialize(eshop, this);

        //Center
        java.util.List<Artikel> artikel = eshop.gibAlleArtikel();
        java.util.List<Ereignis> ereignis = eshop.gibEreignisListe();

        //Zusammenbau in BorderLayout des Frames
        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(addPanel, BorderLayout.WEST);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        // (wahlweise Anzeige als Liste oder Tabelle)
        //booksPanel = new BooksListPanel(artikel);
        artikelPanel = new EditArtikelTablePanel(artikel);
        JScrollPane scrollPane = new JScrollPane(artikelPanel);
        tabbedPane.addTab("Artikel", null, scrollPane, null);

        ereignisPanel = new EreignissTablePanel(ereignis);
        JScrollPane scrollPane_1 = new JScrollPane(ereignisPanel);
        tabbedPane.addTab("Ereignis", null, scrollPane_1, null);

        this.setSize(640, 480);
        this.setVisible(true);
    }

    private void setupMenu() {
        // Men�leiste anlegen ...
        JMenuBar mBar = new JMenuBar();

        JMenu fileMenu = new FileMenu(this, eshop);
        mBar.add(fileMenu);

        JMenu helpMenu = new HelpMenu();
        mBar.add(helpMenu);

        // ... und beim Fenster anmelden
        this.setJMenuBar(mBar);
    }

    /*
     * (non-Javadoc)
     *
     * Listener, der Benachrichtungen erh�lt, wenn im AddBookPanel ein Buch eingef�gt wurde.
     * (Als Reaktion soll die B�cherliste aktualisiert werden.)
     * @see bib.local.ui.gui.panels.AddBookPanel.AddBookListener#onBookAdded(bib.local.valueobjects.Buch)
     */
    @Override
    public void onArtikelAdded(Artikel artikel) {
        //artikel neu laden und anzeigen
        java.util.List<Artikel> artikelListe = eshop.gibAlleArtikel();
        java.util.List<Ereignis> ereignis = eshop.gibEreignisListe();
        artikelPanel.updateArtikelList(artikelListe);
        //ereignisPanel.updateEreignisList(artikelListe);
    }

    /*
     * (non-Javadoc)
     *
     * Listener, der Benachrichtungen erh�lt, wenn das SearchBooksPanel ein Suchergebnis bereitstellen m�chte.
     * (Als Reaktion soll die B�cherliste aktualisiert werden.)
     * @see bib.local.ui.gui.swing.panels.SearchBooksPanel.SearchResultListener#onSearchResult(java.util.List)
     */
    @Override
    public void onSearchResult(List<Artikel> artikelList) {
        artikelPanel.updateArtikelList(artikelList);
    }

    public void onEnterListArbeiter(int nummer, int menge) {
        eshop.aendereBestand(nummer, menge);
        JOptionPane.showMessageDialog(this, "Bestand " + nummer + " um " + menge + "St�ck local ge�ndert");
    }

}

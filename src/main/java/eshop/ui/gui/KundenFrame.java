package eshop.ui.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import eshop.domain.Shop;
import eshop.ui.gui.menus.FileMenu;
import eshop.ui.gui.menus.HelpMenu;
import eshop.ui.gui.panels.AddArtikelInWarenkorbPanel;
import eshop.ui.gui.panels.AddArtikelInWarenkorbPanel.AddArtikelInWarenkorbListener;
import eshop.ui.gui.panels.EditArtikelTablePanel;
import eshop.ui.gui.panels.DeleteArtikelInWarenkorb;
import eshop.ui.gui.panels.DeleteArtikelInWarenkorb.DeleteArtikelInWarenkorbListener;
import eshop.ui.gui.panels.EditWarenkorbPanel;
import eshop.ui.gui.panels.EditWarenkorbPanel.EditWarenkorbListener;
import eshop.ui.gui.panels.SearchArtikelPanel;
import eshop.ui.gui.panels.SearchArtikelPanel.SearchResultListener;
import eshop.ui.gui.panels.ShowArtikelTablePanel;
import eshop.ui.gui.panels.WarenkorbTablePanel;
import eshop.valueobjects.Artikel;
import javax.swing.JTabbedPane;


public class KundenFrame extends JFrame implements SearchResultListener, AddArtikelInWarenkorbListener, EditWarenkorbListener, DeleteArtikelInWarenkorbListener {

    private JPanel contentPane;
    private Shop eshop;
    private SearchArtikelPanel searchPanel;

    private JTabbedPane tabbedPane;

    // Panel Bestand
    private JPanel panelBestand;
    private AddArtikelInWarenkorbPanel addArtikelInWarenkorbPanel;
    private ShowArtikelTablePanel artikelPanel;

    // Panel Warenkorb
    private JPanel panelWarenkorb;
    private EditWarenkorbPanel editWarenkorbPanel;
    private WarenkorbTablePanel warenkorbPanel;
    private JTabbedPane tabbedPane_1;
    private DeleteArtikelInWarenkorb deleteWarenkorbPanel;

    /**
     * Create the frame.
     */
    public KundenFrame(Shop eshop) {
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

        //Center
        java.util.List<Artikel> artikel = eshop.gibAlleArtikel();
        // TODO: Warenkorb des Kunden
        java.util.List<Artikel> warenkorb = eshop.warenkorbAnzeigen();

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        panelBestand = new JPanel();
        tabbedPane.addTab("Bestand", null, panelBestand, null);

        //west
        addArtikelInWarenkorbPanel = new AddArtikelInWarenkorbPanel();
        addArtikelInWarenkorbPanel.setBounds(0, 53, 98, 329);
        addArtikelInWarenkorbPanel.initialize(eshop, this);
        panelBestand.setLayout(null);

        //north
        searchPanel = new SearchArtikelPanel();
        searchPanel.setBounds(0, 0, 609, 53);
        panelBestand.add(searchPanel);
        searchPanel.initialize(eshop, this);
        panelBestand.add(addArtikelInWarenkorbPanel);
        // (wahlweise Anzeige als Liste oder Tabelle)
        //booksPanel = new BooksListPanel(artikel);
        artikelPanel = new ShowArtikelTablePanel(artikel);
        JScrollPane scrollPaneBestand = new JScrollPane(artikelPanel);
        scrollPaneBestand.setBounds(98, 53, 511, 329);
        panelBestand.add(scrollPaneBestand);


        panelWarenkorb = new JPanel();
        tabbedPane.addTab("Warenkorb", null, panelWarenkorb, null);
        panelWarenkorb.setLayout(new BorderLayout(0, 0));
        warenkorbPanel = new WarenkorbTablePanel(warenkorb);
        JScrollPane scrollPaneWarenkorb = new JScrollPane(warenkorbPanel);
        scrollPaneWarenkorb.setBounds(98, 53, 511, 329);
        //panelBestand.add(scrollPaneWarenkorb);
        panelWarenkorb.add(scrollPaneWarenkorb, BorderLayout.CENTER);

        tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        panelWarenkorb.add(tabbedPane_1, BorderLayout.WEST);

        editWarenkorbPanel = new EditWarenkorbPanel();
        tabbedPane_1.addTab("Bearbeiten", null, editWarenkorbPanel, null);
        editWarenkorbPanel.initialize(eshop, this);

        deleteWarenkorbPanel = new DeleteArtikelInWarenkorb();
        tabbedPane_1.addTab("Loeschen", null, deleteWarenkorbPanel, null);
        deleteWarenkorbPanel.initialize(eshop, this);


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
     * Listener, der Benachrichtungen erh�lt, wenn im Warnkorb ein Buch eingef�gt wurde.
     */
    @Override
    public void onArtikelAdded(Artikel artikel) {
        // TODO: Warenkorb zeugs
        warenkorbPanel.updateArtikelList(eshop.warenkorbAnzeigen());
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

    @Override
    public void onArtikelRemoved() {
        warenkorbPanel.updateArtikelList(eshop.warenkorbAnzeigen());
    }

    @Override
    public void onWarenkorbEdited() {
        // TODO Auto-generated method stub
        warenkorbPanel.updateArtikelList(eshop.warenkorbAnzeigen());
    }

    @Override
    public void onCheckOut() {
        // TODO Auto-generated method stub
        warenkorbPanel.updateArtikelList(eshop.warenkorbAnzeigen());
        artikelPanel.updateArtikelList(eshop.gibAlleArtikel());
    }
}

package eshop.ui.gui;

import eshop.domain.Shop;
import eshop.ui.gui.panels.LoginPanel;
import eshop.ui.gui.panels.LoginPanel.LoginListener;
import eshop.ui.gui.panels.RegistrationPanel;
import eshop.valueobjects.*;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class ShopGUI extends JFrame implements LoginListener {

    private Shop eshop;
    private Nutzer benutzer;
    private LoginPanel loginPanel;

    public ShopGUI(String titel) {
        super(titel);

        try {
            eshop = new Shop("SHOP");
            initializeLogin();
        } catch (IOException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ShopGUI(String titel, Shop oldEshop) {
        super(titel);

        eshop = oldEshop;
        initializeLogin();
    }

    private void initializeLogin() {
        //Menu definieren
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.NORTH);
        loginPanel = new LoginPanel();
        tabbedPane.addTab("Login", null, loginPanel, null);

        JPanel registrationPanel = new RegistrationPanel(eshop);
        tabbedPane.addTab("Registrieren", null, registrationPanel, null);
        loginPanel.initialize(eshop, this);

        pack();
        setLocationRelativeTo(null);

        this.setVisible(true);



    }

    private void initializeArbeiter() {
        //this.remove(loginPanel);
        this.dispose();

        ArbeiterFrame frame = new ArbeiterFrame(eshop);
        frame.setVisible(true);
    }

    private void initializeKunde() {
        //this.remove(loginPanel);
        this.dispose();

        KundenFrame frame = new KundenFrame(eshop);
        frame.setVisible(true);
    }

    public void OnLogin(Shop shop){
        if(shop.getEingeloggterNutzer() instanceof Mitarbeiter) {
            initializeArbeiter();
        }else if(shop.getEingeloggterNutzer() instanceof Kunde) {
            initializeKunde();
        }
    }

    public static void main(String[] args) {
        //Start der Anwendung (per anonymer Klasse)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ShopGUI gui = new ShopGUI("Eshop");
            }
        });
    }
}

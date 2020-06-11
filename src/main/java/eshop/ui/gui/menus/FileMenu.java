package eshop.ui.gui.menus;

import eshop.domain.Shop;
import eshop.ui.gui.ShopGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FileMenu extends JMenu implements ActionListener {

    private JFrame frame;
    private Shop shop;

    public FileMenu(JFrame frame, Shop shop) {
        super("File");

        this.frame = frame;
        this.shop = shop;

        JMenuItem mi = new JMenuItem("Save");
        //mi.setEnabled(false);
        mi.addActionListener(this);
        this.add(mi);

        this.addSeparator();

        mi = new JMenuItem("Quit");
        mi.addActionListener(this);
        this.add(mi);

        this.addSeparator();

        mi = new JMenuItem("Logout");
        mi.addActionListener(this);
        this.add(mi);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        System.out.println(command);

        if (command.equals("Save")) {
            try {
                shop.schreibeArtikel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.equals("Quit")) {
            frame.setVisible(false);
            frame.dispose();

            System.exit(0);
        } else if (command.equals("Logout")) {
            frame.setVisible(false);

            frame.dispose();
            ShopGUI shop = new ShopGUI("Eshop", this.shop);

        }
    }
}

package eshop.ui.gui.menus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpMenu extends JMenu implements ActionListener {
    public HelpMenu() {
        super("Help");

        // Nur zu Testzwecken: Men� mit Untermen�
        JMenuItem miP = new JMenuItem("Programmers");
        miP.addActionListener(this);
        add(miP);

        // Geht in AWT auch (alternativ zu mi.addActionListener(this)-Aufrufen oben):
//		m.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand()== "Programmers") {
            JOptionPane.showMessageDialog(null, "Dieses Programm wurde Programmiert von Max, Lars und Timo","Programmers",1);

        }


        System.out.println("Klick auf Men� '" + e.getActionCommand() + "'.");
    }
}

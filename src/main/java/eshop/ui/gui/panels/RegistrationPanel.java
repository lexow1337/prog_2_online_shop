package eshop.ui.gui.panels;

import eshop.domain.Shop;
import eshop.domain.exceptions.BenutzerExistiertBereitsException;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class RegistrationPanel extends JPanel {
    private JTextField txtVorname;
    private JTextField txtNachname;
    private JTextField txtNutzername;
    private JPasswordField pwdPasswort;
    private JPasswordField pwdZweitespasswort;

    private JRadioButton rdbtnKunde;
    private JRadioButton rdbtnArbeiter;
    private final ButtonGroup BenutzerTyp = new ButtonGroup();
    private JLabel lblZweitInformation;
    private JTextField txtAdresse;
    private JButton btnRegistrieren;

    private Shop shop;
    private JPasswordField pwdMasterpasswort;

    /**
     * Create the panel.
     */
    public RegistrationPanel(Shop eshop) {
        shop = eshop;

        setupUI();

        setupEvents();
    }

    private void setupUI() {
        setBorder(new TitledBorder(null, "Registrieren", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowHeights = new int[]{0, -46, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        setLayout(gridBagLayout);

        JLabel lblVorname = new JLabel("Vorname:");
        GridBagConstraints gbc_lblVorname = new GridBagConstraints();
        gbc_lblVorname.insets = new Insets(0, 0, 5, 5);
        gbc_lblVorname.anchor = GridBagConstraints.EAST;
        gbc_lblVorname.gridx = 0;
        gbc_lblVorname.gridy = 0;
        add(lblVorname, gbc_lblVorname);

        txtVorname = new JTextField();
        GridBagConstraints gbc_txtVorname = new GridBagConstraints();
        gbc_txtVorname.insets = new Insets(0, 0, 5, 0);
        gbc_txtVorname.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtVorname.gridx = 1;
        gbc_txtVorname.gridy = 0;
        add(txtVorname, gbc_txtVorname);
        txtVorname.setColumns(20);

        JLabel lblNachname = new JLabel("Nachname:");
        GridBagConstraints gbc_lblNachname = new GridBagConstraints();
        gbc_lblNachname.insets = new Insets(0, 0, 5, 5);
        gbc_lblNachname.anchor = GridBagConstraints.EAST;
        gbc_lblNachname.gridx = 0;
        gbc_lblNachname.gridy = 1;
        add(lblNachname, gbc_lblNachname);

        txtNachname = new JTextField();
        GridBagConstraints gbc_txtNachname = new GridBagConstraints();
        gbc_txtNachname.insets = new Insets(0, 0, 5, 0);
        gbc_txtNachname.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNachname.gridx = 1;
        gbc_txtNachname.gridy = 1;
        add(txtNachname, gbc_txtNachname);
        txtNachname.setColumns(20);

        JLabel lblNutzername = new JLabel("Nutzername:");
        GridBagConstraints gbc_lblNutzername = new GridBagConstraints();
        gbc_lblNutzername.insets = new Insets(0, 0, 5, 5);
        gbc_lblNutzername.anchor = GridBagConstraints.EAST;
        gbc_lblNutzername.gridx = 0;
        gbc_lblNutzername.gridy = 2;
        add(lblNutzername, gbc_lblNutzername);

        txtNutzername = new JTextField();
        GridBagConstraints gbc_txtNutzername = new GridBagConstraints();
        gbc_txtNutzername.insets = new Insets(0, 0, 5, 0);
        gbc_txtNutzername.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNutzername.gridx = 1;
        gbc_txtNutzername.gridy = 2;
        add(txtNutzername, gbc_txtNutzername);
        txtNutzername.setColumns(20);

        JLabel lblPasswort = new JLabel("Passwort:");
        GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
        gbc_lblPasswort.anchor = GridBagConstraints.EAST;
        gbc_lblPasswort.insets = new Insets(0, 0, 5, 5);
        gbc_lblPasswort.gridx = 0;
        gbc_lblPasswort.gridy = 3;
        add(lblPasswort, gbc_lblPasswort);

        pwdPasswort = new JPasswordField();
        pwdPasswort.setColumns(20);
        GridBagConstraints gbc_pwdPasswort = new GridBagConstraints();
        gbc_pwdPasswort.insets = new Insets(0, 0, 5, 0);
        gbc_pwdPasswort.fill = GridBagConstraints.HORIZONTAL;
        gbc_pwdPasswort.gridx = 1;
        gbc_pwdPasswort.gridy = 3;
        add(pwdPasswort, gbc_pwdPasswort);

        JLabel lblPasswortWiederholen = new JLabel("Passwort wiederholen:");
        GridBagConstraints gbc_lblPasswortWiederholen = new GridBagConstraints();
        gbc_lblPasswortWiederholen.anchor = GridBagConstraints.EAST;
        gbc_lblPasswortWiederholen.insets = new Insets(0, 0, 5, 5);
        gbc_lblPasswortWiederholen.gridx = 0;
        gbc_lblPasswortWiederholen.gridy = 4;
        add(lblPasswortWiederholen, gbc_lblPasswortWiederholen);

        pwdZweitespasswort = new JPasswordField();
        pwdZweitespasswort.setColumns(20);
        GridBagConstraints gbc_pwdZweitespasswort = new GridBagConstraints();
        gbc_pwdZweitespasswort.insets = new Insets(0, 0, 5, 0);
        gbc_pwdZweitespasswort.fill = GridBagConstraints.HORIZONTAL;
        gbc_pwdZweitespasswort.gridx = 1;
        gbc_pwdZweitespasswort.gridy = 4;
        add(pwdZweitespasswort, gbc_pwdZweitespasswort);

        rdbtnKunde = new JRadioButton("Kunde");
        rdbtnKunde.setSelected(true);
        BenutzerTyp.add(rdbtnKunde);
        GridBagConstraints gbc_rdbtnKunde = new GridBagConstraints();
        gbc_rdbtnKunde.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnKunde.gridx = 0;
        gbc_rdbtnKunde.gridy = 5;
        add(rdbtnKunde, gbc_rdbtnKunde);

        rdbtnArbeiter = new JRadioButton("Arbeiter");
        BenutzerTyp.add(rdbtnArbeiter);
        GridBagConstraints gbc_rdbtnArbeiter = new GridBagConstraints();
        gbc_rdbtnArbeiter.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnArbeiter.gridx = 1;
        gbc_rdbtnArbeiter.gridy = 5;
        add(rdbtnArbeiter, gbc_rdbtnArbeiter);

        lblZweitInformation = new JLabel("Adresse:");
        GridBagConstraints gbc_lblZweitInformation = new GridBagConstraints();
        gbc_lblZweitInformation.anchor = GridBagConstraints.EAST;
        gbc_lblZweitInformation.insets = new Insets(0, 0, 5, 5);
        gbc_lblZweitInformation.gridx = 0;
        gbc_lblZweitInformation.gridy = 6;
        add(lblZweitInformation, gbc_lblZweitInformation);

        txtAdresse = new JTextField();
        GridBagConstraints gbc_txtAdresse = new GridBagConstraints();
        gbc_txtAdresse.insets = new Insets(0, 0, 5, 0);
        gbc_txtAdresse.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtAdresse.gridx = 1;
        gbc_txtAdresse.gridy = 6;
        add(txtAdresse, gbc_txtAdresse);
        txtAdresse.setColumns(10);

        pwdMasterpasswort = new JPasswordField();
        GridBagConstraints gbc_pwdMasterpasswort = new GridBagConstraints();
        gbc_pwdMasterpasswort.insets = new Insets(0, 0, 5, 0);
        gbc_pwdMasterpasswort.fill = GridBagConstraints.HORIZONTAL;
        gbc_pwdMasterpasswort.gridx = 1;
        gbc_pwdMasterpasswort.gridy = 6;
        add(pwdMasterpasswort, gbc_pwdMasterpasswort);
        pwdMasterpasswort.setVisible(false);

        btnRegistrieren = new JButton("Registrieren");
        GridBagConstraints gbc_btnRegistrieren = new GridBagConstraints();
        gbc_btnRegistrieren.gridwidth = 2;
        gbc_btnRegistrieren.gridx = 0;
        gbc_btnRegistrieren.gridy = 8;
        add(btnRegistrieren, gbc_btnRegistrieren);
    }

    private void setupEvents() {
        rdbtnKunde.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                radioButtonChanged();
            }
        });

        rdbtnArbeiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                radioButtonChanged();
            }
        });

        btnRegistrieren.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                try {
                    registrieren();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        KeyAdapter registerKey = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        registrieren();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        pwdPasswort.addKeyListener(registerKey);
        pwdMasterpasswort.addKeyListener(registerKey);
        pwdZweitespasswort.addKeyListener(registerKey);
        txtAdresse.addKeyListener(registerKey);
        txtNutzername.addKeyListener(registerKey);
    }

    private void radioButtonChanged() {
        if (BenutzerTyp.isSelected(rdbtnArbeiter.getModel())) {
            lblZweitInformation.setText("Masterpasswort:");
            pwdMasterpasswort.setVisible(true);
            txtAdresse.setVisible(false);
        }else if (BenutzerTyp.isSelected(rdbtnKunde.getModel())) {
            lblZweitInformation.setText("Adresse:");
            txtAdresse.setVisible(true);
            pwdMasterpasswort.setVisible(false);
        }
        txtAdresse.setText("");
        pwdMasterpasswort.setText("");
    }

    private void registrieren() throws IOException {
        String master = "test";
        String vorname = txtVorname.getText();
        String nachname = txtVorname.getText();
        //String vorname = "Max";
        //String nachname = "Mustermann";
        String login = txtNutzername.getText();
        String passwort = String.valueOf(pwdPasswort.getPassword());
        String zweitPasswort = String.valueOf(pwdZweitespasswort.getPassword());

        if (!vorname.isEmpty() && !passwort.isEmpty() && !zweitPasswort.isEmpty()) {
            if (passwort.equals(zweitPasswort)) {
                if (BenutzerTyp.isSelected(rdbtnArbeiter.getModel())) {
                    if (String.valueOf(pwdMasterpasswort.getPassword()).equals(master)) {
                        try {
                            Mitarbeiter m = new Mitarbeiter(vorname, nachname, login, passwort);
                            shop.registrieren(m);
                        } catch (BenutzerExistiertBereitsException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }else if (BenutzerTyp.isSelected(rdbtnKunde.getModel())) {
                    if(!txtAdresse.getText().isEmpty()) {
                        try {
                            Kunde k = new Kunde(vorname, nachname, login, passwort, txtAdresse.getText());
                            shop.registrieren(k);
                        } catch (BenutzerExistiertBereitsException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(this, "Bitte etwas eingeben!!", "Error", JOptionPane.INFORMATION_MESSAGE);
                        txtNutzername.setText("");
                        txtAdresse.setText("");
                        pwdPasswort.setText("");
                        pwdMasterpasswort.setText("");
                        pwdZweitespasswort.setText("");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passw�rter nicht gleich", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(this, "Bitte etwas eingeben!!", "Error", JOptionPane.INFORMATION_MESSAGE);
            txtNutzername.setText("");
            txtAdresse.setText("");
            pwdPasswort.setText("");
            pwdMasterpasswort.setText("");
            pwdZweitespasswort.setText("");
        }

    }
}

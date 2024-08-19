package it.unibo.myvet.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unibo.myvet.dao.AnimalDAO;
import it.unibo.myvet.model.Account;
import it.unibo.myvet.model.Animal;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class PrivateView {

    public PrivateView() {
        JFrame mainFrame = new JFrame("Main View");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);

        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Barra di ricerca
        JLabel searchLabel = new JLabel("Search Animal:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainFrame.add(searchLabel, gbc);

        JTextField searchField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainFrame.add(searchField, gbc);

        JButton searchButton = new JButton("Search");
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainFrame.add(searchButton, gbc);

        // Tabella per visualizzare i risultati della ricerca
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[] { "ID", "Nome", "Cognome", "Telefono", "Data di Nascita" }, 0);
        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(scrollPane, gbc);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                searchAnimals(searchText, tableModel);
            }
        });

        JButton signupButton = new JButton("Register your pet");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.NONE;
        mainFrame.add(signupButton, gbc);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignupView();
            }
        });

        mainFrame.setVisible(true);
    }

    private void showSignupView() {
        JFrame signupFrame = new JFrame("Sign Up");
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setSize(350, 350);
        signupFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel IDLabel = new JLabel("Id Animale:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        signupFrame.add(IDLabel, gbc);

        JTextField IDField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signupFrame.add(IDField, gbc);

        JLabel nomeLabel = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        signupFrame.add(nomeLabel, gbc);

        JTextField nomeField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        signupFrame.add(nomeField, gbc);

        JLabel cognomeLabel = new JLabel("Cognome:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        signupFrame.add(cognomeLabel, gbc);

        JTextField cognomeField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        signupFrame.add(cognomeField, gbc);

        JLabel telefonoLabel = new JLabel("Telefono:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        signupFrame.add(telefonoLabel, gbc);

        JTextField telefonoField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        signupFrame.add(telefonoField, gbc);

        JLabel nascitaLabel = new JLabel("Data di nascita (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        signupFrame.add(nascitaLabel, gbc);

        JTextField dataNascitaField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        signupFrame.add(dataNascitaField, gbc);

        JButton submitButton = new JButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signupFrame.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idAnimale = IDField.getText();
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String telefono = telefonoField.getText();
                String dataNascitaStr = dataNascitaField.getText();

                java.sql.Date dataNascita = null;
                try {
                    dataNascita = java.sql.Date.valueOf(dataNascitaStr);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(signupFrame,
                            "Errore: formato della data non valido. Usa il formato YYYY-MM-DD.");
                    return;
                }

                if (registerAnimal(idAnimale, nome, cognome, telefono, dataNascita)) {
                    JOptionPane.showMessageDialog(signupFrame, "Registrazione avvenuta con successo!");
                    signupFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(signupFrame, "Errore durante la registrazione.");
                }
            }
        });

        signupFrame.setVisible(true);
    }

    private boolean registerAnimal(String idAnimale, String nome, String cognome, String telefono,
            java.sql.Date dataNascita) {
        AnimalDAO animalDAO = new AnimalDAO();
        boolean isRegistered = false;
        try {
            animalDAO.save(new Animal(0, nome, cognome, telefono, 0, 0));
            isAuthenticated = true;
        } catch (Exception e) {
        }
        return isAuthenticated;
    }

    private void searchAnimals(String searchText, DefaultTableModel tableModel) {
        String query = "SELECT * FROM animale WHERE nome LIKE ? OR cognome LIKE ?";
        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(query)) {
            statement.setString(1, "%" + searchText + "%");
            statement.setString(2, "%" + searchText + "%");
            ResultSet resultSet = statement.executeQuery();

            // Clear the existing rows
            tableModel.setRowCount(0);

            // Add new rows
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String telefono = resultSet.getString("telefono");
                java.sql.Date dataNascita = resultSet.getDate("data_nascita");
                tableModel.addRow(new Object[] { id, nome, cognome, telefono, dataNascita });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PrivateView::new);
    }
}

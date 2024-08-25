package it.unibo.myvet.view;

import javax.swing.*;
import java.awt.*;

import it.unibo.myvet.controller.AppointmentListController;
import it.unibo.myvet.dao.AccountDAO;
import it.unibo.myvet.dao.SpecializationDAO;
import it.unibo.myvet.dao.UserDAO;
import it.unibo.myvet.dao.VetDAO;
import it.unibo.myvet.model.Specialization;
import it.unibo.myvet.model.User;
import it.unibo.myvet.model.Vet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginView {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    VetDAO veterinarianDAO = new VetDAO();
    AccountDAO acc = new AccountDAO();
    UserDAO userDAO = new UserDAO();
    User user = null;
    Vet vet = null;

    public LoginView() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setResizable(false);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("CF:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(usernameLabel, gbc);

        usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        frame.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        frame.add(loginButton, gbc);

        JButton signupButton = new JButton("Sign Up");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        frame.add(signupButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    frame.dispose();
                    if (isVeterinarian(username)) {
                        showVetView(vet);
                    } else if (isUser(username)) {
                        showPrivateView(user);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Account is not a User, neither a Vet");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignupView();
            }
        });

        frame.setVisible(true);
    }

    SpecializationDAO specializationDAO = new SpecializationDAO();

    private void showSignupView() {
        JFrame signupFrame = new JFrame("Sign Up");
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setSize(500, 400);
        signupFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel cfLabel = new JLabel("Codice Fiscale:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        signupFrame.add(cfLabel, gbc);

        JTextField cfField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signupFrame.add(cfField, gbc);

        JLabel nomeLabel = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 1;
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

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        signupFrame.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        signupFrame.add(passwordField, gbc);

        JLabel telefonoLabel = new JLabel("Telefono:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        signupFrame.add(telefonoLabel, gbc);

        JTextField telefonoField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        signupFrame.add(telefonoField, gbc);

        JCheckBox isVeterinarianCheckbox = new JCheckBox("Registrati come Veterinario");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signupFrame.add(isVeterinarianCheckbox, gbc);

        // Aggiungi il JComboBox per la selezione delle specializzazioni

        JComboBox<Specialization> specializationComboBox = new JComboBox<>();
        specializationComboBox.setEnabled(false); // Disabilitato finché il checkbox non è selezionato
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        signupFrame.add(specializationComboBox, gbc);
        loadSpecializations(specializationComboBox);

        JButton submitButton = new JButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        signupFrame.add(submitButton, gbc);

        isVeterinarianCheckbox.addItemListener(e -> {
            boolean isSelected = isVeterinarianCheckbox.isSelected();
            specializationComboBox.setEnabled(isSelected); // Abilita/disabilita la JComboBox
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String CF = cfField.getText();
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String password = new String(passwordField.getPassword());
                String telefono = telefonoField.getText();
                boolean isVeterinarian = isVeterinarianCheckbox.isSelected();
                int specializationIndex = specializationComboBox.getSelectedIndex() + 1; // Specializzazione selezionata
                                                                                         // (come int)

                if (registerAccount(CF, nome, cognome, password, telefono, isVeterinarian, specializationIndex)) {
                    JOptionPane.showMessageDialog(signupFrame, "Registrazione avvenuta con successo!");
                    signupFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(signupFrame, "Errore durante la registrazione.");
                }
            }
        });

        signupFrame.setVisible(true);
    }

    private boolean registerAccount(String CF, String nome, String cognome, String password, String telefono,
            boolean isVeterinarian, int specialization) {
        boolean isRegistered = false;
        try {
            if (isVeterinarian) {
                // Registra come Veterinario con specializzazione
                this.vet = new Vet(CF, nome, cognome, password, telefono,
                        new Specialization(String.valueOf(specialization)));

                veterinarianDAO.save(this.vet);
            } else {
                // Registra come Utente
                this.user = new User(CF, nome, cognome, password, telefono);
                userDAO.save(this.user);
            }
            isRegistered = true;
        } catch (Exception e) {
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato
        }
        return isRegistered;
    }

    private boolean authenticate(String CF, String password) {
        boolean isAuthenticated = false;
        if (acc.authenticate(CF, password) != null) {
            isAuthenticated = true;

        }
        return isAuthenticated;
    }

    private boolean isVeterinarian(String CF) {
        this.vet = veterinarianDAO.findByCf(CF);
        return this.vet != null;
    }

    private boolean isUser(String CF) {
        this.user = userDAO.findByCf(CF);
        return this.user != null;
    }

    private void showPrivateView(User user) {
        new PrivateView(user);
    }

    private void showVetView(Vet vet) {
        new VetInterfaceView(vet);
    }

    private void loadSpecializations(JComboBox<Specialization> specializationComboBox) {
        specializationComboBox.removeAllItems();

        specializationComboBox.addItem(null);

        List<Specialization> speciesList = specializationDAO.findAll();

        for (Specialization species : speciesList) {
            specializationComboBox.addItem(species);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView());
    }
}

package it.unibo.myvet.view;

import javax.swing.*;
import java.awt.*;

import it.unibo.myvet.dao.AccountDAO;
import it.unibo.myvet.dao.UserDAO;
import it.unibo.myvet.model.Account;
import it.unibo.myvet.model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginView {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

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
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        frame.add(loginButton, gbc);

        JButton signupButton = new JButton("Sign Up");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        frame.add(signupButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose();
                    showPrivateView();
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

    private void showSignupView() {
        JFrame signupFrame = new JFrame("Sign Up");
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setSize(350, 300);
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

        JButton submitButton = new JButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signupFrame.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String CF = cfField.getText();
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String password = new String(passwordField.getPassword());
                String telefono = telefonoField.getText();
                if (registerAccount(CF, nome, cognome, password, telefono)) {
                    JOptionPane.showMessageDialog(signupFrame, "Registrazione avvenuta con successo!");
                    signupFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(signupFrame, "Errore durante la registrazione.");
                }
            }
        });

        signupFrame.setVisible(true);
    }
    AccountDAO acc = new AccountDAO();
    UserDAO userDAO=new UserDAO();
    User user=null;
    int userID=0;
    private boolean registerAccount(String CF, String nome, String cognome, String password, String telefono) {
        boolean isAuthenticated = false;
        try {
            acc.save(new Account(CF, password, cognome, password, telefono));
            user=new User(CF, password, cognome, password, telefono);
            userDAO.save(user);
            userID=user.getUserId();
            isAuthenticated = true;
        } catch (Exception e) {
        }
        return isAuthenticated;
    }

    private boolean authenticate(String CF, String password) {
        boolean isAuthenticated = false;
        if (acc.authenticate(CF, password) != null) {
            isAuthenticated = true;
        }
        return isAuthenticated;
    }

    private void showPrivateView() {
        new PrivateView(userID);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView());
    } 
}

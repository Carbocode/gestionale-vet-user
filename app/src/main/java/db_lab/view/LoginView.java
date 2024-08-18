package db_lab.view;

import javax.swing.*;

import db_lab.data.DAOUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginView {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginView() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setResizable(false);
        frame.setLayout(null);

        JLabel usernameLabel = new JLabel("CF:");
        usernameLabel.setBounds(20, 20, 100, 25);
        frame.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 20, 150, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 60, 100, 25);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 60, 150, 25);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 100, 30);
        frame.add(loginButton);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(100, 100, 100, 30);
        frame.add(signupButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose(); 
                    showMainView();
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
        signupFrame.setLayout(null);

        JLabel cfLabel = new JLabel("Codice Fiscale:");
        cfLabel.setBounds(20, 20, 100, 25);
        signupFrame.add(cfLabel);

        JTextField cfField = new JTextField();
        cfField.setBounds(130, 20, 180, 25);
        signupFrame.add(cfField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 140, 100, 25);
        signupFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(130, 140, 180, 25);
        signupFrame.add(passwordField);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(20, 60, 100, 25);
        signupFrame.add(nomeLabel);

        JTextField nomeField = new JTextField();
        nomeField.setBounds(130, 60, 180, 25);
        signupFrame.add(nomeField);

        JLabel cognomeLabel = new JLabel("Cognome:");
        cognomeLabel.setBounds(20, 100, 100, 25);
        signupFrame.add(cognomeLabel);

        JTextField cognomeField = new JTextField();
        cognomeField.setBounds(130, 100, 180, 25);
        signupFrame.add(cognomeField);

        JLabel telefonoLabel = new JLabel("Telefono:");
        passwordLabel.setBounds(20, 140, 100, 25);
        signupFrame.add(telefonoLabel);

        JTextField telefonoField = new JTextField();
        passwordField.setBounds(130, 140, 180, 25);
        signupFrame.add(telefonoField);
       
        JButton submitButton = new JButton("Sign Up");
        submitButton.setBounds(110, 200, 100, 30);
        signupFrame.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String CF = cfField.getText();
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String password = new String(passwordField.getPassword());
                String telefono = telefonoField.getText();
                if (registerAccount(CF, nome, cognome, password,telefono)) {
                    JOptionPane.showMessageDialog(signupFrame, "Registrazione avvenuta con successo!");
                    signupFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(signupFrame, "Errore durante la registrazione.");
                }
            }
        });

        signupFrame.setVisible(true);
    }

    private boolean registerAccount(String CF, String nome, String cognome, String password, String telefono) {
        boolean isRegistered = false;
        try (Connection conn = DAOUtils.localMySQLConnection("myvet", "root", "Dajeroma")) {
            String query = "INSERT INTO account (CF, password, nome, cognome, telefono) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, CF);
                pstmt.setString(2, password);
                pstmt.setString(3, nome);
                pstmt.setString(4, cognome);
                pstmt.setString(5, telefono);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    isRegistered = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRegistered;
    }

    private boolean authenticate(String CF, String password) {
        boolean isAuthenticated = false;
        try (Connection conn = DAOUtils.localMySQLConnection("myvet", "root", "Dajeroma")) {
            String query = "SELECT * FROM account WHERE CF = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, CF);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        isAuthenticated = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }

    private void showMainView() {
        // Crea e mostra la view principale
        JFrame mainFrame = new JFrame("Main View");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        JLabel welcomeLabel = new JLabel("Welcome, " + usernameField.getText() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainFrame.add(welcomeLabel);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView());
    }
}

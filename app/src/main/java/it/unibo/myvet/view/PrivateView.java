package it.unibo.myvet.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import it.unibo.myvet.dao.AccountDAO;
import it.unibo.myvet.dao.AnimalDAO;
import it.unibo.myvet.dao.BreedDAO;
import it.unibo.myvet.dao.SpeciesDAO;
import it.unibo.myvet.dao.UserDAO;
import it.unibo.myvet.dao.VetDAO;
import it.unibo.myvet.model.Animal;
import it.unibo.myvet.model.Breed;
import it.unibo.myvet.model.Species;

public class PrivateView {
    int userId=0;

    public PrivateView(int userID) {
        this.userId = userID;
        JFrame mainFrame = new JFrame("Main View");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search Vet:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        mainFrame.add(topPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Nome", "Cognome"}, 0);
        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton signupButton = new JButton("Register your pet");
        bottomPanel.add(signupButton);

        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                searchVet(searchText, tableModel);
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignupView();
            }
        });

        mainFrame.setVisible(true);
    }
    UserDAO userDAO=new UserDAO();
    SpeciesDAO speciesDAO = new SpeciesDAO();
    BreedDAO breedDAO = new BreedDAO(speciesDAO);
    AnimalDAO animalDAO = new AnimalDAO(userDAO, breedDAO);

    private void showSignupView() {
        JFrame signupFrame = new JFrame("Sign Up");
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setSize(500, 600);
        signupFrame.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        JLabel nomeLabel = new JLabel("Nome:");
        signupFrame.add(nomeLabel, gbc);
    
        gbc.gridx = 1;
        JTextField nomeField = new JTextField();
        signupFrame.add(nomeField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel telefonoLabel = new JLabel("Telefono:");
        signupFrame.add(telefonoLabel, gbc);
    
        gbc.gridx = 1;
        JTextField telefonoField = new JTextField();
        signupFrame.add(telefonoField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel nascitaLabel = new JLabel("Data di nascita (YYYY-MM-DD):");
        signupFrame.add(nascitaLabel, gbc);
    
        gbc.gridx = 1;
        JTextField dataNascitaField = new JTextField();
        signupFrame.add(dataNascitaField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel speciesLabel = new JLabel("Specie:");
        signupFrame.add(speciesLabel, gbc);
    
        gbc.gridx = 1;
        JComboBox<Species> speciesComboBox = new JComboBox<>();
        signupFrame.add(speciesComboBox, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel breedLabel = new JLabel("Razza:");
        signupFrame.add(breedLabel, gbc);
    
        gbc.gridx = 1;
        JComboBox<Breed> breedComboBox = new JComboBox<>();
        signupFrame.add(breedComboBox, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Sign Up");
        signupFrame.add(submitButton, gbc);
    
        // Load species and breeds
        loadSpecies(speciesComboBox);
    
        speciesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Species selectedSpecies = (Species) speciesComboBox.getSelectedItem();
                    loadBreeds(breedComboBox, selectedSpecies);
                }
            }
        });
    
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String telefono = telefonoField.getText();
                String dataNascitaStr = dataNascitaField.getText();
                Species selectedSpecies = (Species) speciesComboBox.getSelectedItem();
                Breed selectedBreed = (Breed) breedComboBox.getSelectedItem();
    
                LocalDate dataNascita = null;
                try {
                    dataNascita = Date.valueOf(dataNascitaStr).toLocalDate();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(signupFrame, "Errore: formato della data non valido. Usa il formato YYYY-MM-DD.");
                    return;
                }
    
                if (registerAnimal(nome, telefono, dataNascita, selectedSpecies, selectedBreed)) {
                    JOptionPane.showMessageDialog(signupFrame, "Registrazione avvenuta con successo!");
                    signupFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(signupFrame, "Errore durante la registrazione.");
                }
            }
        });
    
        signupFrame.setVisible(true);
    }
    
    private void loadSpecies(JComboBox<Species> speciesComboBox) {
       
        List<Species> speciesList = speciesDAO.findAll();
        for (Species species : speciesList) {
            speciesComboBox.addItem(species);
        }
    }

    private void loadBreeds(JComboBox<Breed> breedComboBox, Species species) {
        breedComboBox.removeAllItems();
        
        List<Breed> breeds = breedDAO.findAll();
        for (Breed breed : breeds) {
            breedComboBox.addItem(breed);
        }
    }

    private boolean registerAnimal(String nome, String telefono, LocalDate dataNascita, Species species, Breed breed) {
        
        boolean isRegistered = false;
        try {
            Animal animal = new Animal(userId, telefono, dataNascita, userDAO.findById(userId), breed);
            animalDAO.save(animal);
            isRegistered = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRegistered;
    }

    private void searchVet(String searchText, DefaultTableModel tableModel) {
        VetDAO vetDAO = new VetDAO();
        vetDAO.searchVet(searchText, tableModel);
    }
}

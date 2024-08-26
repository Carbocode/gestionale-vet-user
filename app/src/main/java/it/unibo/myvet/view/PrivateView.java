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
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import it.unibo.myvet.controller.ButtonEditor;
import it.unibo.myvet.controller.ButtonRenderer;
import it.unibo.myvet.dao.AnimalDAO;
import it.unibo.myvet.dao.AppointmentDAO;
import it.unibo.myvet.dao.BreedDAO;
import it.unibo.myvet.dao.FavouriteVetDAO;
import it.unibo.myvet.dao.SpeciesDAO;
import it.unibo.myvet.dao.UserDAO;
import it.unibo.myvet.dao.VetDAO;
import it.unibo.myvet.model.Animal;
import it.unibo.myvet.model.Appointment;
import it.unibo.myvet.model.AppointmentState;
import it.unibo.myvet.model.Breed;
import it.unibo.myvet.model.FavouriteVet;
import it.unibo.myvet.model.Sex;
import it.unibo.myvet.model.Species;
import it.unibo.myvet.model.User;
import it.unibo.myvet.model.Vet;

public class PrivateView {
    User user;
    JFrame mainFrame;
    JFrame signupFrame;
    JTable animalsTable;
    JTable resultsTable;
    DefaultListModel<String> favoriteVetsModel;
    JList<String> favoriteVetsList;
    List<Vet> favoriteVets = new ArrayList<>();
    JFrame appointmentFrame;
    DefaultTableModel appointmentsTableModel;

    UserDAO userDAO = new UserDAO();
    SpeciesDAO speciesDAO = new SpeciesDAO();
    BreedDAO breedDAO = new BreedDAO();
    AnimalDAO animalDAO = new AnimalDAO();
    FavouriteVetDAO favouriteVetDAO = new FavouriteVetDAO();
    VetDAO vetDAO = new VetDAO();

    public PrivateView(User user) {
        this.user = user;

        mainFrame = new JFrame("Main View");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 800);
        mainFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search Vet:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        mainFrame.add(topPanel, BorderLayout.NORTH);

        DefaultTableModel searchTableModel = new DefaultTableModel(new Object[] { "Nome", "Cognome", "Preferito" }, 0);
        resultsTable = new JTable(searchTableModel);
        resultsTable.getColumn("Preferito").setCellRenderer(new ButtonRenderer());
        resultsTable.getColumn("Preferito").setCellEditor(new ButtonEditor(new JCheckBox(), resultsTable));
        JScrollPane searchScrollPane = new JScrollPane(resultsTable);
        mainFrame.add(searchScrollPane, BorderLayout.CENTER);

        JPanel favoritesPanel = new JPanel(new BorderLayout());
        JLabel favoritesLabel = new JLabel("Favorite Vets:");
        favoritesPanel.add(favoritesLabel, BorderLayout.NORTH);

        favoriteVetsModel = new DefaultListModel<>();
        favoriteVetsList = new JList<>(favoriteVetsModel);
        JScrollPane favoritesScrollPane = new JScrollPane(favoriteVetsList);
        favoritesPanel.add(favoritesScrollPane, BorderLayout.CENTER);

        mainFrame.add(favoritesPanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton signupButton = new JButton("Register your pet");
        JButton appointmentButton = new JButton("Book an Appointment");
        bottomPanel.add(signupButton);
        bottomPanel.add(appointmentButton);

        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        JPanel animalsPanel = new JPanel(new BorderLayout());
        JLabel animalsLabel = new JLabel("I miei animali:");
        animalsPanel.add(animalsLabel, BorderLayout.NORTH);

        animalsTable = new JTable(
                new DefaultTableModel(new Object[] { "Nome", "Specie", "Razza", "Sesso", "Data di nascita" }, 0));
        JScrollPane animalsScrollPane = new JScrollPane(animalsTable);
        animalsPanel.add(animalsScrollPane, BorderLayout.CENTER);

        mainFrame.add(animalsPanel, BorderLayout.EAST);

        JButton viewAppointmentsButton = new JButton("View Appointments");
        animalsPanel.add(viewAppointmentsButton, BorderLayout.SOUTH);

        // Usa JSplitPane per visualizzare il pannello degli animali e dei preferiti
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setLeftComponent(animalsPanel); // Pannello degli animali
        splitPane.setRightComponent(favoritesPanel); // Pannello dei preferiti

        mainFrame.add(splitPane, BorderLayout.EAST); // Usa BorderLayout.CENTER per il contenuto centrale

        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointmentsView();
            }
        });

        // Carica gli animali dell'utente
        loadUserAnimals();
        loadUserFavorites();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                searchVet(searchText, searchTableModel);
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignupView();
            }
        });

        appointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointmentView();
            }
        });

        mainFrame.setVisible(true);
    }

    private void showSignupView() {
        JFrame signupFrame = new JFrame("Sign Up");
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setSize(600, 600);
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
        JLabel nascitaLabel = new JLabel("Data di nascita (YYYY-MM-DD):");
        signupFrame.add(nascitaLabel, gbc);

        gbc.gridx = 1;
        JTextField dataNascitaField = new JTextField();
        signupFrame.add(dataNascitaField, gbc);

        // Aggiunta della JComboBox per il sesso
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel sexLabel = new JLabel("Sesso:");
        signupFrame.add(sexLabel, gbc);

        gbc.gridx = 1;
        JComboBox<Sex> sexComboBox = new JComboBox<>(Sex.values());
        signupFrame.add(sexComboBox, gbc);

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
                String dataNascitaStr = dataNascitaField.getText();
                Species selectedSpecies = (Species) speciesComboBox.getSelectedItem();
                Breed selectedBreed = (Breed) breedComboBox.getSelectedItem();
                Sex selectedSex = (Sex) sexComboBox.getSelectedItem(); // Ottieni il sesso selezionato

                LocalDate dataNascita = null;
                try {
                    dataNascita = Date.valueOf(dataNascitaStr).toLocalDate();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(signupFrame,
                            "Errore: formato della data non valido. Usa il formato YYYY-MM-DD.");
                    return;
                }

                if (registerAnimal(nome, dataNascita, selectedSex, selectedSpecies, selectedBreed)) { // Passa il sesso
                                                                                                      // selezionato
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
        speciesComboBox.removeAllItems();
        speciesComboBox.addItem(null);
        List<Species> speciesList = speciesDAO.findAll();
        for (Species species : speciesList) {
            speciesComboBox.addItem(species);
        }
    }

    private void loadBreeds(JComboBox<Breed> breedComboBox, Species species) {
        breedComboBox.removeAllItems(); // Rimuovi tutte le razze attuali
        breedComboBox.addItem(null);
        if (species == null) {
            System.out.println("Specie non selezionata o nullo.");
            return;
        }

        int speciesId = species.getSpeciesId();
        System.out.println("Caricamento razze per specie ID: " + speciesId);

        List<Breed> breeds = breedDAO.findBySpeciesId(speciesId);
        if (breeds == null || breeds.isEmpty()) {
            System.out.println("Nessuna razza trovata per la specie con ID: " + speciesId);
        } else {
            for (Breed breed : breeds) {
                breedComboBox.addItem(breed);
            }
        }
    }

    private boolean registerAnimal(String nome, LocalDate dataNascita, Sex sex, Species species, Breed breed) {
        boolean isRegistered = false;
        System.out.println(this.user.toString());
        try {

            Animal animal = new Animal(nome, dataNascita, sex, this.user, breed);
            animalDAO.save(animal);
            isRegistered = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRegistered;
    }

    private void searchVet(String searchText, DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clears the table

        VetDAO vetDAO = new VetDAO();
        List<Vet> vets = vetDAO.searchVet(searchText);
        System.out.println("Searching vets with: " + searchText);
        System.out.println("Found " + vets.size() + " vets");
        for (Vet vet : vets) {
            JButton addButton = new JButton("Preferito");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addVetToFavorites(vet);
                }
            });

            tableModel.addRow(new Object[] { vet.getFirstName(), vet.getLastName(), addButton });
        }
    }

    private void addVetToFavorites(Vet vet) {
        if (!favoriteVets.contains(vet)) {
            favoriteVets.add(vet);
            favoriteVetsModel.addElement(vet.getFirstName() + " " + vet.getLastName());

            FavouriteVet favouriteVet = new FavouriteVet(user.getUserId(), vet.getVetId());
            favouriteVetDAO.save(favouriteVet);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Vet is already in your favorites!");
        }
    }

    private void loadUserFavorites() {
        favoriteVets.clear();
        favoriteVetsModel.clear();

        List<FavouriteVet> favourites = favouriteVetDAO.findByUserId(user.getUserId());
        System.out.println("Loading user favorites...");
        System.out.println("Number of favorite vets: " + favourites.size());

        for (FavouriteVet favourite : favourites) {
            Vet vet = vetDAO.findById(favourite.getVetId());
            if (vet != null) {
                favoriteVets.add(vet);
                favoriteVetsModel.addElement(vet.getFirstName() + " " + vet.getLastName());
                System.out.println("Added vet to list: " + vet.getFirstName() + " " + vet.getLastName());
            } else {
                System.out.println("Vet with ID " + favourite.getVetId() + " not found.");
            }
        }
    }

    private void showAppointmentView() {
        appointmentFrame = new JFrame("Book Appointment");
        appointmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        appointmentFrame.setSize(800, 400);
        appointmentFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Imposta una dimensione minima per le JLabel
        Dimension labelSize = new Dimension(200, 20);

        JLabel animalLabel = new JLabel("Select Animal:");
        animalLabel.setPreferredSize(labelSize);
        appointmentFrame.add(animalLabel, gbc);

        gbc.gridx = 1;
        JComboBox<Animal> animalComboBox = new JComboBox<>();
        appointmentFrame.add(animalComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel vetLabel = new JLabel("Select Vet:");
        vetLabel.setPreferredSize(labelSize);
        appointmentFrame.add(vetLabel, gbc);

        gbc.gridx = 1;
        JComboBox<Vet> vetComboBox = new JComboBox<>();
        appointmentFrame.add(vetComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel dateLabel = new JLabel("Appointment Date (YYYY-MM-DD):");
        dateLabel.setPreferredSize(labelSize);
        appointmentFrame.add(dateLabel, gbc);

        gbc.gridx = 1;
        JTextField dateField = new JTextField();
        appointmentFrame.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel timeLabel = new JLabel("Appointment Time (HH:MM):");
        timeLabel.setPreferredSize(labelSize);
        appointmentFrame.add(timeLabel, gbc);

        gbc.gridx = 1;
        JTextField timeField = new JTextField();
        appointmentFrame.add(timeField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Book Appointment");
        appointmentFrame.add(submitButton, gbc);

        // Load user's animals
        loadAnimals(animalComboBox);

        // Load available vets
        loadVets(vetComboBox);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Animal selectedAnimal = (Animal) animalComboBox.getSelectedItem();
                Vet selectedVet = (Vet) vetComboBox.getSelectedItem();
                String dateStr = dateField.getText();
                String timeStr = timeField.getText();

                LocalDateTime appointmentDateTime;

                try {
                    appointmentDateTime = LocalDateTime.parse(dateStr + "T" + timeStr);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(appointmentFrame,
                            "Invalid date/time format. Please use the correct formats.");
                    return;
                }

                if (bookAppointment(selectedAnimal, appointmentDateTime, selectedVet)) {
                    JOptionPane.showMessageDialog(appointmentFrame, "Appointment booked successfully!");
                    appointmentFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(appointmentFrame, "Error booking appointment.");
                }
            }
        });

        appointmentFrame.setVisible(true);
    }

    private void loadAnimals(JComboBox<Animal> animalComboBox) {
        animalComboBox.removeAllItems();
        animalComboBox.addItem(null);
        List<Animal> animals = animalDAO.findByOwnerId(this.user.getUserId());
        for (Animal animal : animals) {
            animalComboBox.addItem(animal);
        }
    }

    private void loadVets(JComboBox<Vet> vetComboBox) {
        vetComboBox.removeAllItems();
        vetComboBox.addItem(null);
        VetDAO vetDAO = new VetDAO();
        List<Vet> vets = vetDAO.findAll();
        for (Vet vet : vets) {
            vetComboBox.addItem(vet);
        }
    }

    private boolean bookAppointment(Animal animal, LocalDateTime appointmentDateTime, Vet vet) {
        boolean isBooked = false;
        try {
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            AppointmentState appointmentState = new AppointmentState("Prenotato");
            for (Appointment app : appointmentDAO.findByVetId(vet.getVetId())) {
                if (app.getDateTime() != appointmentDateTime) {
                    Appointment appointment = new Appointment(animal, vet, appointmentDateTime, appointmentState);
                    appointmentDAO.save(appointment);
                    isBooked = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isBooked;
    }

    private void loadUserAnimals() {
        DefaultTableModel model = (DefaultTableModel) animalsTable.getModel();
        model.setRowCount(0); // Pulisce la tabella

        List<Animal> animals = animalDAO.findByOwnerId(user.getUserId());
        for (Animal animal : animals) {
            model.addRow(new Object[] {
                    animal.getName(),
                    animal.getBreed().getSpecies().getSpeciesName(),
                    animal.getBreed().getBreedName(),
                    animal.getSex().toString(),
                    animal.getBirthDate().toString()
            });
        }
    }

    private void showAppointmentsView() {
        JFrame appointmentsFrame = new JFrame("My Appointments");
        appointmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        appointmentsFrame.setSize(800, 400);
        appointmentsFrame.setLayout(new BorderLayout());

        // Modello per la tabella degli appuntamenti
       appointmentsTableModel = new DefaultTableModel(
                new Object[] { "Animal", "Vet", "Date", "Time", "Status", "cANCEL" }, 0);
        JTable appointmentsTable = new JTable(appointmentsTableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        appointmentsFrame.add(scrollPane, BorderLayout.CENTER);

        // Carica gli appuntamenti dell'utente
        loadUserAppointments(appointmentsTableModel);

        appointmentsFrame.setVisible(true);
    }

        private void loadUserAppointments(DefaultTableModel model) {
            model.setRowCount(0); // Clears the table

            AppointmentDAO appointmentDAO = new AppointmentDAO();
            AnimalDAO animalDAO = new AnimalDAO();
            List<Animal> userAnimals = animalDAO.findByOwnerId(user.getUserId());

            for (Animal animal : userAnimals) {
                List<Appointment> appointments = appointmentDAO.findByAnimalId(animal.getAnimalId());

                for (Appointment appointment : appointments) {
                    Vet vet = appointment.getVet();
                    LocalDateTime dateTime = appointment.getDateTime();
                    String status = appointment.getStatus().getStateName();

                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.addActionListener(e -> cancelAppointment(appointment));

                    model.addRow(new Object[] {
                        animal.getName(),
                        vet.getFirstName() + " " + vet.getLastName(),
                        dateTime.toLocalDate().toString(),
                        dateTime.toLocalTime().toString(),
                        status,
                        cancelButton
                    });
                }
            }
        }
    private void cancelAppointment(Appointment appointment) {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        AppointmentState cancelledState = new AppointmentState("Annullato");
        appointment.setStatus(cancelledState);
        appointmentDAO.update(appointment);
        JOptionPane.showMessageDialog(appointmentFrame, "Appointment cancelled successfully!");
        loadUserAppointments(appointmentsTableModel); // Refresh the table
    }

}

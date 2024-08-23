package it.unibo.myvet.view;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import it.unibo.myvet.controller.TherapyListController;
import it.unibo.myvet.model.Animal;
import it.unibo.myvet.model.Appointment;
import it.unibo.myvet.model.AppointmentState;
import it.unibo.myvet.model.Breed;
import it.unibo.myvet.model.Specialization;
import it.unibo.myvet.model.Species;
import it.unibo.myvet.model.User;
import it.unibo.myvet.model.Vet;
import it.unibo.myvet.model.Therapy;

public class TherapyCreationView extends JFrame {

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton createButton;

    private Appointment appointment; // L'appuntamento a cui è associata la terapia
    private TherapyListController therapyListController; // Controller per la gestione della creazione

    public TherapyCreationView(Appointment appointment, TherapyListController therapyListController) {
        this.appointment = appointment;
        this.therapyListController = therapyListController;

        this.createView();
    }

    private void createView() {
        setTitle("Create Therapy");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout manager
        setLayout(new BorderLayout());

        // Pannello centrale con i campi per creare la terapia
        JPanel therapyPanel = new JPanel(new GridLayout(5, 2));
        therapyPanel.setBorder(BorderFactory.createTitledBorder("Therapy Details"));

        therapyPanel.add(new JLabel("Therapy Name:"));
        nameField = new JTextField();
        therapyPanel.add(nameField);

        therapyPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(3, 20);
        therapyPanel.add(new JScrollPane(descriptionArea));

        therapyPanel.add(new JLabel("Start Date (yyyy-MM-dd HH:mm):"));
        startDateField = new JTextField();
        therapyPanel.add(startDateField);

        therapyPanel.add(new JLabel("End Date (yyyy-MM-dd HH:mm):"));
        endDateField = new JTextField();
        therapyPanel.add(endDateField);

        // Pannello inferiore con il pulsante per creare la terapia
        JPanel buttonPanel = new JPanel();
        createButton = new JButton("Create Therapy");
        buttonPanel.add(createButton);

        // Aggiungi i pannelli al frame
        add(therapyPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Gestore dell'evento di clic sul pulsante di creazione
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateTherapy();
            }
        });

        setVisible(true);
    }

    private void handleCreateTherapy() {
        try {
            String name = nameField.getText();
            String description = descriptionArea.getText();
            LocalDateTime startDate = LocalDateTime.parse(startDateField.getText(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime endDate = LocalDateTime.parse(endDateField.getText(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            // Chiamata al controller per creare la terapia
            therapyListController.createTherapy(appointment, name, description, startDate, endDate);

            // Chiudi la finestra dopo la creazione della terapia
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating therapy: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo main temporaneo per testare la visualizzazione
    public static void main(String[] args) {
        // Creazione di dati fittizi per testare la view

        // Creazione di alcuni utenti (proprietari)
        User owner1 = new User("CF001", "password123", "John", "Doe", "123456789");

        // Creazione di alcune specie
        Species dogSpecies = new Species(1, "Dog");

        // Creazione di alcune razze
        Breed breed1 = new Breed(1, "Labrador", dogSpecies);

        // Creazione di alcuni animali
        Animal animal1 = new Animal(1, "Rex", LocalDate.of(2015, 5, 20), owner1, breed1);

        Specialization specialization = new Specialization("pisellone");

        // Creazione di alcuni veterinari
        Vet vet1 = new Vet("CFV001", "vetpassword1", "Dr.", "Jones", "111222333", specialization);

        // Creazione di alcuni stati degli appuntamenti
        AppointmentState state1 = new AppointmentState(1, "Completed");

        // Creazione di alcuni appuntamenti
        Appointment appointment = new Appointment(
                1,
                animal1,
                vet1,
                LocalDateTime.of(2024, 8, 19, 10, 30),
                10,
                state1,
                "Report 1".getBytes());

        TherapyListController therapyListController = new TherapyListController(new ArrayList<Therapy>());

        new TherapyCreationView(appointment, therapyListController);
    }
}

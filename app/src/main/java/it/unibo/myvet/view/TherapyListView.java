package it.unibo.myvet.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import it.unibo.myvet.controller.TherapyListController;
import it.unibo.myvet.dao.TherapyDAO;
import it.unibo.myvet.model.Animal;
import it.unibo.myvet.model.Appointment;
import it.unibo.myvet.model.AppointmentState;
import it.unibo.myvet.model.Breed;
import it.unibo.myvet.model.Service;
import it.unibo.myvet.model.Sex;
import it.unibo.myvet.model.Specialization;
import it.unibo.myvet.model.Species;
import it.unibo.myvet.model.Therapy;
import it.unibo.myvet.model.User;
import it.unibo.myvet.model.Vet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TherapyListView extends JPanel {

        private JTable therapyTable;
        private DefaultTableModel tableModel;

        TherapyDAO therapyDAO = new TherapyDAO();
        private TherapyListController therapyListController;
        private List<Therapy> therapies;
        private Appointment appointment;

        public TherapyListView(Appointment appointment, TherapyListController therapyListController) {

                this.therapyListController = therapyListController;
                this.therapies = this.therapyListController.getTherapies();
                this.appointment = appointment;

                this.createView();

        }

        private void createView() {
                setLayout(new BorderLayout());

                // Nomi delle colonne per la tabella
                String[] columnNames = { "Therapy ID", "Appointment ID", "Therapy Name", "Description", "Start Date",
                                "End Date" };

                // Crea il modello della tabella e rendi le celle non modificabili
                tableModel = new DefaultTableModel(columnNames, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false; // Rendi tutte le celle non modificabili
                        }
                };

                therapyTable = new JTable(tableModel);

                // Formatter per le date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Popola la tabella con i dati delle terapie
                for (Therapy therapy : therapies) {
                        Object[] rowData = {
                                        therapy.getTherapyId(),
                                        therapy.getAppointmentId(),
                                        therapy.getName(),
                                        therapy.getDescription(),
                                        therapy.getStartDate().format(formatter),
                                        therapy.getEndDate().format(formatter)
                        };
                        tableModel.addRow(rowData);
                }

                // Aggiungi la tabella a un pannello di scorrimento
                JScrollPane scrollPane = new JScrollPane(therapyTable);
                add(scrollPane, BorderLayout.CENTER);

                setVisible(true);

                // Aggiungi un pannello con un pulsante in fondo
                JPanel buttonPanel = new JPanel();
                JButton createTherapyButton = new JButton("Create New Therapy");
                createTherapyButton.addActionListener(e -> openTherapyCreationFrame());
                buttonPanel.add(createTherapyButton);
                add(buttonPanel, BorderLayout.SOUTH);
        }

        // Metodo per aprire il frame TherapyCreation
        private void openTherapyCreationFrame() {
                // Puoi passare i parametri necessari al costruttore di TherapyCreation
                new TherapyCreationView(this.appointment, this.therapyListController).setVisible(true);
        }

        // Metodo main temporaneo per testare la visualizzazione
        public static void main(String[] args) {
                JFrame frame = new JFrame("Therapies Test");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(900, 400);

                // Creazione di dati fittizi per testare la view

                Species species = new Species("Cane");
                Breed breed = new Breed("Labrador", species);
                User user = new User("ciao", "ciao", "ciao", "ciao", "ciao");

                Animal animal1 = new Animal(
                                "Ciaone",
                                LocalDateTime.of(2020, 1, 1, 0, 0).toLocalDate(),
                                Sex.M,
                                user,
                                breed);

                Specialization specialization = new Specialization("pisellone");

                // Creazione di un veterinario
                Vet vet = new Vet("CFV001", "vetpassword1", "Dr.", "Jones", "111222333", 1, specialization);

                // Creazione dello stato dell'appuntamento
                AppointmentState state = new AppointmentState(1, "Completed");

                // Creazione di un esempio di BLOB immagine
                byte[] imageBytes = loadSampleImage(); // Funzione per caricare un'immagine di esempio come byte
                                                       // array

                Service service = new Service("General Checkup");

                // Creazione di un appuntamento
                Appointment appointment = new Appointment(
                                1,
                                animal1,
                                vet,
                                LocalDateTime.of(2024, 8, 19, 10, 30),
                                service,
                                10,
                                state,
                                imageBytes);

                // Esempio di lista di terapie
                List<Therapy> therapies = List.of(
                                new Therapy(
                                                101,
                                                "Therapy Name 1",
                                                "Therapy Description 1",
                                                LocalDate.of(2024, 1, 1),
                                                LocalDate.of(2024, 1, 15)),
                                new Therapy(
                                                101,
                                                "Therapy Name 1",
                                                "Therapy Description 1",
                                                LocalDate.of(2024, 1, 1),
                                                LocalDate.of(2024, 1, 15)));

                // Visualizzazione della finestra con la lista di terapie

                TherapyListController therapyListController = new TherapyListController(therapies);

                frame.add(new TherapyListView(appointment, therapyListController));

                frame.setVisible(true);
        }

        private static byte[] loadSampleImage() {
                try {
                        BufferedImage bufferedImage = ImageIO.read(new File(
                                        "/Users/ligmaballz/Desktop/uni-projects/db-2024/app/src/main/java/it/unibo/myvet/assets/animal.png"));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage, "png", baos);
                        return baos.toByteArray();
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
        }

}

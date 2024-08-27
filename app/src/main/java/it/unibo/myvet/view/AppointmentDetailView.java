package it.unibo.myvet.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.controller.TherapyListController;
import it.unibo.myvet.dao.AppointmentDAO;
import it.unibo.myvet.dao.AppointmentStateDAO;
import it.unibo.myvet.dao.TherapyDAO;
import it.unibo.myvet.model.*;

public class AppointmentDetailView extends JFrame {

    private List<Therapy> therapies = new ArrayList<>();
    private TherapyListController therapyListController;
    private Appointment appointment;
    private JTextField durationField;
    private JPanel imagePanel;

    public AppointmentDetailView(Appointment appointment) {
        this.therapies = new TherapyDAO().findByAppointmentId(appointment.getAppointmentId());
        this.therapyListController = new TherapyListController(this.therapies);
        this.appointment = appointment;

        this.createView();
    }

    private void createView() {
        setTitle("Appointment Details");
        setSize(800, 1000); // Dimensioni aumentate per dare più spazio all'immagine
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Layout a bordi per maggiore flessibilità

        // Formatter per la data e ora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Pannello per i dettagli dell'appuntamento
        JPanel detailsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        detailsPanel.add(new JLabel("Appointment ID:"));
        detailsPanel.add(new JLabel(String.valueOf(appointment.getAppointmentId())));

        detailsPanel.add(new JLabel("Animal Name:"));
        detailsPanel.add(new JLabel(appointment.getAnimal().getName()));

        detailsPanel.add(new JLabel("Owner Name:"));
        detailsPanel.add(new JLabel(appointment.getAnimal().getOwner().getFirstName() + " "
                + appointment.getAnimal().getOwner().getLastName()));

        detailsPanel.add(new JLabel("Vet Name:"));
        detailsPanel.add(new JLabel(
                appointment.getVet().getFirstName() + " " + appointment.getVet().getLastName()));

        detailsPanel.add(new JLabel("Date & Time:"));
        detailsPanel.add(new JLabel(appointment.getDateTime().format(formatter)));

        detailsPanel.add(new JLabel("Status:"));
        detailsPanel.add(new JLabel(appointment.getStatus().getStateName()));

        detailsPanel.add(new JLabel("Duration (minutes):"));
        durationField = new JTextField(String.valueOf(appointment.getDuration()));
        detailsPanel.add(durationField);

        add(detailsPanel, BorderLayout.NORTH);

        // Verifica se lo stato dell'appuntamento non è 1
        if (appointment.getStatus().getStateId() != 1) {
            displayReportAndTherapies();
        }

        // Aggiungi pulsante "Salva" solo se lo stato è ancora 1
        if (appointment.getStatus().getStateId() == 1) {
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> saveAppointment());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(saveButton);
            add(buttonPanel, BorderLayout.SOUTH);
        } else {
            durationField.setEditable(false); // Rende il campo di durata non modificabile
        }

        setVisible(true);
    }

    private void displayReportAndTherapies() {
        // Renderizza l'immagine se presente
        imagePanel = new JPanel(new BorderLayout());
        if (appointment.getReport() != null) {
            renderReport();
        }

        JButton uploadButton = new JButton("Upload Report");
        uploadButton.addActionListener(e -> uploadReport());

        imagePanel.add(uploadButton, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.CENTER);

        // Aggiungi il pannello delle terapie
        JPanel therapyPanel = new JPanel(new BorderLayout());
        therapyPanel.setBorder(BorderFactory.createTitledBorder("Therapies"));
        TherapyListView therapyListView = new TherapyListView(appointment, this.therapyListController);
        therapyPanel.add(therapyListView, BorderLayout.CENTER);
        add(therapyPanel, BorderLayout.SOUTH);
    }

    private void renderReport() {
        try {
            imagePanel.removeAll();
            byte[] reportData = appointment.getReport();

            BufferedImage img = ImageIO.read(new ByteArrayInputStream(reportData));

            if (img != null) {
                JLabel imageLabel = new JLabel(new ImageIcon(img));
                imagePanel.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
            } else {
                imagePanel.add(new JLabel("Unsupported file format or no image available."), BorderLayout.CENTER);
            }

            revalidate();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
            imagePanel.add(new JLabel("Error loading report"), BorderLayout.CENTER);
        }
    }

    private void uploadReport() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String fileName = selectedFile.getName().toLowerCase();
                if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
                    JOptionPane.showMessageDialog(this, "Only PNG and JPG files are supported.", "Unsupported File",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                byte[] fileData = loadFileAsByteArray(selectedFile);
                appointment.setReport(fileData);

                // Salva il report nel database
                AppointmentDAO appointmentDAO = new AppointmentDAO();
                appointmentDAO.update(appointment);

                // Aggiorna la vista con il nuovo report
                renderReport();
                JOptionPane.showMessageDialog(this, "Report uploaded and saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error uploading report.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private byte[] loadFileAsByteArray(File file) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream is = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }

    private void saveAppointment() {
        try {
            int newDuration = Integer.parseInt(durationField.getText());
            appointment.setDuration(newDuration);

            // Aggiorna lo stato dell'appuntamento a 2
            AppointmentStateDAO appointmentStateDAO = new AppointmentStateDAO();
            AppointmentState newState = appointmentStateDAO.findById(2);
            appointment.setStatus(newState);

            // Usa AppointmentDAO per salvare l'aggiornamento
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            appointmentDAO.update(appointment);

            // Una volta salvato, rendi la durata non modificabile e visualizza report e
            // terapie
            durationField.setEditable(false);
            displayReportAndTherapies();

            JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid duration format!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving appointment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo main temporaneo per testare la visualizzazione
    public static void main(String[] args) {
        Species species = new Species("Cane");
        Breed breed = new Breed("Labrador", species);
        User user = new User("ciao", "ciao", "ciao", "ciao", "ciao");

        // Creazione di un animale
        Animal animal = new Animal(1, "Rex", LocalDate.of(2015, 5, 20), Sex.M, user, breed);

        Specialization specialization = new Specialization("pisellone");

        // Creazione di un veterinario
        Vet vet = new Vet("CFV001", "vetpassword1", "Dr.", "Jones", "111222333", 1, specialization);

        // Creazione dello stato dell'appuntamento
        AppointmentState state = new AppointmentState(1, "Completed");

        // Creazione di un esempio di BLOB immagine
        byte[] imageBytes = loadSampleImage(); // Funzione per caricare un'immagine di esempio come byte array

        Service service = new Service("General Checkup");

        // Creazione di un appuntamento
        Appointment appointment = new Appointment(
                1,
                animal,
                vet,
                LocalDateTime.of(2024, 8, 19, 10, 30),
                service,
                30,
                state,
                imageBytes);

        new AppointmentDetailView(appointment);
    }

    // Funzione di esempio per caricare un'immagine come byte array
    private static byte[] loadSampleImage() {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(
                    "/path/to/your/image.png"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

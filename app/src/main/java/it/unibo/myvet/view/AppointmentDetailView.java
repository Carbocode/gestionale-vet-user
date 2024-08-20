package it.unibo.myvet.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import it.unibo.myvet.controller.TherapyListController;
import it.unibo.myvet.dao.TherapyDAO;
import it.unibo.myvet.model.*;

public class AppointmentDetailView extends JFrame {

    private List<Therapy> therapies = new ArrayList<>();
    private TherapyListController therapyListController;
    private Appointment appointment;

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
        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 10, 10));
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

        add(detailsPanel, BorderLayout.NORTH);

        // Renderizza l'immagine se presente
        if (appointment.getReport() != null) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(appointment.getReport()));
                if (img != null) {
                    // Crea un pannello per l'immagine
                    JPanel imagePanel = new JPanel(new BorderLayout());
                    JLabel imageLabel = new JLabel();
                    imageLabel.setHorizontalAlignment(JLabel.CENTER);

                    // Calcola il dimensionamento dell'immagine
                    int imageWidth = img.getWidth();
                    int imageHeight = img.getHeight();
                    double imageAspect = (double) imageWidth / imageHeight;

                    int panelWidth = 600;
                    int panelHeight = 400;
                    double panelAspect = (double) panelWidth / panelHeight;

                    int scaledWidth;
                    int scaledHeight;
                    if (imageAspect > panelAspect) {
                        scaledWidth = panelWidth;
                        scaledHeight = (int) (panelWidth / imageAspect);
                    } else {
                        scaledWidth = (int) (panelHeight * imageAspect);
                        scaledHeight = panelHeight;
                    }

                    Image scaledImage = img.getScaledInstance(scaledWidth, scaledHeight,
                            Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));

                    imagePanel.add(imageLabel, BorderLayout.CENTER);
                    add(imagePanel, BorderLayout.CENTER);
                } else {
                    add(new JLabel("No Image Available"), BorderLayout.CENTER);
                }
            } catch (IOException e) {
                e.printStackTrace();
                add(new JLabel("Error Loading Image"), BorderLayout.CENTER);
            }
        } else {
            add(new JLabel("No Report"), BorderLayout.CENTER);
        }

        // Aggiungi il pannello delle terapie
        JPanel therapyPanel = new JPanel(new BorderLayout());
        therapyPanel.setBorder(BorderFactory.createTitledBorder("Therapies"));
        TherapyListView therapyListView = new TherapyListView(appointment, this.therapyListController);
        therapyPanel.add(therapyListView, BorderLayout.CENTER);
        add(therapyPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Metodo main temporaneo per testare la visualizzazione
    public static void main(String[] args) {
        Species species = new Species("Cane");
        Breed breed = new Breed("Labrador", species);
        User user = new User("ciao", "ciao", "ciao", "ciao", "ciao");

        // Creazione di un animale
        Animal animal = new Animal(1, "Rex", LocalDate.of(2015, 5, 20), user, breed);

        // Creazione di un veterinario
        Vet vet = new Vet("CFV001", "vetpassword1", "Dr.", "Jones", "111222333", 1);

        // Creazione dello stato dell'appuntamento
        AppointmentState state = new AppointmentState(1, "Completed");

        // Creazione di un esempio di BLOB immagine
        byte[] imageBytes = loadSampleImage(); // Funzione per caricare un'immagine di esempio come byte
                                               // array

        // Creazione di un appuntamento
        Appointment appointment = new Appointment(
                1,
                animal,
                vet,
                LocalDateTime.of(2024, 8, 19, 10, 30),
                1,
                state,
                imageBytes);

        new AppointmentDetailView(appointment);
    }

    // Funzione di esempio per caricare un'immagine come byte array
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

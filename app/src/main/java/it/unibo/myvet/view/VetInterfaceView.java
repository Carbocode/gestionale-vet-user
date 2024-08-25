package it.unibo.myvet.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import it.unibo.myvet.controller.AppointmentListController;
import it.unibo.myvet.dao.AppointmentDAO;
import it.unibo.myvet.model.*;

public class VetInterfaceView extends JFrame {
    private Vet vet;
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public VetInterfaceView(Vet vet) {
        this.vet = vet;

        // Configurazione del JFrame
        setTitle("Vet Interface");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        List<Appointment> appointments = appointmentDAO.findByVetId(this.vet.getVetId());

        // Pannello per Richieste di Appuntamento
        JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new BorderLayout());
        JLabel requestsLabel = new JLabel("Richieste di Appuntamento", SwingConstants.CENTER);
        requestsLabel.setFont(new Font("Arial", Font.BOLD, 16));

        AppointmentListView requestsListView = new AppointmentListView(
                new AppointmentListController(appointments));

        requestsPanel.add(requestsLabel, BorderLayout.NORTH);
        requestsPanel.add(requestsListView, BorderLayout.CENTER);

        // Pannello per Prossimi Appuntamenti
        JPanel upcomingPanel = new JPanel();
        upcomingPanel.setLayout(new BorderLayout());
        JLabel upcomingLabel = new JLabel("Prossimi Appuntamenti", SwingConstants.CENTER);
        upcomingLabel.setFont(new Font("Arial", Font.BOLD, 16));

        AppointmentListView upcomingListView = new AppointmentListView(
                new AppointmentListController(appointments));
        upcomingPanel.add(upcomingLabel, BorderLayout.NORTH);
        upcomingPanel.add(upcomingListView, BorderLayout.CENTER);

        // Aggiungi entrambi i pannelli al JFrame
        add(requestsPanel);
        add(upcomingPanel);

        // Mostra la finestra
        setVisible(true);
    }

    // Metodo main per testare la GUI
    public static void main(String[] args) {
        // Creazione di dati fittizi per testare la view
        Specialization specialization = new Specialization("General");

        // Creazione di alcuni veterinari
        Vet vet1 = new Vet("PRSVCN02P21D704E", "vetpassword1", "Dr.", "Jones", "111222333", 2, specialization);

        // Creazione e visualizzazione della finestra principale
        new VetInterfaceView(vet1);
    }
}
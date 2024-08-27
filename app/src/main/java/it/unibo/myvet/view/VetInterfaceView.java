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
    private AppointmentListView requestsListView;
    private AppointmentListView upcomingListView;

    public VetInterfaceView(Vet vet) {
        this.vet = vet;

        // Configurazione del JFrame
        setTitle("Vet Interface");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        refreshAppointmentViews();

        // Aggiungi il pannello inferiore con i pulsanti per aprire VetServicesView e
        // VetShiftView
        addBottomPanel();

        // Mostra la finestra
        setVisible(true);
    }

    private void refreshAppointmentViews() {
        List<Appointment> upcomingAppointments = appointmentDAO.findByVetIdAndStateId(this.vet.getVetId(), 2);
        List<Appointment> pendingAppointments = appointmentDAO.findByVetIdAndStateId(this.vet.getVetId(), 1);

        // Pannello per Richieste di Appuntamento
        JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new BorderLayout());
        JLabel requestsLabel = new JLabel("Richieste di Appuntamento", SwingConstants.CENTER);
        requestsLabel.setFont(new Font("Arial", Font.BOLD, 16));

        requestsListView = new AppointmentListView(new AppointmentListController(pendingAppointments));

        requestsPanel.add(requestsLabel, BorderLayout.NORTH);
        requestsPanel.add(requestsListView, BorderLayout.CENTER);

        // Pannello per Prossimi Appuntamenti
        JPanel upcomingPanel = new JPanel();
        upcomingPanel.setLayout(new BorderLayout());
        JLabel upcomingLabel = new JLabel("Prossimi Appuntamenti", SwingConstants.CENTER);
        upcomingLabel.setFont(new Font("Arial", Font.BOLD, 16));

        upcomingListView = new AppointmentListView(new AppointmentListController(upcomingAppointments));
        upcomingPanel.add(upcomingLabel, BorderLayout.NORTH);
        upcomingPanel.add(upcomingListView, BorderLayout.CENTER);

        // Aggiungi i pannelli delle richieste e degli appuntamenti al pannello
        // principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(requestsPanel);
        mainPanel.add(upcomingPanel);

        // Rimuove il vecchio contenuto e aggiunge quello nuovo
        getContentPane().removeAll();
        add(mainPanel, BorderLayout.CENTER);
        addBottomPanel(); // Riaggiunge il pannello inferiore dopo il refresh
        revalidate();
        repaint();
    }

    private void addBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Pulsante per aprire VetServicesView
        JButton openVetServicesButton = new JButton("Apri Vet Services");
        openVetServicesButton.addActionListener(e -> {
            // Apri la finestra VetServicesView
            new VetServicesView(vet);
        });
        bottomPanel.add(openVetServicesButton);

        // Pulsante per aprire VetShiftView
        JButton openVetShiftButton = new JButton("Apri Vet Shift");
        openVetShiftButton.addActionListener(e -> {
            // Apri la finestra VetShiftView
            new VetShiftView(vet);
        });
        bottomPanel.add(openVetShiftButton);

        // Pulsante per refreshare le view
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshAppointmentViews());
        bottomPanel.add(refreshButton);

        // Aggiungi il pannello inferiore al frame
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Metodo main per testare la GUI
    public static void main(String[] args) {
        // Creazione di dati fittizi per testare la view
        Specialization specialization = new Specialization("General");

        // Creazione di un veterinario
        Vet vet1 = new Vet("PRSVCN02P21D704E", "vetpassword1", "Dr.", "Jones", "111222333", 2, specialization);

        // Creazione e visualizzazione della finestra principale
        new VetInterfaceView(vet1);
    }
}

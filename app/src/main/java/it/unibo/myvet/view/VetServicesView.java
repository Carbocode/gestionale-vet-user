package it.unibo.myvet.view;

import it.unibo.myvet.dao.ServiceDAO;
import it.unibo.myvet.dao.VetServiceDAO;
import it.unibo.myvet.model.Service;
import it.unibo.myvet.model.Specialization;
import it.unibo.myvet.model.Vet;
import it.unibo.myvet.model.VetService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VetServicesView extends JFrame {

    private Vet vet;
    private VetServiceDAO vetServiceDAO = new VetServiceDAO();
    private ServiceDAO serviceDAO = new ServiceDAO();
    private JPanel servicesPanel;
    private GridBagConstraints gbc;

    public VetServicesView(Vet vet) {
        this.vet = vet;

        setTitle("Servizi del Veterinario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pannello principale per la lista dei servizi (scrollabile)
        servicesPanel = new JPanel();
        servicesPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margini tra i componenti
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Recupera tutti i VetService per il veterinario specificato
        List<VetService> vetServices = vetServiceDAO.findByVetId(vet.getVetId());

        // Aggiunge i servizi esistenti al pannello
        for (int i = 0; i < vetServices.size(); i++) {
            addVetServiceRow(vetServices.get(i), i);
        }

        // Aggiungi il pannello dei servizi a un JScrollPane e ancoralo alla parte alta
        JScrollPane scrollPane = new JScrollPane(servicesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Aggiungi la parte inferiore con il JComboBox e il pulsante "+"
        addAddServicePanel();

        // Mostra la finestra
        setVisible(true);
    }

    private void addVetServiceRow(VetService vetService, int row) {
        // Nome del servizio
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.5;
        JLabel serviceNameLabel = new JLabel(vetService.getService().getName());
        servicesPanel.add(serviceNameLabel, gbc);

        // Campo di testo per la durata
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        JTextField durationField = new JTextField(String.valueOf(vetService.getDurationMinutes()));
        servicesPanel.add(durationField, gbc);

        // Pulsante di salvataggio
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        JButton saveButton = new JButton("Salva");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newDuration = Integer.parseInt(durationField.getText());
                    vetService.setDurationMinutes(newDuration);
                    vetServiceDAO.update(vetService);
                    JOptionPane.showMessageDialog(VetServicesView.this,
                            "Durata salvata con successo per " + vetService.getService().getName());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VetServicesView.this, "Durata non valida!", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        servicesPanel.add(saveButton, gbc);
    }

    private void addAddServicePanel() {
        // Pannello per aggiungere nuovi servizi
        JPanel addServicePanel = new JPanel();
        addServicePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Recupera tutti i servizi disponibili
        List<Service> allServices = serviceDAO.findAll();

        // Crea un JComboBox con tutti i servizi
        JComboBox<Service> serviceComboBox = new JComboBox<>(allServices.toArray(new Service[0]));
        addServicePanel.add(new JLabel("Aggiungi Servizio:"));
        addServicePanel.add(serviceComboBox);

        // Aggiungi il pulsante "+"
        JButton addButton = new JButton("+");
        addServicePanel.add(addButton);

        // Azione per aggiungere un nuovo servizio alla lista
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Service selectedService = (Service) serviceComboBox.getSelectedItem();
                if (selectedService != null) {
                    // Crea un nuovo VetService con la durata di default 30 minuti
                    VetService newVetService = new VetService(vet.getVetId(), selectedService, 30);
                    vetServiceDAO.save(newVetService);

                    // Aggiungi il nuovo servizio alla UI
                    addVetServiceRow(newVetService, servicesPanel.getComponentCount() / 3);

                    // Rinfresca la UI
                    servicesPanel.revalidate();
                    servicesPanel.repaint();

                    JOptionPane.showMessageDialog(VetServicesView.this,
                            "Servizio aggiunto con successo: " + selectedService.getName());
                }
            }
        });

        // Aggiungi il pannello di aggiunta alla parte inferiore del frame
        add(addServicePanel, BorderLayout.SOUTH);
    }

    // Metodo main per testare la view
    public static void main(String[] args) {
        // Supponiamo che il veterinario abbia un ID 1
        Vet vet = new Vet("PRSVCN02P21D704E", "password", "Dr.", "House", "555123456", 2,
                new Specialization("Generale"));
        new VetServicesView(vet);
    }
}
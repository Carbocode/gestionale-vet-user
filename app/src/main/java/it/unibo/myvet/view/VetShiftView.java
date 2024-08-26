package it.unibo.myvet.view;

import it.unibo.myvet.dao.ShiftDAO;
import it.unibo.myvet.model.Shift;
import it.unibo.myvet.model.Specialization;
import it.unibo.myvet.model.Vet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class VetShiftView extends JFrame {

    private Vet vet;
    private ShiftDAO shiftDAO = new ShiftDAO();
    private Map<DayOfWeek, JTextField[]> shiftFields = new EnumMap<>(DayOfWeek.class);

    public VetShiftView(Vet vet) {
        this.vet = vet;

        // Configurazione del JFrame
        setTitle("Orari di Lavoro del Veterinario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pannello principale per la gestione degli orari
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 4, 10, 10)); // 7 righe (giorni) x 4 colonne

        // Carica gli orari esistenti dal database
        List<Shift> shifts = shiftDAO.findByVetId(vet.getVetId());

        // Crea i campi per ogni giorno della settimana
        for (DayOfWeek day : DayOfWeek.values()) {
            // Label del giorno
            JLabel dayLabel = new JLabel(day.name(), SwingConstants.CENTER);
            mainPanel.add(dayLabel);

            // Label per il formato dell'ora di inizio
            JLabel startTimeLabel = new JLabel("Inizio (HH:mm):", SwingConstants.RIGHT);
            mainPanel.add(startTimeLabel);

            // Campo di testo per l'ora di inizio
            JTextField startTimeField = new JTextField();

            // Se l'orario esiste già, popola i campi con i valori esistenti
            for (Shift shift : shifts) {
                if (shift.getDay() == day) {
                    startTimeField.setText(shift.getStartTime().toString());
                    break;
                }
            }

            // Aggiungi il campo di input al pannello
            mainPanel.add(startTimeField);

            // Label per il formato dell'ora di fine
            JLabel endTimeLabel = new JLabel("Fine (HH:mm):", SwingConstants.RIGHT);
            mainPanel.add(endTimeLabel);

            // Campo di testo per l'ora di fine
            JTextField endTimeField = new JTextField();

            // Se l'orario esiste già, popola i campi con i valori esistenti
            for (Shift shift : shifts) {
                if (shift.getDay() == day) {
                    endTimeField.setText(shift.getEndTime().toString());
                    break;
                }
            }

            // Aggiungi il campo di input al pannello
            mainPanel.add(endTimeField);

            // Aggiungi i campi al map per un facile accesso
            shiftFields.put(day, new JTextField[] { startTimeField, endTimeField });
        }

        // Pannello per i pulsanti di salvataggio
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton saveButton = new JButton("Salva Orari");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveShifts();
            }
        });
        buttonPanel.add(saveButton);

        // Aggiungi i pannelli al frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Mostra la finestra
        setVisible(true);
    }

    private void saveShifts() {
        for (DayOfWeek day : DayOfWeek.values()) {
            JTextField[] fields = shiftFields.get(day);
            String startTimeText = fields[0].getText();
            String endTimeText = fields[1].getText();

            if (!startTimeText.isEmpty() && !endTimeText.isEmpty()) {
                LocalTime startTime = LocalTime.parse(startTimeText);
                LocalTime endTime = LocalTime.parse(endTimeText);
                Shift shift = new Shift(day, vet.getVetId(), startTime, endTime);

                // Controlla se esiste già un orario per questo giorno
                Shift existingShift = shiftDAO.findById(vet.getVetId(), day);
                if (existingShift != null) {
                    // Aggiorna l'orario esistente
                    shiftDAO.update(shift);
                } else {
                    // Salva un nuovo orario
                    shiftDAO.save(shift);
                }
            } else {
                // Se uno dei campi è vuoto, cancella l'orario per quel giorno (se esiste)
                shiftDAO.delete(vet.getVetId(), day);
            }
        }

        JOptionPane.showMessageDialog(this, "Orari di lavoro salvati con successo.");
    }

    // Metodo main per testare la view
    public static void main(String[] args) {
        // Creazione di dati fittizi per testare la view
        Vet vet = new Vet("PRSVCN02P21D704E", "password", "Dr.", "House", "555123456", 2,
                new Specialization("Generale"));
        new VetShiftView(vet);
    }
}
package it.unibo.myvet.controller;

import it.unibo.myvet.dao.TherapyDAO;
import it.unibo.myvet.model.Appointment;
import it.unibo.myvet.model.Therapy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TherapyListController {

    private TherapyDAO therapyDAO;
    private List<Therapy> therapies;

    public TherapyListController(List<Therapy> therapies) {
        this.therapyDAO = new TherapyDAO();
        this.therapies = therapies;
    }

    public TherapyListController() {
        this(new ArrayList<>());
    }

    public List<Therapy> getTherapies() {
        return therapies;
    }

    public void createTherapy(Appointment appointment, String name, String description, LocalDate startDate,
            LocalDate endDate) {
        // Crea un oggetto Therapy
        Therapy therapy = new Therapy(appointment.getAppointmentId(), name, description, startDate, endDate);

        // Salva la terapia nel database
        therapyDAO.save(therapy);

        // Aggiungi la terapia alla lista locale
        this.therapies.add(therapy);

        // Eventuali altre operazioni (es. notifiche, aggiornamenti, ecc.)
        System.out.println("Therapy created: " + therapy);
    }
}

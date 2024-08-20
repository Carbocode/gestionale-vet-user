package it.unibo.myvet.controller;

import it.unibo.myvet.dao.AppointmentDAO;
import it.unibo.myvet.model.Animal;
import it.unibo.myvet.model.Appointment;
import it.unibo.myvet.model.AppointmentState;
import it.unibo.myvet.model.Vet;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentListController {

    private AppointmentDAO appointmentDAO;
    private List<Appointment> appointments;

    public AppointmentListController(List<Appointment> appointments) {
        this.appointmentDAO = new AppointmentDAO();
        this.appointments = appointments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void createAppointment(Animal animal, Vet vet, LocalDateTime dateTime, byte[] report,
            AppointmentState status) {
        // Crea un oggetto Therapy
        Appointment appointment = new Appointment(animal, vet, dateTime, 10, status, report);

        // Salva la terapia nel database
        appointmentDAO.save(appointment);

        // Aggiungi la terapia alla lista locale
        this.appointments.add(appointment);

        // Eventuali altre operazioni (es. notifiche, aggiornamenti, ecc.)
        System.out.println("Appointment created: " + appointment);
    }
}

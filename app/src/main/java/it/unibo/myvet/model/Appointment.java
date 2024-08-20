package it.unibo.myvet.model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private Animal animal;
    private Vet vet;
    private LocalDateTime dateTime;
    private byte[] report;
    private AppointmentState status;

    public Appointment(Animal animal, Vet vet, LocalDateTime dateTime, AppointmentState status) {
        this.animal = animal;
        this.vet = vet;
        this.dateTime = dateTime;
        this.status = status;
    }

    // Costruttore senza ID dell'appuntamento (per nuovi oggetti)
    public Appointment(Animal animal, Vet vet, LocalDateTime dateTime, byte[] report, AppointmentState status) {
        this(animal, vet, dateTime, status);
        this.report = report;
    }

    // Costruttore con ID dell'appuntamento (per oggetti esistenti)
    public Appointment(int appointmentId, Animal animal, Vet vet, LocalDateTime dateTime, byte[] report,
            AppointmentState status) {
        this(animal, vet, dateTime, report, status);
        this.appointmentId = appointmentId;

    }

    // Getter e Setter
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public byte[] getReport() {
        return report;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }

    public AppointmentState getStatus() {
        return status;
    }

    public void setStatus(AppointmentState status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", animal=" + animal.getName() +
                ", vet=" + vet.getFirstName() + " " + vet.getLastName() +
                ", dateTime=" + dateTime +
                ", report=" + (report != null ? "[binary data]" : "null") +
                ", status=" + status.getStateName() +
                '}';
    }
}

package it.unibo.myvet.model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private Animal animal;
    private Vet vet;
    private Service service; // Nuovo campo per il servizio
    private LocalDateTime dateTime;
    private byte[] report;
    private AppointmentState status;
    private int duration;

    // Costruttore senza ID, senza report, senza durata
    public Appointment(Animal animal, Vet vet, LocalDateTime dateTime, Service service, AppointmentState status) {
        this.animal = animal;
        this.vet = vet;
        this.service = service; // Inizializza il servizio
        this.dateTime = dateTime;
        this.status = status;
        this.duration = 0;
    }

    // Costruttore senza ID, senza report
    public Appointment(Animal animal, Vet vet, LocalDateTime dateTime, Service service, int duration,
            AppointmentState status) {
        this(animal, vet, dateTime, service, status);
        this.duration = duration;
    }

    // Costruttore senza ID, con report
    public Appointment(Animal animal, Vet vet, LocalDateTime dateTime, Service service, int duration,
            AppointmentState status, byte[] report) {
        this(animal, vet, dateTime, service, duration, status);
        this.report = report;
    }

    // Costruttore con ID, senza report
    public Appointment(int appointmentId, Animal animal, Vet vet, LocalDateTime dateTime, Service service, int duration,
            AppointmentState status) {
        this(animal, vet, dateTime, service, duration, status);
        this.appointmentId = appointmentId;
    }

    // Costruttore con ID e report
    public Appointment(int appointmentId, Animal animal, Vet vet, LocalDateTime dateTime, Service service, int duration,
            AppointmentState status, byte[] report) {
        this(appointmentId, animal, vet, dateTime, service, duration, status);
        this.report = report;
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

    public Service getService() { // Nuovo getter per il servizio
        return service;
    }

    public void setService(Service service) { // Nuovo setter per il servizio
        this.service = service;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", animal=" + animal.getName() +
                ", vet=" + vet.getFirstName() + " " + vet.getLastName() +
                ", service=" + service.getName() + // Aggiungi il nome del servizio al toString
                ", dateTime=" + dateTime +
                ", report=" + (report != null ? "[binary data]" : "null") +
                ", status=" + status.getStateName() +
                ", duration=" + duration + " minutes" +
                '}';
    }
}
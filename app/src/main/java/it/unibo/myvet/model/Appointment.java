package it.unibo.myvet.model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private int animalId;
    private int vetId;
    private LocalDateTime dateTime; // Cambiato da appointmentDate a dateTime
    private byte[] report; // Questo campo rappresenta il file del referto

    // Costruttore
    public Appointment(int appointmentId, int animalId, int vetId, LocalDateTime dateTime, byte[] report) {
        this.appointmentId = appointmentId;
        this.animalId = animalId;
        this.vetId = vetId;
        this.dateTime = dateTime;
        this.report = report;
    }

    // Getter e Setter
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
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

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", animalId=" + animalId +
                ", vetId=" + vetId +
                ", dateTime=" + dateTime +
                ", report=" + (report != null ? "[binary data]" : "null") +
                '}';
    }
}

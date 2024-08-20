package it.unibo.myvet.model;

import java.time.LocalDateTime;

public class Therapy {
    private int therapyId;
    private int appointmentId; // Chiave esterna che fa riferimento all'appuntamento
    private String name; // Nome della terapia
    private String description; // Descrizione della terapia
    private LocalDateTime startDate; // Data di inizio della terapia
    private LocalDateTime endDate; // Data di fine della terapia

    // Costruttore senza ID (per nuovi oggetti)
    public Therapy(int appointmentId, String name, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.appointmentId = appointmentId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Costruttore con ID (per oggetti esistenti)
    public Therapy(int therapyId, int appointmentId, String name, String description, LocalDateTime startDate,
            LocalDateTime endDate) {
        this.therapyId = therapyId;
        this.appointmentId = appointmentId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter e Setter
    public int getTherapyId() {
        return therapyId;
    }

    public void setTherapyId(int therapyId) {
        this.therapyId = therapyId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Therapy{" +
                "therapyId=" + therapyId +
                ", appointmentId=" + appointmentId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

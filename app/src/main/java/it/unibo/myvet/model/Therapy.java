package it.unibo.myvet.model;

import java.time.LocalDateTime;

public class Therapy {
    private int therapyId;
    private int animalId;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Costruttore
    public Therapy(int animalId, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.animalId = animalId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Costruttore
    public Therapy(int therapyId, int animalId, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.therapyId = therapyId;
        this.animalId = animalId;
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

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
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
                ", animalId=" + animalId +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

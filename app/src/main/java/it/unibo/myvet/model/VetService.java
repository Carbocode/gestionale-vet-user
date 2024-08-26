package it.unibo.myvet.model;

public class VetService {
    private int vetId; // Reference to Vet
    private Service service; // Reference to Service object
    private int durationMinutes; // Duration in minutes

    // Constructor
    public VetService(int vetId, Service service, int durationMinutes) {
        this.vetId = vetId;
        this.service = service;
        this.durationMinutes = durationMinutes;
    }

    // Getters and Setters
    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return "VetService{" +
                "vetId=" + vetId +
                ", service=" + service +
                ", durationMinutes=" + durationMinutes +
                '}';
    }
}
package it.unibo.myvet.model;

public class VetService {
    private int vetId; // Reference to Vet
    private int serviceId; // Reference to Service
    private int durationMinutes; // Duration in minutes

    // Constructor
    public VetService(int vetId, int serviceId, int durationMinutes) {
        this.vetId = vetId;
        this.serviceId = serviceId;
        this.durationMinutes = durationMinutes;
    }

    // Getters and Setters
    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
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
                ", serviceId=" + serviceId +
                ", durationMinutes=" + durationMinutes +
                '}';
    }
}

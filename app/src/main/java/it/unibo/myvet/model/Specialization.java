package it.unibo.myvet.model;

public class Specialization {
    private int specializationId;
    private String name;

    // Constructor
    public Specialization(String name) {
        this.name = name;
    }

    // Constructor
    public Specialization(int specializationId, String name) {
        this(name);
        this.specializationId = specializationId;
    }

    // Getters and Setters
    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "specializationId=" + specializationId +
                ", name='" + name + '\'' +
                '}';
    }
}

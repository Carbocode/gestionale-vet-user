package it.unibo.myvet.model;

public class Vet extends Account {
    private int vetId;
    private Specialization specialization;

    // Costruttore
    public Vet(String cf, String password, String firstName, String lastName, String phoneNumber,
            Specialization specialization) {
        super(cf, password, firstName, lastName, phoneNumber);
        this.specialization = specialization;
    }

    // Costruttore
    public Vet(String cf, String password, String firstName, String lastName, String phoneNumber, int vetId,
            Specialization specialization) {
        this(cf, password, firstName, lastName, phoneNumber, specialization);
        this.vetId = vetId;
    }

    // Getter e Setter per vetId
    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
    }

    // Getter e Setter per specialization
    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Vet{" +
                "vetId=" + vetId +
                ", cf='" + getCf() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", specialization='" + getSpecialization().toString() + '\'' +
                '}';
    }
}

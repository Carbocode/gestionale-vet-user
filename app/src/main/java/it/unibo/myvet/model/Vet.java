package it.unibo.myvet.model;

public class Vet extends Account {
    private int vetId;

    // Costruttore
    public Vet(String cf, String password, String firstName, String lastName, String phoneNumber) {
        super(cf, password, firstName, lastName, phoneNumber);
    }

    // Costruttore
    public Vet(String cf, String password, String firstName, String lastName, String phoneNumber, int vetId) {
        super(cf, password, firstName, lastName, phoneNumber);
        this.vetId = vetId;
    }

    // Getter e Setter per vetId
    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
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
                '}';
    }
}

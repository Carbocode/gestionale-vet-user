package it.unibo.myvet.model;

public class User extends Account {
    private int userId;

    // Costruttore
    public User(String cf, String password, String firstName, String lastName, String phoneNumber) {
        super(cf, password, firstName, lastName, phoneNumber);
    }

    // Costruttore
    public User(String cf, String password, String firstName, String lastName, String phoneNumber, int userId) {
        this(cf, password, firstName, lastName, phoneNumber);
        this.userId = userId;
    }

    // Getter e Setter per userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", cf='" + getCf() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                '}';
    }
}

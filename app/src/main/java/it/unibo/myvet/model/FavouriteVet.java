package it.unibo.myvet.model;

public class FavouriteVet {
    private int userId;
    private int vetId;

    // Costruttore
    public FavouriteVet(int userId, int vetId) {
        this.userId = userId;
        this.vetId = vetId;
    }

    // Getter e Setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
    }

    @Override
    public String toString() {
        return "FavouriteVet{" +
                "userId=" + userId +
                ", vetId=" + vetId +
                '}';
    }
}

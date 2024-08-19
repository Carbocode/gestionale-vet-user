package it.unibo.myvet.model;

public class Breed {
    private int breedId;
    private String breedName;
    private int speciesId; // Chiave esterna che fa riferimento a Species

    // Costruttore
    public Breed(String breedName, int speciesId) {
        this.breedName = breedName;
        this.speciesId = speciesId;
    }

    // Costruttore
    public Breed(int breedId, String breedName, int speciesId) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.speciesId = speciesId;
    }

    // Getter e Setter
    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(int speciesId) {
        this.speciesId = speciesId;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "breedId=" + breedId +
                ", breedName='" + breedName + '\'' +
                ", speciesId=" + speciesId +
                '}';
    }
}

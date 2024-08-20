package it.unibo.myvet.model;

public class Breed {
    private int breedId;
    private String breedName;
    private Species species; // L'oggetto Species come riferimento alla specie

    // Costruttore senza ID della razza (per nuovi oggetti)
    public Breed(String breedName, Species species) {
        this.breedName = breedName;
        this.species = species;
    }

    // Costruttore con ID della razza (per oggetti esistenti)
    public Breed(int breedId, String breedName, Species species) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.species = species;
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

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "breedId=" + breedId +
                ", breedName='" + breedName + '\'' +
                ", species=" + species.getSpeciesName() +
                '}';
    }
}

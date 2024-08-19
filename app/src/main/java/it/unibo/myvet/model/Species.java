package it.unibo.myvet.model;

public class Species {
    private int speciesId;
    private String speciesName;

    // Costruttore
    public Species(int speciesId, String speciesName) {
        this.speciesId = speciesId;
        this.speciesName = speciesName;
    }

    // Getter e Setter
    public int getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(int speciesId) {
        this.speciesId = speciesId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    @Override
    public String toString() {
        return "Species{" +
                "speciesId=" + speciesId +
                ", speciesName='" + speciesName + '\'' +
                '}';
    }
}

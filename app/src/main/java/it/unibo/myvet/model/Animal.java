package it.unibo.myvet.model;

public class Animal {
    private int animalId;
    private String name;
    private String species;
    private String breed;
    private int age;
    private int ownerId; // Reference to User (owner)

    // Costruttore
    public Animal(int animalId, String name, String species, String breed, int age, int ownerId) {
        this.animalId = animalId;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.ownerId = ownerId;
    }

    // Getter e Setter
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", ownerId=" + ownerId +
                '}';
    }
}

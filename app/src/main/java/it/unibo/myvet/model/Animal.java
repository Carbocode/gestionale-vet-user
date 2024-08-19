package it.unibo.myvet.model;

import java.time.LocalDate;

public class Animal {
    private int animalId;
    private String name;
    private String breed;
    private LocalDate birthDate; // Data di nascita dell'animale
    private int ownerId; // Riferimento all'utente proprietario

    // Costruttore
    public Animal(String name, String breed, LocalDate birthDate, int ownerId) {
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
        this.ownerId = ownerId;
    }

    // Costruttore
    public Animal(int animalId, String name, String breed, LocalDate birthDate, int ownerId) {
        this.animalId = animalId;
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
                ", breed='" + breed + '\'' +
                ", birthDate=" + birthDate +
                ", ownerId=" + ownerId +
                '}';
    }
}

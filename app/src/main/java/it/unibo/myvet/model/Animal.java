package it.unibo.myvet.model;

import java.time.LocalDate;

public class Animal {
    private int animalId;
    private String name;
    private LocalDate birthDate;
    private Sex sex;
    private User owner; // L'oggetto User come riferimento al proprietario
    private Breed breed; // L'oggetto Breed come riferimento alla razza

    // Costruttore senza ID dell'animale (per nuovi oggetti)
    public Animal(String name, LocalDate birthDate, Sex sex, User owner, Breed breed) {
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.owner = owner;
        this.breed = breed;

    }

    // Costruttore con ID dell'animale (per oggetti esistenti)
    public Animal(int animalId, String name, LocalDate birthDate, Sex sex, User owner, Breed breed) {
        this(name, birthDate, sex, owner, breed);
        this.animalId = animalId;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", sex=" + sex.toString() +
                ", owner=" + owner.getFirstName() + " " + owner.getLastName() +
                ", breed=" + breed.getBreedName() +
                '}';
    }
}

package it.unibo.myvet.model;

public enum Sex {
    FEMALE('F'),
    MALE('M');

    char sex;

    Sex(char sex) {
        this.sex = sex;
    }

    public String toString() {
        return String.valueOf(sex);
    }
}

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

    public static Sex fromChar(char sexChar) {
        switch (sexChar) {
            case 'F':
                return Sex.FEMALE;
            case 'M':
                return Sex.MALE;
            default:
                throw new IllegalArgumentException("Carattere non valido: " + sexChar);
        }
    }
}

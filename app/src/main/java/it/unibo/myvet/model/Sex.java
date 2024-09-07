package it.unibo.myvet.model;

public enum Sex {
    F('F'),
    M('M');

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
                return Sex.F;
            case 'M':
                return Sex.M;
            default:
                throw new IllegalArgumentException("Carattere non valido: " + sexChar);
        }
    }
}

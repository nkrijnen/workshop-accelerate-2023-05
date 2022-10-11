package eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects;

import java.util.Objects;

public class LegoSetNumber {
    private final int number;

    public LegoSetNumber(int number) {
        if (number < 100 || number > 9999999) {
            throw new IllegalArgumentException("Lego set number must have 3 to 7 digits");
        }
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegoSetNumber that = (LegoSetNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}

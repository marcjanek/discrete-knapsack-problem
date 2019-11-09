package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.elka.pszt.knapsack.model.Item;

/**
 * The type Gen.
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@Setter
public class Gen extends Item implements Cloneable {
    /**
     * The Is present represents if gen is active in chromosome.
     */
    boolean isPresent = false;

    /**
     * Instantiates a new Gen.
     *
     * @param volume the volume
     * @param value  the value
     */
    public Gen(Long volume, Long value) {
        super(volume, value);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return isPresent ? "1" : "0";
    }

    /**
     * Negate is present.
     */
    void negateIsPresent() {
        isPresent = !isPresent;
    }
}

package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.elka.pszt.knapsack.model.Item;

@Getter
@EqualsAndHashCode(callSuper = false)
@Setter
public class Gen extends Item implements Cloneable {
    boolean isPresent = false;

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

    void negateIsPresent() {
        isPresent = !isPresent;
    }
}

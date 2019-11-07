package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.elka.pszt.knapsack.model.Item;

@Getter
@Setter
public class Gen extends Item implements Cloneable {
    boolean isPresent = false;

    public Gen(Long weight, Long value) {
        super(weight, value);
    }

    void negateIsPresent() {
        isPresent = !isPresent;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public String toString(){
        return isPresent ? "1":"0";
    }
}

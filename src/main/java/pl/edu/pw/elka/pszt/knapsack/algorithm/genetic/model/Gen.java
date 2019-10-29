package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.elka.pszt.knapsack.model.Item;

@Getter @Setter
public class Gen extends Item {
    Boolean isPresent = false;
    public Gen(Long weight, Long value) {
        super(weight, value);
    }
    void negateIsPresent(){
        isPresent = !isPresent;
    }
    @Override
    public Gen clone() throws CloneNotSupportedException {
        Item item = super.clone();
        Gen copy = new Gen(item.getWeight(), item.getValue());
        copy.isPresent = this.isPresent;
        return copy;
    }
}

package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Data;

@Data
public class Item {
    private final Long weight, value;

    @Override
    protected Item clone() throws CloneNotSupportedException {
        return new Item(this.weight, this.value);
    }
}
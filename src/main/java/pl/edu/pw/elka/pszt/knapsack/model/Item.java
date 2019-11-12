package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Data;

@Data
public class Item implements Cloneable {
    private final Long volume, value;
}
package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Data;

/**
 * The type Item. Contains item volume and value.
 */
@Data
public class Item implements Cloneable {
    private final Long volume, value;
}
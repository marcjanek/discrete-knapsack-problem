package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Knapsack objects. Contains knapsack capacity and items.
 */
@RequiredArgsConstructor
@Getter
public class KnapsackObjects {
    private final Long knapsackCapacity;
    private final List<Item> items = new ArrayList<>();

    public void add(Item item) {
        items.add(item);
    }
}
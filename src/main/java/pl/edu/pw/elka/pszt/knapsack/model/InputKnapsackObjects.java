package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class InputKnapsackObjects {
    private final Long knapsackCapacity;
    private final List<KnapsackObject> knapsackObjects = new ArrayList<>();

    public void add(KnapsackObject knapsackObject) {
        knapsackObjects.add(knapsackObject);
    }
}
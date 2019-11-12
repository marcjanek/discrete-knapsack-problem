package pl.edu.pw.elka.pszt.knapsack.model;

import java.util.List;
import java.util.Objects;

public class ValidateKnapsackObjects {
    public static boolean checkCapacity(KnapsackObjects knapsackObjects) {
        Long knapsackCapacity = knapsackObjects.getKnapsackCapacity();
        return Objects.nonNull(knapsackCapacity) && knapsackCapacity > 0;
    }

    public static boolean checkItems(KnapsackObjects knapsackObjects) {
        List<Item> items = knapsackObjects.getItems();
        if (Objects.isNull(items))
            return false;
        for (Item item : items) {
            if (Objects.isNull(item.getValue()) ||
                    Objects.isNull(item.getVolume()) ||
                    item.getVolume() < 0 ||
                    item.getValue() < 0)
                return false;
        }
        return true;
    }
}

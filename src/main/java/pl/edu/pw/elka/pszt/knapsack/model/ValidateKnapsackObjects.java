package pl.edu.pw.elka.pszt.knapsack.model;

import java.util.List;
import java.util.Objects;

/**
 * The type Validate knapsack objects.
 */
public class ValidateKnapsackObjects {
    /**
     * Check capacity.
     *
     * @param knapsackObjects the knapsack objects
     * @return true if knapsack capacity is non null and is positive number, else return false
     */
    public static boolean checkCapacity(KnapsackObjects knapsackObjects) {
        Long knapsackCapacity = knapsackObjects.getKnapsackCapacity();
        return Objects.nonNull(knapsackCapacity) && knapsackCapacity > 0;
    }

    /**
     * Check items.
     *
     * @param knapsackObjects the knapsack objects
     * @return true if knapsack items are non null and are positive numbers, else return false
     */
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

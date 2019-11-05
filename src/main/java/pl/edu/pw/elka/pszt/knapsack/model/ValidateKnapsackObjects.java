package pl.edu.pw.elka.pszt.knapsack.model;

import java.util.List;
import java.util.Objects;

public class ValidateKnapsackObjects {
    public static boolean checkCapacity(KnapsackObjects iko) {
        Long knapsackCapacity = iko.getKnapsackCapacity();
        return Objects.nonNull(knapsackCapacity) && knapsackCapacity > 0;
    }

    public static boolean checkItems(KnapsackObjects iko) {
        List<Item> items = iko.getItems();
        if (Objects.isNull(items))
            return false;
        for (Item item : items) {
            if (Objects.isNull(item.getValue()) ||
                    Objects.isNull(item.getWeight()) ||
                    item.getWeight() < 0 ||
                    item.getValue() < 0)
                return false;
        }
        return true;
    }
}

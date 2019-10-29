package pl.edu.pw.elka.pszt.knapsack.model;

import java.util.List;
import java.util.Objects;

public class ValidateInputKnapsackObjects {
    public static boolean checkCapacity(InputKnapsackObjects iko) {
        Long knapsackCapacity = iko.getKnapsackCapacity();
        return Objects.nonNull(knapsackCapacity) && knapsackCapacity < 0;
    }

    public static boolean checkItems(InputKnapsackObjects iko) {
        List<Item> items = iko.getItems();
        if (Objects.isNull(items))
            return false;
        for (Item item : items) {
            if (Objects.isNull(item.getValue()) ||
                    Objects.isNull(item.getWeight()) ||
                    item.getWeight() < 0 ||
                    item.getValue() < 0)
                return true;
        }
        return false;
    }
}

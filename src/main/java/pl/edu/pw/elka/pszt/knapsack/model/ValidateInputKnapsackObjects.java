package pl.edu.pw.elka.pszt.knapsack.model;

import java.util.List;
import java.util.Objects;

public class ValidateInputKnapsackObjects {
    public static boolean checkCapacity(InputKnapsackObjects iko) {
        Long knapsackCapacity = iko.getKnapsackCapacity();
        return Objects.nonNull(knapsackCapacity) && knapsackCapacity >= 0;
    }

    public static boolean checkItems(InputKnapsackObjects iko) {
        List<KnapsackObject> knapsackObjects = iko.getKnapsackObjects();
        if (Objects.isNull(knapsackObjects))
            return false;
        for (KnapsackObject knapsackObject : knapsackObjects) {
            if (Objects.isNull(knapsackObject.getValue()) ||
                    Objects.isNull(knapsackObject.getWeight()) ||
                    knapsackObject.getWeight() < 0 ||
                    knapsackObject.getValue() < 0)
                return false;
        }
        return true;
    }
}

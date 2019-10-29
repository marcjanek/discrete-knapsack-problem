package pl.edu.pw.elka.pszt.knapsack;

import lombok.AllArgsConstructor;
import pl.edu.pw.elka.pszt.knapsack.model.InputKnapsackObjects;
import pl.edu.pw.elka.pszt.knapsack.model.InputLoader;
import pl.edu.pw.elka.pszt.knapsack.model.ValidateInputKnapsackObjects;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class Knapsack {
    private final String inputPath;
    private final String outputPath;

    public void run() throws IOException {
        InputKnapsackObjects iko = loadInput();
        validate(iko);
        calculate(iko);
        //ToDo save output
    }

    private void validate(InputKnapsackObjects iko) throws IOException {
        if(ValidateInputKnapsackObjects.checkCapacity(iko))
            throw new IOException("Error in capacity. Capacity must be integer >= 0, but is: " + iko.getKnapsackCapacity());
        if(ValidateInputKnapsackObjects.checkItems(iko))
            throw new IOException("Error in items. Value and weight for each item must be integer. All items: " +
                    Arrays.toString(iko.getKnapsackObjects().toArray()));
    }

    private InputKnapsackObjects loadInput() throws IOException {
        return new InputLoader(inputPath).load();
    }

    private void calculate(InputKnapsackObjects iko) {
        //ToDo body;
    }
}
package pl.edu.pw.elka.pszt.knapsack;

import lombok.AllArgsConstructor;
import pl.edu.pw.elka.pszt.knapsack.model.InputKnapsackObjects;
import pl.edu.pw.elka.pszt.knapsack.model.InputLoader;

import java.io.IOException;

@AllArgsConstructor
public class Knapsack {
    private final String inputPath;
    private final String outputPath;

    public void run() throws IOException {
        InputKnapsackObjects iko = loadInput();
        calculate(iko);
        //ToDo save output
    }

    private InputKnapsackObjects loadInput() throws IOException {
        return new InputLoader(inputPath).load();
    }

    private void calculate(InputKnapsackObjects iko) {
        //ToDo body;
    }
}
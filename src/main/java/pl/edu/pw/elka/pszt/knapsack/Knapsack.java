package pl.edu.pw.elka.pszt.knapsack;

import lombok.AllArgsConstructor;
import pl.edu.pw.elka.pszt.knapsack.algorithm.Algorithm;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.Genetic;
import pl.edu.pw.elka.pszt.knapsack.model.KnapsackObjects;
import pl.edu.pw.elka.pszt.knapsack.model.InputLoader;
import pl.edu.pw.elka.pszt.knapsack.model.ValidateKnapsackObjects;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
public class Knapsack {
    private final String inputPath, outputPath;

    public void run() throws IOException, CloneNotSupportedException {
        KnapsackObjects iko = loadInput();
        validate(iko);
        String result = calculate(iko);
        saveOutput(result);
    }

    private KnapsackObjects loadInput() throws IOException {
        return new InputLoader(inputPath).load();
    }

    private void validate(KnapsackObjects iko) throws IOException {
        if (!ValidateKnapsackObjects.checkCapacity(iko))
            throw new IOException("Error in capacity. Capacity must be integer >= 0, but is: " + iko.getKnapsackCapacity());
        if (!ValidateKnapsackObjects.checkItems(iko))
            throw new IOException("Error in items. Value and weight for each item must be integer. All items: " +
                    Arrays.toString(iko.getItems().toArray()));
    }

    private String calculate(KnapsackObjects iko) throws CloneNotSupportedException {
        Algorithm algorithm = new Genetic(iko);
        return algorithm.calculate();
    }

    private void saveOutput(String string) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write(string);
        writer.close();
    }
}
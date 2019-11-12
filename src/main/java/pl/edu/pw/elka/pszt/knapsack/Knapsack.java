package pl.edu.pw.elka.pszt.knapsack;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.elka.pszt.knapsack.algorithm.Algorithm;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.Genetic;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Population;
import pl.edu.pw.elka.pszt.knapsack.model.*;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
public class Knapsack {
    private final String inputPath, outputPath;
    private String settingsPath;

    public void run() throws IOException, CloneNotSupportedException {
        KnapsackObjects knapsackObjects = loadInput();
        Settings settings = loadSettings(knapsackObjects);
        validate(knapsackObjects);
        calculate(knapsackObjects, settings);
    }

    private void calculate(KnapsackObjects knapsackObjects, Settings settings) throws CloneNotSupportedException, IOException {
        Algorithm algorithm = new Genetic(knapsackObjects, settings);
        String result = algorithm.calculate();
        saveOutput(result);
        if (settings.getGenerateChart()) {
            createChart(algorithm.getOldPopulations());
        }
    }

    private void createChart(List<Population> populations) {
        SwingUtilities.invokeLater(() -> {
            Chart ex = new Chart(populations);
            ex.setVisible(true);
        });
    }

    private Settings loadSettings(KnapsackObjects knapsackObjects) {
        Settings settings = new Settings(knapsackObjects.getItems().size());
        settings.initDataFromFile(settingsPath);
        return settings;
    }

    private KnapsackObjects loadInput() throws IOException {
        return new KnapsackDataLoader(inputPath).load();
    }

    private void validate(KnapsackObjects knapsackObjects) throws IOException {
        if (!ValidateKnapsackObjects.checkCapacity(knapsackObjects))
            throw new IOException("Error in capacity. Capacity must be integer >= 0, but is: " + knapsackObjects.getKnapsackCapacity());
        if (!ValidateKnapsackObjects.checkItems(knapsackObjects))
            throw new IOException("Error in items. Value and volume for each item must be integer. All items: " +
                    Arrays.toString(knapsackObjects.getItems().toArray()));
    }

    public void saveOutput(String string) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write(string);
        writer.close();
    }
}
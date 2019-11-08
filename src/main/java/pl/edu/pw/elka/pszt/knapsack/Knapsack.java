package pl.edu.pw.elka.pszt.knapsack;

import lombok.AllArgsConstructor;
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
public class Knapsack {
    private final String inputPath, outputPath, settingsPath;

    public void run() throws IOException, CloneNotSupportedException {
        KnapsackObjects iko = loadInput();
        Settings settings = loadSettings();
        settings.setInitialPopulation(
                settings.getInitialPopulation() == 0 ? iko.getItems().size() : settings.getInitialPopulation()
        );
        validate(iko);
        if (settings.getGenerateChart() == 0) {
            String result = calculate(iko, settings);
            saveOutput(result);
        } else {
            Algorithm algorithm = new Genetic(iko, settings);
            algorithm.calculate();
            createChart(algorithm.getOldPopulations());
        }
    }

    private void createChart(List<Population> populations) {
        SwingUtilities.invokeLater(() -> {
            Chart ex = new Chart(populations);
            ex.setVisible(true);
        });
    }

    private Settings loadSettings() {
        Settings settings = new Settings();
        settings.initDataFromFile(settingsPath);
        return settings;
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

    private String calculate(KnapsackObjects iko, Settings settings) throws CloneNotSupportedException {
        Algorithm algorithm = new Genetic(iko,settings);
        return algorithm.calculate();
    }

    private void saveOutput(String string) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write(string);
        writer.close();
    }
}
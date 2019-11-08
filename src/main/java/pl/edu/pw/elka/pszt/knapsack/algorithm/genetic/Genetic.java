package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.elka.pszt.knapsack.algorithm.Algorithm;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Chromosome;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Gen;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Population;
import pl.edu.pw.elka.pszt.knapsack.model.KnapsackObjects;
import pl.edu.pw.elka.pszt.knapsack.model.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class Genetic implements Algorithm {
    @Getter
    public  List<Population> oldPopulations;
    private final KnapsackObjects knapsackObjects;
    private final Settings settings;
    @Override
    public String calculate() throws CloneNotSupportedException {
        Population population = getInitPopulation((int)settings.getInitialPopulation());
        oldPopulations = new ArrayList<>();
        do{
            oldPopulations.add(population);
            population = population.cycle(knapsackObjects.getKnapsackCapacity().intValue(), (int) settings.getProbability());
        } while (population.getNumber() < settings.getIterations() ||
                population.dominatorPercentage() < settings.getDominatorPercentage());
        oldPopulations.add(population);
        return getResultString(oldPopulations);
    }

    private String getResultString(List<Population> populations) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < populations.size() - 1; i++) {
            text.append(populations.get(i).toString()).append("\n");
        }
        if (populations.size() > 0)
            text.append("result:")
                    .append("\n")
                    .append(populations.get(populations.size() - 1).toString())
                    .append("\n")
                    .append(populations.get(populations.size() - 1).bestFound())
                    .append("\n");
        return text.toString();
    }

    private Population getInitPopulation(int size) throws CloneNotSupportedException {
        Population population = new Population(0L);
        Chromosome chromosome = getInitChromosome();
        for (int i = 0; i < size; i++) {
            population.add((Chromosome) chromosome.clone());
        }
        randomizeGens(population);
        population.fixChromosomes(Math.toIntExact(knapsackObjects.getKnapsackCapacity()));
        return population;
    }
    private void randomizeGens(Population population){
        Random random = new Random(System.currentTimeMillis());
        population.getChromosomes().forEach(chromosome->
                chromosome.getGens().forEach(gen -> gen.setPresent(random.nextBoolean())));
    }
    private Chromosome getInitChromosome(){
        Chromosome chromosome = new Chromosome();
        knapsackObjects.getItems().forEach(e -> chromosome.add(new Gen(e.getVolume(), e.getValue())));
        return chromosome;
    }
}
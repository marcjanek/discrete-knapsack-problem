package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic;

import lombok.AllArgsConstructor;
import pl.edu.pw.elka.pszt.knapsack.algorithm.Algorithm;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Chromosome;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Gen;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Population;
import pl.edu.pw.elka.pszt.knapsack.model.KnapsackObjects;

import java.util.Random;

@AllArgsConstructor
public class Genetic implements Algorithm {
    private final KnapsackObjects iko;

    @Override
    public String calculate() throws CloneNotSupportedException {
        Population population = getInitPopulation(iko.getItems().size());// FIXME: 29.10.2019 change to setting
        fitness(population);
        do {
            population.cycle(iko.knapsackCapacity.intValue(), );
        } while (population.dominatorPercentage() >= 90L && population.getNumber() >= 5);// FIXME: 29.10.2019 change to setting
        return population.toString();
    }

    private void fitness(Population population) {
        population.getChromosomes()
                .stream()
                .filter(e -> e.weight() > iko.getKnapsackCapacity())
                .forEach(chromosome -> chromosome.fitness(iko.getKnapsackCapacity().intValue()));// FIXME: 29.10.2019 change to setting
    }

    private Population getInitPopulation(int size) throws CloneNotSupportedException {
        Population population = new Population(0L);
        Chromosome chromosome = getInitChromosome();
        for (int i = 0; i < size; i++) {
            population.add((Chromosome) chromosome.clone());
        }
        randomizeGens(population);
        return population;
    }

    private void randomizeGens(Population population) {
        Random random = new Random(System.currentTimeMillis());
        population.getChromosomes().forEach(chromosome ->
                chromosome.getGens().forEach(gen -> gen.setPresent(random.nextBoolean())));
    }

    private Chromosome getInitChromosome() {
        Chromosome chromosome = new Chromosome();
        iko.getItems().forEach(e -> chromosome.add(new Gen(e.getWeight(), e.getValue())));
        return chromosome;
    }
}
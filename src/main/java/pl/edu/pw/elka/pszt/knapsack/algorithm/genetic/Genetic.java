package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic;

import lombok.AllArgsConstructor;
import pl.edu.pw.elka.pszt.knapsack.algorithm.Algorithm;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Chromosome;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Gen;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Population;
import pl.edu.pw.elka.pszt.knapsack.model.KnapsackObjects;
import pl.edu.pw.elka.pszt.knapsack.model.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class Genetic implements Algorithm {
    private final KnapsackObjects iko;
    private final Settings settings;
    @Override
    public String calculate() throws CloneNotSupportedException {
        Population population = getInitPopulation((int)settings.getInitialPopulation());
        StringBuilder text = new StringBuilder();
        fix(population);
        do{
            text.append(population.toString()).append("\n");
            population = population.cycle(iko.knapsackCapacity.intValue(), (int)settings.getProbability());
        }while(population.getNumber() < settings.getIterations());
        text.append("result:").append("\n").append(population.toString()).append("\n").append(population.bestFound()).append("\n");
        return text.toString();
    }

    private void fix(Population population) {
        population.getChromosomes()
                .stream()
                .filter(e->e.weight()>iko.getKnapsackCapacity())
                .forEach(chromosome -> chromosome.fix(iko.getKnapsackCapacity().intValue()));
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
    private void randomizeGens(Population population){
        Random random = new Random(System.currentTimeMillis());
        population.getChromosomes().forEach(chromosome->
                chromosome.getGens().forEach(gen -> gen.setPresent(random.nextBoolean())));
    }
    private Chromosome getInitChromosome(){
        Chromosome chromosome = new Chromosome();
        iko.getItems().forEach(e->chromosome.add(new Gen(e.getWeight(),e.getValue())));
        return chromosome;
    }
}
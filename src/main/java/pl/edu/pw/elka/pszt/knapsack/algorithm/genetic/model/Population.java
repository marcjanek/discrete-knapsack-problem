package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Population implements Cloneable {

    @NonNull
    private final Long number;
    private final List<Chromosome> chromosomes = new ArrayList<>();
    private final List<Chromosome> parents = new ArrayList<>();
    private final List<Chromosome> children = new ArrayList<>();

    @Override
    public Object clone() throws CloneNotSupportedException {
        Population clone = new Population(this.number + 1);
        for (Chromosome chromosome : this.chromosomes) {
            clone.add((Chromosome) chromosome.clone());
        }
        return clone;
    }

    public void add(Chromosome chromosome) {
        this.chromosomes.add(chromosome);
    }

    public Population cycle(int maxVolume, double GenChance) throws CloneNotSupportedException {
        selectParents();
        crossover();
        fix(children, maxVolume);
        mutate(GenChance);
        fix(children, maxVolume);
        return nextGeneration();
    }

    public double getAverageFitness() {
        return this.chromosomes
                .stream()
                .mapToDouble(Chromosome::fitness)
                .average()
                .orElse(Double.NaN);
    }

    public double getMaxFitness() {
        return chromosomes.stream()
                .max(Comparator.comparingInt(Chromosome::fitness))
                .orElse(new Chromosome())
                .fitness();
    }

    public double getMinFitness() {
        return chromosomes.stream()
                .min(Comparator.comparingInt(Chromosome::fitness))
                .orElse(new Chromosome())
                .fitness();
    }

    public double dominatorPercentage() {
        if(chromosomes.size() == 0) return 100;
        int max = chromosomes.get(0).fitness();
        int count = 0;
        for (Chromosome chromosome : chromosomes) {
            if (chromosome.fitness() > max) {
                max = chromosome.fitness();
                count = 1;
            } else if (chromosome.fitness() == max) {
                count++;
            }
        }
        return (double) (count * 100) / (double) chromosomes.size();
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append("Population ").append(String.format("%5s", number + 1)).append(": ");
        for (Chromosome chromosome : chromosomes) {
            for (Gen gen : chromosome.getGens()) {
                text.append(gen.toString());
            }
            text.append(" ");
        }
        return text.toString();
    }

    public String bestFound(boolean printDetails)
    {
        if(printDetails){
            String chromosomeInString, fitness, volume, list = "";
            if(chromosomes.isEmpty()){
                chromosomeInString = "null";
                fitness = String.valueOf(Double.NaN);
                volume = String.valueOf(Double.NaN);
            } else {
                sort(chromosomes);
                Chromosome chromosome = chromosomes.get(0);
                chromosomeInString = chromosome.toString();
                fitness = String.valueOf(chromosome.fitness());
                volume = String.valueOf(chromosome.volume());
                list = chromosome.listItems();
            }
            return String.format("Best found: %s Fitness: %s Volume: %s \nList of items:\n%s", chromosomeInString, fitness, volume, list);
        } else{
            if(chromosomes.isEmpty()){
                return "";
            } else {
                sort(chromosomes);
                return chromosomes.get(0).listItems();
            }
        }

    }

    public void fixChromosomes(int maxVolume) {
        fix(this.chromosomes, maxVolume);
    }

    private void fix(List<Chromosome> list, int maxVolume) {
        list.forEach(chromosome -> chromosome.fix(maxVolume));
    }

    private void sort(List<Chromosome> list) {
        list.sort((d1, d2) -> d2.fitness() - d1.fitness());
    }

    private void crossover() throws CloneNotSupportedException {
        for (int i = 0; i < parents.size(); i++) {
            Chromosome mother = parents.get(i);
            Chromosome father;
            if (i == parents.size() - 1) father = parents.get(0);
            else father = parents.get(i + 1);
            cross(father, mother);
        }
    }

    private void cross(Chromosome father, Chromosome mother) throws CloneNotSupportedException {
        Chromosome child = (Chromosome) father.clone();
        Chromosome child2 = (Chromosome) mother.clone();
        if(father.equals(mother)) {
            children.add(child);
            children.add(child2);
            return;
        }
        int random = new Random().nextInt(father.size() + 1);
        for (; random < father.size(); random++) {
            child.changeGen(random, (Gen) mother.getGen((long) random).clone());
            child2.changeGen(random, (Gen) father.getGen((long) random).clone());
        }
        children.add(child);
        children.add(child2);
    }

    private void selectParents() {
        if (chromosomes.size() == 0)
            return;
        int scoresSum = chromosomes.stream().mapToInt(Chromosome::fitness).sum();       //roulette wheel
        if (scoresSum == 0)
            return;
        chromosomes.forEach(chromosome -> {
            int random = new Random().nextInt(scoresSum);
            int sum = 0;
            for (Chromosome chromosome1 : chromosomes) {
                sum += chromosome1.fitness();
                if (sum >= random) {
                    parents.add(chromosome1);
                    break;
                }
            }
        });
    }

    private void mutate(final double probability) {
        Random random = new Random();
        children.parallelStream().forEach(chromosome -> chromosome.getGens()
                .stream()
                .filter(gen -> random.nextDouble() <= probability)
                .forEach(Gen::negateIsPresent));
    }

    private Population nextGeneration() {
        if (chromosomes.size() == 0) return null;
        sort(children);
        sort(chromosomes);
        Population newPopulation = new Population(this.number + 1);
        newPopulation.add(chromosomes.get(0));
        newPopulation.addAll(children.stream().limit(chromosomes.size() - 1).collect(Collectors.toList()));
        return newPopulation;
    }

    private void addAll(List<Chromosome> chromosomes) {
        this.chromosomes.addAll(chromosomes);
    }
}

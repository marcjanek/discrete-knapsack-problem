package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Population implements Cloneable {
    @NonNull
    private final Long number;
    private List<Chromosome> chromosomes = new ArrayList<>();
    private List<Chromosome> parents = new ArrayList<>();
    private List<Chromosome> children = new ArrayList<>();

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object clone = super.clone();
        for (Chromosome chromosome : this.chromosomes) {
            ((Population) clone).add((Chromosome) chromosome.clone());
        }
        return clone;
    }

    public void add(Chromosome chromosome) {
        this.chromosomes.add(chromosome);
    }

    public void addAll(List<Chromosome> chromosomes) {
        this.chromosomes.addAll(chromosomes);
    }

    public double getAverageScore() {
        return this.chromosomes.stream().mapToDouble(Chromosome::fitness).sum() / this.chromosomes.size();
    }
    public Population cycle(int maxWeight, int probability) throws CloneNotSupportedException {
        selectParents();
        crossover();
        fix(children, maxWeight);
        mutate(probability);
        fix(children, maxWeight);
        return nextGeneration();
    }

    public double getMaxScore() {
        Chromosome chromosome = chromosomes.stream().max(Comparator.comparingInt(Chromosome::fitness)).orElseGet(null);
        return Objects.nonNull(chromosome) ? chromosome.fitness() : 0;
    }

    public double getMinScore() {
        Chromosome chromosome = chromosomes.stream().min(Comparator.comparingInt(Chromosome::fitness)).orElseGet(null);
        return Objects.nonNull(chromosome) ? chromosome.fitness() : 0;
    }

    public double dominatorPercentage() {
        Map<Integer, Integer> map = new HashMap<>();
        this.chromosomes.forEach(chromosome -> {
            int key = chromosome.fitness();
            map.put(key, map.getOrDefault(key, 0) + 1);
        });
        int frequency = map.values()
                .stream()
                .mapToInt(value -> value)
                .max()
                .orElse(0);
        return (((100L * (double) frequency) / (double) chromosomes.size()));
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append("Population ").append(String.format("%5s", number + 1)).append(": ");
        for (Chromosome chromosome : chromosomes) {
            for (Gen gen : chromosome.gens) {
                text.append(gen.toString());
            }
            text.append(" ");
        }
        return text.toString();
    }

    public String bestFound()
    {
        sort(chromosomes);
        return "Best found: " + chromosomes.get(0).toString() + " Fitness: " + chromosomes.get(0).fitness() + " Weight: " + chromosomes.get(0).weight();
    }

    private void fix(List<Chromosome> list, int maxWeight) {
        list.forEach(chromosome -> chromosome.fix(maxWeight));
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
        int random = new Random().nextInt(father.size() + 1);
        Chromosome child = new Chromosome();
        Chromosome child2 = new Chromosome();
        Long i = 0L;
        for (; i < random; i++) {
            child.add((Gen) father.getGen(i).clone());
            child2.add((Gen) mother.getGen(i).clone());
        }
        for (; i < father.size(); i++) {
            child.add((Gen) mother.getGen(i).clone());
            child2.add((Gen) father.getGen(i).clone());
        }
        children.add(child);
        children.add(child2);
    }

    private void selectParents() {
        if (chromosomes.size() == 0) return;
        //ToDo: same parent??

        int scoresSum = chromosomes.stream().mapToInt(Chromosome::fitness).sum();
        int random = new Random().nextInt(scoresSum);
        //ToDo: ?????
        for (int i = 0; i < chromosomes.size(); i++) {
            int sum = 0;
            for (Chromosome chromosome : chromosomes) {
                sum += chromosome.fitness();
                if (sum >= random) {
                    parents.add(chromosome);
                    break;
                }
            }
        }
    }

    private void mutate(final int probability) {
        final Random random = new Random();
        children.forEach(chromosome -> chromosome.gens.forEach(gen -> {
            int randomNumber = random.nextInt(1000);
            if (randomNumber <= probability) {
                gen.negateIsPresent();
            }
        }));
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


}

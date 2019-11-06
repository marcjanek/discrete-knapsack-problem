package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;

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

    private void sort(List<Chromosome> list) {
        list.sort((d1, d2) -> d2.fitness() - d1.fitness());
    }

    public void add(Chromosome chromosome) {
        this.chromosomes.add(chromosome);
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
            child.add((Gen) father.getGen(i).clone());
        }
        children.add(child);
        children.add(child2);
    }

    private void selectParents() {

        int scoressum = 0;
        for (Chromosome chromosome : chromosomes) {
            scoressum += chromosome.fitness();
        }
        int random = new Random().nextInt(scoressum);
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

    private void mutate(int probability) {
        for (Chromosome chromosome : children) {
            for (Gen gen : chromosome.gens) {
                int random = new Random().nextInt(1000);
                if (random <= probability) gen.negateIsPresent();
            }
        }
    }

    private void nextGeneration() {
        sort(children);
        sort(chromosomes);
        List<Chromosome> tmp = new ArrayList<>();
        tmp.add(chromosomes.get(0));
        int i = 1;
        while (i < chromosomes.size()) {
            tmp.add(children.get(i - 1));
        }
        chromosomes = tmp;
        parents.clear();
        children.clear();
    }

    public void cycle(int maxWeight, int probability) throws CloneNotSupportedException {
        selectParents();
        crossover();
        for (Chromosome child : children)
        {
            child.fix(maxWeight);
        }
        mutate(probability);
        for (Chromosome child : children)
        {
            child.fix(maxWeight);
        }
        nextGeneration();
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
}

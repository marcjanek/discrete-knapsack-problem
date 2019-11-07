package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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

    public void add(Chromosome chromosome) {
        this.chromosomes.add(chromosome);
    }

    public void addAll(List<Chromosome> chromosomes) {
        this.chromosomes.addAll(chromosomes);
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
        if(chromosomes.size() == 0) return;
        int scoressum = 0;
        for (Chromosome chromosome : chromosomes) {
            scoressum += chromosome.fitness();
        }
        int random = new Random().nextInt(scoressum);
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
        if(chromosomes.size() == 0) return null;
        sort(children);
        sort(chromosomes);
        List<Chromosome> tmp = new ArrayList<>();
        tmp.add(chromosomes.get(0));//ToDo: ??????
        int i = 1;
        while (i < chromosomes.size()) {
            tmp.add(children.get(i - 1));
            i++;
        }
        chromosomes = tmp;
        Population newPopulation = new Population(this.number + 1);
        newPopulation.addAll(tmp);
        return newPopulation;
    }

    public Population cycle(int maxWeight, int probability) throws CloneNotSupportedException {
        //ToDo: chromosomes are not cloned
        selectParents();
        crossover();
        for (Chromosome child : children) {
            child.fix(maxWeight);
        }
        mutate(probability);
        for (Chromosome child : children) {
            child.fix(maxWeight);
        }
        return nextGeneration();
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

    public String toString()
    {
        StringBuilder text = new StringBuilder();
        text.append("Population ").append(number + 1).append(": ");
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
}

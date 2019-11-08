package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode
public class Chromosome implements Cloneable {
    List<Gen> gens = new ArrayList<>();

    public void add(Gen gen) {
        gens.add(gen);
    }

    public int weight() {
        return getPresentGens().stream()
                .mapToInt(e -> Math.toIntExact(e.getWeight()))
                .sum();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Chromosome chromosome = new Chromosome();
        for (Gen gen : this.gens)
            chromosome.add((Gen) gen.clone());
        return chromosome;
    }

    public void fix(final int maxWeight) {
        Random random = new Random(System.currentTimeMillis());
        List<Gen> presentGens = this.gens.stream().filter(Gen::isPresent).collect(Collectors.toList());
        while (weight() > maxWeight && presentGens.size() > 0) {
            Gen gen = presentGens.get(random.nextInt(presentGens.size()));
            presentGens.remove(gen);
            gen.negateIsPresent();
        }
    }

    int size() {
        return gens.size();
    }

    Gen getGen(Long index) {
        return gens.get(Math.toIntExact(index));
    }

    void changeGen(long index, Gen gen) {
        gens.set((int) index, gen);
    }

    int fitness() {
        return getPresentGens().stream()
                .mapToInt(e -> Math.toIntExact(e.getValue()))
                .sum();
    }

    private List<Gen> getPresentGens() {
        return gens.stream()
                .filter(e -> e.isPresent)
                .collect(Collectors.toList());
    }
}

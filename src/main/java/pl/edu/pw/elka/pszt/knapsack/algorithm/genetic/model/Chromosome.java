package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class Chromosome implements Cloneable {
    List<Gen> gens = new ArrayList<>();

    public void add(Gen gen) {
        gens.add(gen);
    }

    int size() {
        return gens.size();
    }

    Gen getGen(Long index) {
        return gens.get(Math.toIntExact(index));
    }

    void mutate(int index) {
        gens.get(index % size()).negateIsPresent();
    }

    public int fitness() {
        return getPresentGens().stream()
                .mapToInt(e -> Math.toIntExact(e.getValue()))
                .sum();
    }

    public int weight() {
        return getPresentGens().stream()
                .mapToInt(e -> Math.toIntExact(e.getWeight()))
                .sum();
    }

    public List<Gen> getPresentGens() {
        return gens.stream()
                .filter(e -> e.isPresent)
                .collect(Collectors.toList());
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
        List<Gen> collect = this.gens.stream().filter(Gen::isPresent).collect(Collectors.toList());
        while (weight() > maxWeight && collect.size() > 0) {
            Gen gen = collect.get(random.nextInt(collect.size()));
            collect.remove(gen);
            gen.negateIsPresent();
        }
    }
}

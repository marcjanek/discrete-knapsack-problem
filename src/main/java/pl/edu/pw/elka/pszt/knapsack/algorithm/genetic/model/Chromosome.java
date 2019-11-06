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

    public int score() {
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

    public void fitness(int maxWeight) {
        Random random = new Random(System.currentTimeMillis());
        while(weight()>maxWeight){
            Gen gen;
            do{
                 gen = this.gens.get(random.nextInt(this.size()));
            }while (!gen.isPresent);
            gen.negateIsPresent();
        }
    }
}

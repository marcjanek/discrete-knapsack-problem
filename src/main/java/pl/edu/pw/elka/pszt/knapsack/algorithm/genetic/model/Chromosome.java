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

    private final List<Gen> gens = new ArrayList<>();

    public void add(Gen gen) {
        gens.add(gen);
    }

    public int volume() {
        return getPresentGens().stream()
                .mapToInt(e -> Math.toIntExact(e.getVolume()))
                .sum();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Chromosome chromosome = new Chromosome();
        for (Gen gen : this.gens) {
            chromosome.add((Gen) gen.clone());
        }
        return chromosome;
    }

    public void fix(final int maxVolume) {
        Random random = new Random(System.currentTimeMillis());
        List<Gen> presentGens = this.gens.stream().filter(Gen::isPresent).collect(Collectors.toList());
        while (volume() > maxVolume && presentGens.size() > 0) {
            Gen gen = presentGens.get(random.nextInt(presentGens.size()));
            gen.negateIsPresent();
            presentGens.remove(gen);
        }
    }

    String listItems() {
        StringBuilder out = new StringBuilder();
        for (Gen gen : gens) {
            if (gen.isPresent) {
                out.append(gen.getVolume()).append(" ").append(gen.getValue()).append("\n");
            }
        }
        return out.toString();
    }

    int size() {
        return gens.size();
    }

    Gen getGen(Long index) {
        return gens.get(Math.toIntExact(index));
    }

    void changeGen(long index, Gen newGen) {
        gens.set((int) index, newGen);
    }

    int fitness() {
        return getPresentGens().stream()
                .mapToInt(e -> Math.toIntExact(e.getValue()))
                .sum();
    }

    private List<Gen> getPresentGens() {
        return gens.stream()
                .filter(Gen::isPresent)
                .collect(Collectors.toList());
    }


}

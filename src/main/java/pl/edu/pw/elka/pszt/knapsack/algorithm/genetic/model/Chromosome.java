package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.lang.ref.Cleaner;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The type Chromosome.
 */
@Getter
@ToString
@EqualsAndHashCode
public class Chromosome implements Cloneable {

    final List<Gen> gens = new ArrayList<>();

    public void add(Gen gen) {
        gens.add(gen);
    }

    /**
     * calculates volume of chromosome
     *
     * @return the int
     */
    public int volume() {
        return getPresentGens().stream()
                .mapToInt(e -> Math.toIntExact(e.getVolume()))
                .sum();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object clone = super.clone();
        List<Gen> genList = this.gens;
        for (int i = 0; i < genList.size(); i++) {
            ((Chromosome) clone).changeGen(i, (Gen) genList.get(i).clone());
        }
        return clone;
    }

    /**
     * method decreases chromosome volume while it is bigger than maxVolume
     *
     * @param maxVolume the max volume
     */
    public void fix(final int maxVolume) {
        Random random = new Random(System.currentTimeMillis());
        List<Gen> presentGens = this.gens.stream().filter(Gen::isPresent).collect(Collectors.toList());
        while (volume() > maxVolume && presentGens.size() > 0) {
            Gen gen = presentGens.get(random.nextInt(presentGens.size()));
            gen.negateIsPresent();
            presentGens.remove(gen);
        }
    }

    int size() {
        return gens.size();
    }

    /**
     * Gets gen.
     *
     * @param index the search index
     * @return the gen
     */
    Gen getGen(Long index) {
        return gens.get(Math.toIntExact(index));
    }

    /**
     * method replaces old gen with new gen.
     *
     * @param index the index of old gen
     * @param newGen   the new gen
     */
    void changeGen(long index, Gen newGen) {
        gens.set((int) index, newGen);
    }

    /**
     * method calculates fitness of chromosome.
     *
     * @return the int
     */
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

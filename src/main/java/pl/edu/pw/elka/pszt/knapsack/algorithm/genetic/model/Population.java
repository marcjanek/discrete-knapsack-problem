package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter @RequiredArgsConstructor
public class Population implements Cloneable{
    @NonNull
    private final Long number;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object clone = super.clone();
        for (Chromosome chromosome : this.chromosomes) {
            ((Population)clone).add((Chromosome) chromosome.clone());
        }
        return clone;
    }

    List<Chromosome> chromosomes = new ArrayList<>();

    public void add(Chromosome chromosome){
        this.chromosomes.add(chromosome);
    }
    public void addAll(List<Chromosome> list){
        this.chromosomes.addAll(list);
    }

    public List<Chromosome> crossover(Long crossoverPoint) throws CloneNotSupportedException {
        List<Chromosome> crossover = new ArrayList<>();
        for (int i = 0; i < chromosomes.size() - 1; i++) {
            crossover.addAll(crossover(crossoverPoint,chromosomes.get(i),chromosomes.get(i + 1)));
        }
        if(chromosomes.size() >= 3)
            crossover.addAll(crossover(crossoverPoint,chromosomes.get(chromosomes.size() -1 ),chromosomes.get(0)));
        return crossover;
    }
    public void mutate(int seed){
        List<Chromosome> result = new ArrayList<>();
        Random random = new Random(seed);
        this.chromosomes.forEach(e->e.mutate(random.nextInt()));
    }

    private List<Chromosome> crossover(Long crossoverPoint, Chromosome chromosome1, Chromosome chromosome2) throws CloneNotSupportedException {
        List<Chromosome> crossover = new ArrayList<>();
        Chromosome newChromosome1 = new Chromosome();
        Chromosome newChromosome2 = new Chromosome();
        for(long i = 0; i<crossoverPoint && i<chromosome1.size(); i++){
            newChromosome1.add((Gen) chromosome1.getGen(i).clone());
            newChromosome2.add((Gen) chromosome2.getGen(i).clone());
        }
        for(long i = Math.toIntExact(crossoverPoint); i<chromosome1.size(); i++){
            newChromosome1.add((Gen) chromosome2.getGen(i).clone());
            newChromosome2.add((Gen) chromosome1.getGen(i).clone());
        }
        return crossover;
    }

    public double dominatorPercentage() {
        Map<Integer,Integer> map = new HashMap<>();
        this.chromosomes.forEach(chromosome -> {
            int key = chromosome.score();
            map.put(key,map.getOrDefault(key,0) + 1);
        });
        int frequency = map.values()
                .stream()
                .mapToInt(value -> value)
                .max()
                .orElse(0);
        return (((100L * (double) frequency) / (double) chromosomes.size()));
    }
}

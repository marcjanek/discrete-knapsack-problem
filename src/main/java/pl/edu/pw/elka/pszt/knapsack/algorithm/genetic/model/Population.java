package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter @RequiredArgsConstructor
public class Population {
    private final Long number;
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
            newChromosome1.add(chromosome1.getGen(i).clone());
            newChromosome2.add(chromosome2.getGen(i).clone());
        }
        for(long i = Math.toIntExact(crossoverPoint); i<chromosome1.size(); i++){
            newChromosome1.add(chromosome2.getGen(i).clone());
            newChromosome2.add(chromosome1.getGen(i).clone());
        }
        return crossover;
    }

    public double dominatorPercentage() {
        Map<Integer,Integer> map = new HashMap<>();
        int freqency;
        this.chromosomes.forEach(chromosome -> {
            int key = chromosome.score();
            map.put(key,map.getOrDefault(key,0) + 1);
        });
        freqency = map.values()
                .stream()
                .mapToInt(value -> value)
                .max()
                .orElse(0);
        return (((100L * (double) freqency) / (double) chromosomes.size()));
    }
}

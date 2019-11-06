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

    private List<Chromosome> chromosomes = new ArrayList<>();
    private List<Chromosome> parents = new ArrayList<>();
    private List<Chromosome> children = new ArrayList<>();

    public void add(Chromosome chromosome){
        this.chromosomes.add(chromosome);
    }

    void crossover() throws CloneNotSupportedException {
        for(int i = 0; i < parents.size(); i++)
        {
            Chromosome mother = parents.get(i);
            Chromosome father;
            if(i == parents.size() - 1) father = parents.get(0);
            else father = parents.get(i + 1);
            children.add(cross(father, mother));
        }
    }

    private Chromosome cross(Chromosome father, Chromosome mother) throws CloneNotSupportedException {
        int random = new Random().nextInt(father.size() + 1);
        Chromosome child = new Chromosome();
        Long i = 0L;
        for(; i < random; i++)
        {
            child.add((Gen) father.getGen(i).clone());
        }
        for(; i < father.size(); i++)
        {
            child.add((Gen) mother.getGen(i).clone());
        }
        return child;
    }

    private void selectParents() {

        int scoressum = 0;
        for (Chromosome chromosome : chromosomes) {
            scoressum += chromosome.score();
        }
        int random = new Random().nextInt(scoressum);
        for(int i = 0; i < chromosomes.size(); i++)
        {
            int sum = 0;
            for (Chromosome chromosome : chromosomes) {
                sum += chromosome.score();
                if (sum >= random) {
                    parents.add(chromosome);
                    break;
                }
            }
        }
    }

    public void mutate(int probability)
    {
        for (Chromosome chromosome : children)
        {
            for(Gen gen : chromosome.gens)
            {
                int random = new Random().nextInt(1000);
                if(random <= probability) gen.negateIsPresent();
            }
        }
    }
//    public void addAll(List<Chromosome> list){
//        this.chromosomes.addAll(list);
//    }
//
//    public List<Chromosome> crossover() throws CloneNotSupportedException {
//        List<Chromosome> crossover = new ArrayList<>();
//        for (int i = 0; i < chromosomes.size() - 1; i++) {
//            crossover.addAll(crossover(crossoverPoint,chromosomes.get(i),chromosomes.get(i + 1)));
//        }
//        if(chromosomes.size() >= 3)
//            crossover.addAll(crossover(crossoverPoint,chromosomes.get(chromosomes.size() -1 ),chromosomes.get(0)));
//        return crossover;
//    }
//    public void mutate(int seed, int probability){
//        for (Chromosome chromosome: chromosomes) {
//            for (Gen gen: chromosome.gens)
//            {
//
//            }
//        }
//        List<Chromosome> result = new ArrayList<>();
//        Random random = new Random(seed);
//        this.chromosomes.forEach(e->e.mutate(random.nextInt()));
//    }

//    private List<Chromosome> crossover(Long crossoverPoint, Chromosome chromosome1, Chromosome chromosome2) throws CloneNotSupportedException {
//        List<Chromosome> crossover = new ArrayList<>();
//        Chromosome newChromosome1 = new Chromosome();
//        Chromosome newChromosome2 = new Chromosome();
//        for(long i = 0; i<crossoverPoint && i<chromosome1.size(); i++){
//            newChromosome1.add((Gen) chromosome1.getGen(i).clone());
//            newChromosome2.add((Gen) chromosome2.getGen(i).clone());
//        }
//        for(long i = Math.toIntExact(crossoverPoint); i<chromosome1.size(); i++){
//            newChromosome1.add((Gen) chromosome2.getGen(i).clone());
//            newChromosome2.add((Gen) chromosome1.getGen(i).clone());
//        }
//        return crossover;
//    }

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

package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {
    
    Population emptyPopulation;
    Population oneChromosomePopulation;
    Population filledPopulation;
    private final int EMPTY_POPULATION_SIZE = 0;
    private final int ONE_CHROMOSOME_POPULATION_SIZE = 1;
    private final int FILLED_POPULATION_SIZE = 10;
    private final int CHROMOSOME_LENGTH = 5;
    
    @BeforeEach
    void init(){
        emptyPopulation = new Population(0L);
        oneChromosomePopulation = new Population(10L);
        filledPopulation = new Population(100L);
        oneChromosomePopulation.add(creteChromosome());
        for (int i = 0; i < FILLED_POPULATION_SIZE; i++) {
            filledPopulation.add(creteChromosome());
        }
    }
    Chromosome creteChromosome(){
        Chromosome chromosome = new Chromosome();
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            Gen gen = new Gen((long) i % FILLED_POPULATION_SIZE,
                    (long) (i * i) % FILLED_POPULATION_SIZE);
            if(i%CHROMOSOME_LENGTH == 0){
                gen.setPresent(true);
            }
            chromosome.add(gen);
        }
        return chromosome;
    }
    @Nested
    class Add{
        @Test
        @DisplayName("1 chromosome to empty population")
        void test1(){
            assertEquals(EMPTY_POPULATION_SIZE, emptyPopulation.getChromosomes().size());
            final Chromosome chromosome = creteChromosome();
            emptyPopulation.add(chromosome);
            assertEquals(EMPTY_POPULATION_SIZE + 1, emptyPopulation.getChromosomes().size());
            assertSame(chromosome, emptyPopulation.getChromosomes().get(EMPTY_POPULATION_SIZE));
        }
        @Test
        @DisplayName("1 chromosome to one chromosome population")
        void test2(){
            assertEquals(ONE_CHROMOSOME_POPULATION_SIZE, oneChromosomePopulation.getChromosomes().size());
            final Chromosome chromosome = creteChromosome();
            oneChromosomePopulation.add(chromosome);
            assertEquals(ONE_CHROMOSOME_POPULATION_SIZE + 1, oneChromosomePopulation.getChromosomes().size());
            assertSame(chromosome, oneChromosomePopulation.getChromosomes().get(ONE_CHROMOSOME_POPULATION_SIZE));

        }
        @Test
        @DisplayName("few new chromosome to filled population")
        void test3(){
            final int NUMBER_OF_NEW_CHROMOSOMES = 10;
            assertEquals(FILLED_POPULATION_SIZE, filledPopulation.getChromosomes().size());
            for (int i = 0; i < NUMBER_OF_NEW_CHROMOSOMES; i++) {
                filledPopulation.add(creteChromosome());
            }
            assertEquals(FILLED_POPULATION_SIZE + NUMBER_OF_NEW_CHROMOSOMES,
                    filledPopulation.getChromosomes().size());
        }
    }

    @Nested
    class Cycle{
        //ToDo:
    }

    @Nested
    class GetAverageScore{
        @Test
        @DisplayName("empty population")
        void test1(){
            assertEquals(Double.NaN,emptyPopulation.getAverageFitness());
        }
        @Test
        @DisplayName("one chromosome population")
        void test2(){
            assertEquals(average(oneChromosomePopulation.getChromosomes()),oneChromosomePopulation.getAverageFitness());
        }
        @Test
        @DisplayName("filled population")
        void test3(){
            assertEquals(average(filledPopulation.getChromosomes()),filledPopulation.getAverageFitness());
        }
        private double average(List<Chromosome> chromosomes){
            return chromosomes.stream().mapToDouble(Chromosome::fitness).average().orElse(Double.NaN);
        }
    }

    @Nested
    class GetMaxScore{
        @Test
        @DisplayName("empty population")
        void test1(){
            assertEquals(0,emptyPopulation.getMaxFitness());
        }
        @Test
        @DisplayName("one chromosome population")
        void test2(){
            assertEquals(max(oneChromosomePopulation.getChromosomes()),oneChromosomePopulation.getMaxFitness());
        }
        @Test
        @DisplayName("filled population")
        void test3(){
            assertEquals(max(filledPopulation.getChromosomes()),filledPopulation.getMaxFitness());
        }
        private double max(List<Chromosome> chromosomes){
            return chromosomes.stream()
                    .max(Comparator.comparingInt(Chromosome::fitness))
                    .orElse(new Chromosome())
                    .fitness();
        }
    }

    @Nested
    class GetMinScore{
        @Test
        @DisplayName("empty population")
        void test1(){
            assertEquals(0,emptyPopulation.getMinFitness());
        }
        @Test
        @DisplayName("one chromosome population")
        void test2(){
            assertEquals(min(oneChromosomePopulation.getChromosomes()),oneChromosomePopulation.getMinFitness());
        }
        @Test
        @DisplayName("filled population")
        void test3(){
            assertEquals(min(filledPopulation.getChromosomes()),filledPopulation.getMinFitness());
        }
        private double min(List<Chromosome> chromosomes){
            return chromosomes.stream()
                    .min(Comparator.comparingInt(Chromosome::fitness))
                    .orElse(new Chromosome())
                    .fitness();
        }
    }

    @Nested
    class DominatorPercentage{
        @Test
        @DisplayName("empty population")
        void test1(){
            assertEquals(dominatorPercentage(emptyPopulation.getChromosomes()),emptyPopulation.dominatorPercentage());
        }
        @Test
        @DisplayName("one chromosome population")
        void test2(){
            assertEquals(dominatorPercentage(oneChromosomePopulation.getChromosomes()),
                    oneChromosomePopulation.dominatorPercentage());
        }
        @Test
        @DisplayName("filled population")
        void test3(){
            assertEquals(dominatorPercentage(filledPopulation.getChromosomes()),filledPopulation.dominatorPercentage());
        }
        private double dominatorPercentage(List<Chromosome> chromosomes){
            HashMap<Integer, Integer> map = new HashMap<>();
            chromosomes.forEach(chromosome -> map.put(chromosome.fitness(),
                    map.getOrDefault(chromosome.fitness(), 0) + 1));
            Optional<Map.Entry<Integer, Integer>> max;
            max = map.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue));
            return max.map(integerIntegerEntry ->
                    (double) integerIntegerEntry.getValue() * 100 / (double) chromosomes.size()).orElse(100D);
        }
    }
}
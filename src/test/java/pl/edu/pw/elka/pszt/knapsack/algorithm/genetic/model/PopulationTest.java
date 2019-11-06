package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {
    @Nested
    class Check
    {
        void check(Population population) throws CloneNotSupportedException {
            Population population1 = (Population) population.clone();
            population1.crossover();
            assertEquals(population.getParents().size(), population1.getChildren().size());
        }

        @Test
        @DisplayName("crossoverforzero")
        void testCrossover() throws CloneNotSupportedException {
            Population population = new Population(0L);
            check(population);
        }

        @Test
        @DisplayName("crossoverforone")

        void testCrossover1() throws CloneNotSupportedException {
            Population population = new Population(1L);
            check(population);
        }

        @Test
        @DisplayName("crossoverfortwo")

        void testCrossover2() throws CloneNotSupportedException {
            Population population = new Population(2L);
            check(population);
        }
    }
}
package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        Gen gen = new Gen(1L,2L);
        Gen clone = gen.clone();
        assertNotSame(gen,clone);
    }
}
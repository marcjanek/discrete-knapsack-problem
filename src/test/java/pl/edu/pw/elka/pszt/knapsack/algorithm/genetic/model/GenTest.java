package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        Gen gen = new Gen(1L,2L);
        gen.isPresent = false;
        Gen clone = (Gen) gen.clone();
        gen.isPresent = true;
        assertNotSame(gen,clone);
        assertNotEquals(clone.isPresent,gen.isPresent);
    }
}
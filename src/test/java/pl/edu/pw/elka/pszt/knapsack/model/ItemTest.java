package pl.edu.pw.elka.pszt.knapsack.model;

import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Gen;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        Gen gen = new Gen(1L,2L);
        Gen copy = gen.clone();
        assertNotSame(gen, copy);
    }
}
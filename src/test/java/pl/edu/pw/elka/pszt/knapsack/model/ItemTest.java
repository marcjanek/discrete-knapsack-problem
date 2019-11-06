package pl.edu.pw.elka.pszt.knapsack.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;

class ItemTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        Item item = new Item(1L, 2L);
        Object clone = item.clone();
        assertNotSame(item, clone);
    }
}
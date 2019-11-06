package pl.edu.pw.elka.pszt.knapsack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidateKnapsackObjectsTest {
    @Nested
    class correctObjects {
        KnapsackObjects knapsackObjects;

        @BeforeEach
        void init() {
            knapsackObjects = new KnapsackObjects(10L);
            knapsackObjects.add(new Item(1L, 2L));
            knapsackObjects.add(new Item(3L, 4L));
            knapsackObjects.add(new Item(100L, 200L));
        }

        @Test
        void checkCapacity() {
            assertTrue(ValidateKnapsackObjects.checkCapacity(knapsackObjects));
        }

        @Test
        void checkItems() {
            assertTrue(ValidateKnapsackObjects.checkItems(knapsackObjects));
        }
    }

    @Nested
    class incorrectObjects {
        KnapsackObjects knapsackObjects;

        @BeforeEach
        void init() {
            knapsackObjects = new KnapsackObjects(-10L);
            knapsackObjects.add(new Item(1L, -2L));
            knapsackObjects.add(new Item(3L, 4L));
            knapsackObjects.add(new Item(100L, 200L));
        }

        @Test
        void checkCapacity() {
            assertFalse(ValidateKnapsackObjects.checkCapacity(knapsackObjects));
        }

        @Test
        void checkItems() {
            assertFalse(ValidateKnapsackObjects.checkItems(knapsackObjects));
        }
    }

}
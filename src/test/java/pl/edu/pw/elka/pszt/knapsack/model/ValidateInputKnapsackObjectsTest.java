package pl.edu.pw.elka.pszt.knapsack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateInputKnapsackObjectsTest {
    @Nested
    class correctObjects{
        InputKnapsackObjects inputKnapsackObjects;
        @BeforeEach
        void init(){
            inputKnapsackObjects = new InputKnapsackObjects(10L);
            inputKnapsackObjects.add(new Item(1L, 2L));
            inputKnapsackObjects.add(new Item(3L, 4L));
            inputKnapsackObjects.add(new Item(100L,200L));
        }

        @Test
        void checkCapacity() {
            assertTrue(ValidateInputKnapsackObjects.checkCapacity(inputKnapsackObjects));
        }

        @Test
        void checkItems() {
            assertTrue(ValidateInputKnapsackObjects.checkItems(inputKnapsackObjects));
        }
    }
    @Nested
    class incorrectObjects{
        InputKnapsackObjects inputKnapsackObjects;
        @BeforeEach
        void init(){
            inputKnapsackObjects = new InputKnapsackObjects(-10L);
            inputKnapsackObjects.add(new Item(1L, -2L));
            inputKnapsackObjects.add(new Item(3L, 4L));
            inputKnapsackObjects.add(new Item(100L,200L));
        }

        @Test
        void checkCapacity() {
            assertFalse(ValidateInputKnapsackObjects.checkCapacity(inputKnapsackObjects));
        }

        @Test
        void checkItems() {
            assertFalse(ValidateInputKnapsackObjects.checkItems(inputKnapsackObjects));
        }
    }

}
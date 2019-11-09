package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        Gen gen = new Gen(1L, 2L);
        gen.isPresent = false;
        Gen clone = (Gen) gen.clone();
        gen.isPresent = true;
        assertNotSame(gen, clone);
        assertNotEquals(clone.isPresent, gen.isPresent);
    }

    @Nested
    class ToString {
        private Gen gen;

        @BeforeEach
        void init() {
            this.gen = new Gen(1L, 3L);
        }

        @Test
        @DisplayName("Default value")
        void test1() {
            assertEquals("0", gen.toString());
        }

        @Test
        @DisplayName("One change value")
        void test2() {
            gen.negateIsPresent();
            assertEquals("1", gen.toString());
        }
    }

    @Nested
    class negateIsPresent {
        private Gen gen;

        @BeforeEach
        void init() {
            this.gen = new Gen(1L, 3L);
        }

        @Test
        @DisplayName("Default value negation")
        void test1() {
            assertFalse(gen.isPresent);
            gen.negateIsPresent();
            assertTrue(gen.isPresent);
        }

        @Test
        @DisplayName("Double value negation")
        void test2() {
            final boolean before = gen.isPresent;
            gen.negateIsPresent();
            gen.negateIsPresent();
            final boolean after = gen.isPresent;
            assertEquals(before, after);
        }
    }
}
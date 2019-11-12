package pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.population;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ChromosomeTest {
    private final static int SIZE_OF_FILLED_CHROMOSOME = 80;
    Chromosome emptyChromosome;
    Chromosome filledChromosome;

    @BeforeEach
    void init() {
        this.emptyChromosome = new Chromosome();
        this.filledChromosome = new Chromosome();
        for (int i = 0; i < SIZE_OF_FILLED_CHROMOSOME; i++) {
            Gen gen = new Gen((long) i, (long) i * i);
            if (i % 7 == 0) {
                gen.setPresent(true);
            }
            filledChromosome.add(gen);
        }
    }

    @Nested
    class Clone{
        @Test
        @DisplayName("empty list")
        void test1() throws CloneNotSupportedException {
            Object clone = emptyChromosome.clone();
            assertNotSame(emptyChromosome, clone);
            assertNotSame(emptyChromosome.getGens(),((Chromosome) clone).getGens());
        }

        @Test
        @DisplayName("filled list")
        void test2() throws CloneNotSupportedException {
            final Chromosome clone = (Chromosome) filledChromosome.clone();
            assertNotSame(emptyChromosome, clone);
            assertNotSame(emptyChromosome.getGens(),clone.getGens());
            List<Gen> gens = emptyChromosome.getGens();
            for (int i = 0; i < gens.size(); i++) {
                assertNotSame(emptyChromosome.getGen((long)i),clone.getGen((long)i));
            }
        }
    }

    @Nested
    class Add {
        @Test
        @DisplayName("add gen to empty list")
        void test1() {
            assertEquals(0, emptyChromosome.getGens().size());
            emptyChromosome.add(new Gen(1L, 2L));
            assertEquals(1, emptyChromosome.getGens().size());
        }

        @Test
        @DisplayName("80 number of new items")
        void test2() {
            final int NUMBER_OF_ITEMS = 80;
            assertEquals(0, emptyChromosome.getGens().size());
            for (int i = 0; i < NUMBER_OF_ITEMS; ++i) {
                emptyChromosome.add(new Gen(1L, 2L));
            }
            assertEquals(NUMBER_OF_ITEMS, emptyChromosome.getGens().size());
        }
    }

    @Nested
    class volume {
        @Test
        @DisplayName("empty list")
        void test1() {
            assertEquals(0, emptyChromosome.getGens().size());
            assertEquals(0, emptyChromosome.volume());
        }

        @Test
        @DisplayName("filled list")
        void test2() {
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.getGens().size());
            assertEquals(sum(filledChromosome.getGens()), filledChromosome.volume());
        }

        int sum(List<Gen> list) {
            if (Objects.isNull(list))
                return 0;
            return list.stream().filter(Gen::isPresent).mapToInt(e -> Math.toIntExact(e.getVolume())).sum();
        }
    }

    @Nested
    class fix {
        final int EMPTY_SIZE = 0;
        final int NEGATIVE_SIZE = -1;
        final int POSITIVE_SIZE = 10;

        @Test
        @DisplayName("empty list with max value 0")
        void test1() {
            assertEquals(EMPTY_SIZE, emptyChromosome.getGens().size());
            emptyChromosome.fix(EMPTY_SIZE);
            assertEquals(emptyChromosome.volume(), EMPTY_SIZE);
        }

        @Test
        @DisplayName("empty list with max value -1")
        void test2() {
            assertEquals(EMPTY_SIZE, emptyChromosome.getGens().size());
            emptyChromosome.fix(NEGATIVE_SIZE);
            assertEquals(emptyChromosome.volume(), EMPTY_SIZE);
        }

        @Test
        @DisplayName("empty list with max value 10")
        void test3() {
            assertEquals(EMPTY_SIZE, emptyChromosome.getGens().size());
            emptyChromosome.fix(POSITIVE_SIZE);
            assertEquals(emptyChromosome.volume(), EMPTY_SIZE);
        }

        @Test
        @DisplayName("filled list with max value -1")
        void test4() {
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.getGens().size());
            filledChromosome.fix(NEGATIVE_SIZE);
            assertEquals(filledChromosome.volume(), EMPTY_SIZE);
        }

        @Test
        @DisplayName("filled list with max value 0")
        void test5() {
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.getGens().size());
            filledChromosome.fix(EMPTY_SIZE);
            assertEquals(filledChromosome.volume(), EMPTY_SIZE);
        }

        @Test
        @DisplayName("filled list with max value 10")
        void test6() {
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.getGens().size());
            filledChromosome.fix(EMPTY_SIZE);
            assertTrue(filledChromosome.volume() <= POSITIVE_SIZE);
        }
    }

    @Nested
    class Size {
        @Test
        @DisplayName("empty list")
        void test1() {
            assertEquals(0, emptyChromosome.getGens().size());
            assertEquals(0, emptyChromosome.size());
        }

        @Test
        @DisplayName("filled list")
        void test2() {
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.getGens().size());
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.size());
        }
    }

    @Nested
    class GetGen {
        final long ZERO_INDEX = 0;
        final long POSITIVE_INDEX = 10;

        @Test
        @DisplayName("empty list with zero index")
        void test1() {
            try {
                emptyChromosome.getGen(ZERO_INDEX);
                fail();
            } catch (Exception ignored) {
            }

        }

        @Test
        @DisplayName("empty list with positive index")
        void test2() {
            try {
                emptyChromosome.getGen(POSITIVE_INDEX);
                fail();
            } catch (Exception ignored) {
            }

        }

        @Test
        @DisplayName("filled list with zero index")
        void test3() {
            assertSame(filledChromosome.getGens().get((int) ZERO_INDEX), filledChromosome.getGen(ZERO_INDEX));
        }

        @Test
        @DisplayName("filled list with middle index")
        void test5() {
            assertSame(filledChromosome.getGens().get((SIZE_OF_FILLED_CHROMOSOME - 1) / 2),
                    filledChromosome.getGen((long) (SIZE_OF_FILLED_CHROMOSOME - 1) / 2));
        }

        @Test
        @DisplayName("filled list with last index")
        void test6() {
            assertSame(filledChromosome.getGens().get(SIZE_OF_FILLED_CHROMOSOME - 1),
                    filledChromosome.getGen((long) (SIZE_OF_FILLED_CHROMOSOME - 1)));
        }
    }

    @Nested
    class SetGen {
        final long ZERO_INDEX = 0;
        final long POSITIVE_INDEX = 10;
        final Gen gen = new Gen(1L, 2L);

        @Test
        @DisplayName("empty list with zero index")
        void test1() {
            try {
                emptyChromosome.changeGen(ZERO_INDEX, gen);
                fail();
            } catch (Exception ignored) {
            }

        }

        @Test
        @DisplayName("empty list with positive index")
        void test2() {
            try {
                emptyChromosome.changeGen(POSITIVE_INDEX, gen);
                fail();
            } catch (Exception ignored) {
            }

        }

        @Test
        @DisplayName("filled list with zero index")
        void test3() {
            final Gen oldGen = filledChromosome.getGen(ZERO_INDEX);
            filledChromosome.changeGen(ZERO_INDEX, gen);
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.size());
            assertSame(gen, filledChromosome.getGen(ZERO_INDEX));
            assertNotSame(oldGen, filledChromosome.getGen(ZERO_INDEX));
        }

        @Test
        @DisplayName("filled list with middle index")
        void test5() {
            final Gen oldGen = filledChromosome.getGen((long) ((SIZE_OF_FILLED_CHROMOSOME - 1) / 2));
            filledChromosome.changeGen((SIZE_OF_FILLED_CHROMOSOME - 1) / 2, gen);
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.size());
            assertSame(gen, filledChromosome.getGen((long) ((SIZE_OF_FILLED_CHROMOSOME - 1) / 2)));
            assertNotSame(oldGen, filledChromosome.getGen((long) ((SIZE_OF_FILLED_CHROMOSOME - 1) / 2)));
        }

        @Test
        @DisplayName("filled list with last index")
        void test6() {
            final Gen oldGen = filledChromosome.getGen((long) (SIZE_OF_FILLED_CHROMOSOME - 1));
            filledChromosome.changeGen(SIZE_OF_FILLED_CHROMOSOME - 1, gen);
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.size());
            assertSame(gen, filledChromosome.getGen((long) (SIZE_OF_FILLED_CHROMOSOME - 1)));
            assertNotSame(oldGen, filledChromosome.getGen((long) (SIZE_OF_FILLED_CHROMOSOME - 1)));
        }
    }

    @Nested
    class Fitness {
        @Test
        @DisplayName("empty list")
        void test1() {
            assertEquals(0, emptyChromosome.getGens().size());
            assertEquals(0, emptyChromosome.fitness());
        }

        @Test
        @DisplayName("filled list")
        void test2() {
            assertEquals(SIZE_OF_FILLED_CHROMOSOME, filledChromosome.getGens().size());
            assertEquals(sum(filledChromosome.getGens()), filledChromosome.fitness());
        }

        int sum(List<Gen> list) {
            if (Objects.isNull(list))
                return 0;
            return list.stream().filter(Gen::isPresent).mapToInt(e -> Math.toIntExact(e.getValue())).sum();
        }
    }
}
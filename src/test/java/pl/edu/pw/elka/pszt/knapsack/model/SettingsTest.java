package pl.edu.pw.elka.pszt.knapsack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SettingsTest {

    private final String PATH = "src/test/resources/pl.edu.pw.elka.pszt.knapsack.model/settings/";

    private Settings settings;
    @BeforeEach
    void init(){
        settings = new Settings(10L);
    }


    @Nested
    class CorrectInput {


        @Test
        @DisplayName("3 values")
        void test1() {
            String path = PATH + "correctInput/test1.txt";
            settings.initDataFromFile(path);
            assertEquals(101, settings.getGenChance());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(1,settings.getIterations());
        }

        @Test
        @DisplayName("2 values")
        void test2() {
            String path = PATH + "correctInput/test2.txt";
            settings.initDataFromFile(path);
            assertEquals(101, settings.getGenChance());
            assertEquals(new Settings(10L).getDominatorPercentage(),settings.getDominatorPercentage());
            assertEquals(1,settings.getIterations());
        }

        @Test
        @DisplayName("1 value")
        void test3() {
            String path = PATH + "correctInput/test3.txt";
            settings.initDataFromFile(path);
            assertEquals(101, settings.getGenChance());
            assertEquals(new Settings(10L).getDominatorPercentage(),settings.getDominatorPercentage());
            assertEquals(new Settings(10L).getIterations(),settings.getIterations());
        }
    }

    @Nested
    class IncorrectInput {
        @Test
        @DisplayName("less then 0")
        void test1() {
            String path = PATH + "exceptions/test1.txt";
            settings.initDataFromFile(path);
            assertEquals(101, settings.getGenChance());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(new Settings(10L).getIterations(),settings.getIterations());
        }

        @Test
        @DisplayName("char in number")
        void test2() {
            String path = PATH + "exceptions/test2.txt";
            settings.initDataFromFile(path);
            assertEquals(101, settings.getGenChance());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(new Settings(10L).getIterations(),settings.getIterations());
        }
        @Test
        @DisplayName("no \"=\" between key and value")
        void test3() {
            String path = PATH + "exceptions/test3.txt";
            settings.initDataFromFile(path);
            assertEquals(101, settings.getGenChance());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(new Settings(10L).getIterations(),settings.getIterations());
        }
        @Test
        @DisplayName("2 \"=\" in line")
        void test4() {
            String path = PATH + "exceptions/test3.txt";
            settings.initDataFromFile(path);
            assertEquals(101, settings.getGenChance());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(new Settings(10L).getIterations(),settings.getIterations());
        }
    }
}
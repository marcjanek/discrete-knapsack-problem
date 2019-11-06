package pl.edu.pw.elka.pszt.knapsack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {

    @Test
    void initDataFromFile() {
    }

    private final String PATH = "src/test/resources/pl.edu.pw.elka.pszt.knapsack.model/settings/";

    String loadFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine())
            stringBuilder.append(scanner.nextLine()).append("\n");
        scanner.close();
        return stringBuilder.toString();
    }

    Settings settings;
    @BeforeEach
    void init(){
        settings = new Settings();
    }


    @Nested
    class CorrectInput {


        @Test
        @DisplayName("3 values")
        void test1() {
            String path = PATH + "correctInput/test1.txt";
            settings.initDataFromFile(path);
            assertEquals(101,settings.getProbability());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(1,settings.getIterations());
        }

        @Test
        @DisplayName("2 values")
        void test2() {
            String path = PATH + "correctInput/test2.txt";
            settings.initDataFromFile(path);
            assertEquals(101,settings.getProbability());
            assertEquals(new Settings().getDominatorPercentage(),settings.getDominatorPercentage());
            assertEquals(1,settings.getIterations());
        }

        @Test
        @DisplayName("1 value")
        void test3() {
            String path = PATH + "correctInput/test3.txt";
            settings.initDataFromFile(path);
            assertEquals(101,settings.getProbability());
            assertEquals(new Settings().getDominatorPercentage(),settings.getDominatorPercentage());
            assertEquals(new Settings().getIterations(),settings.getIterations());
        }
    }

    @Nested
    class IncorrectInput {
        @Test
        @DisplayName("less then 0")
        void test1() {
            String path = PATH + "exceptions/test1.txt";
            settings.initDataFromFile(path);
            assertEquals(101,settings.getProbability());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(new Settings().getProbability(),settings.getIterations());
        }

        @Test
        @DisplayName("char in number")
        void test2() {
            String path = PATH + "exceptions/test2.txt";
            settings.initDataFromFile(path);
            assertEquals(101,settings.getProbability());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(new Settings().getProbability(),settings.getIterations());
        }
        @Test
        @DisplayName("no \"=\" between key and value")
        void test3() {
            String path = PATH + "exceptions/test3.txt";
            settings.initDataFromFile(path);
            assertEquals(101,settings.getProbability());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(new Settings().getProbability(),settings.getIterations());
        }
        @Test
        @DisplayName("2 \"=\" in line")
        void test4() {
            String path = PATH + "exceptions/test3.txt";
            settings.initDataFromFile(path);
            assertEquals(101,settings.getProbability());
            assertEquals(0.8,settings.getDominatorPercentage());
            assertEquals(new Settings().getProbability(),settings.getIterations());
        }
    }
}
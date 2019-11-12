package pl.edu.pw.elka.pszt.knapsack;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class KnapsackTest {

    @Nested
    class Run{
        @TempDir
        File anotherTempDir;
        final String OUT = "/out.txt";
        @Test
        void test1() throws IOException, CloneNotSupportedException {
            assertTrue(anotherTempDir.isDirectory(), "Should be a directory ");
            Knapsack knapsack = new Knapsack("src/test/resources/pl.edu.pw.elka.pszt.knapsack/test1.txt",
                    anotherTempDir.getAbsolutePath()+OUT);
            knapsack.run();
            assertTrue(Files.exists(Paths.get(anotherTempDir.toPath() + OUT)),"File should exist");
        }
    }
}
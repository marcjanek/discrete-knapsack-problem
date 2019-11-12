package pl.edu.pw.elka.pszt.knapsack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class FileGetterTest {
    final private String TEST_STRING = "test\n";
    final private String TEST_FILENAME = "out.txt";

    @TempDir
    File file;

    @BeforeEach
    void init() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath() + TEST_FILENAME));
        writer.write(TEST_STRING);
        writer.close();
    }

    @Test
    void getDataFromFile() throws FileNotFoundException {
        assert file.exists();
        String dataFromFile = new FileGetter().getDataFromFile(file.getPath() + TEST_FILENAME);
        assertEquals(TEST_STRING, dataFromFile);
    }

    @Test
    void _getDataFromFile() {
        assert file.exists();
        final String WRONG_FILE = "out1.txt";
        assertThrows(FileNotFoundException.class,()->new FileGetter().getDataFromFile(file.getPath()+WRONG_FILE));
    }
}
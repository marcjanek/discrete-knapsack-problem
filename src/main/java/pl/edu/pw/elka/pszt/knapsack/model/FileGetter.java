package pl.edu.pw.elka.pszt.knapsack.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class FileGetter {
    String getDataFromFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(getFile(path));
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine())
            stringBuilder.append(scanner.nextLine()).append("\n");
        scanner.close();
        return stringBuilder.toString();
    }
    private File getFile(String path) {
        return new File(path);
    }
}

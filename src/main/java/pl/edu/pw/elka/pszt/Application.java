package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.knapsack.Knapsack;

import java.io.IOException;
import java.util.Objects;
// src/main/resources/inputExample.txt src/main/resources/out.txt src/main/resources/settings.txt
public class Application {
    private static final int NUMBER_OF_ARGS = 3;
    public static void main(String[] args) {
        if (Objects.isNull(args)) {
            System.out.println("Expected are two arguments");
            return;
        } else if (args.length != NUMBER_OF_ARGS) {
            System.out.println(String.format("Expected are %d arguments, but found: %d", NUMBER_OF_ARGS, args.length));
            return;
        }
        try {
            new Knapsack(args[0], args[1], args[2]).run();
        } catch (IOException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
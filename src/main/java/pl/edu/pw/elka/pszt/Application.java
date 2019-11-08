package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.knapsack.Knapsack;

import java.io.IOException;
import java.util.Objects;
// src/main/resources/inputExample.txt src/main/resources/out.txt src/main/resources/settings.txt
class Application {
    private static final int NUMBER_OF_REQUIRED_ARGS = 2;
    private static final int NUMBER_OF_OPTIONAL_ARGS = 1;
    public static void main(String[] args) {
        if (Objects.isNull(args)) {
            System.out.println("Expected are two can't be null");
        } else if (args.length == NUMBER_OF_REQUIRED_ARGS) {
            try {
                new Knapsack(args[0], args[1]).run();
            } catch (IOException | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else if (args.length == NUMBER_OF_REQUIRED_ARGS + NUMBER_OF_OPTIONAL_ARGS) {
            try {
                new Knapsack(args[0], args[1], args[2]).run();
            } catch (IOException | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(String.format(
                    "Expected are %d arguments, but found: %d", NUMBER_OF_REQUIRED_ARGS, args.length
            ));
        }
    }
}
package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.knapsack.Knapsack;

import java.io.IOException;
import java.util.Objects;
// example of program arguments file: example_input_files/inputExample.txt example_input_files/out.txt
// example of program arguments with settings file: example_input_files/inputExample.txt example_input_files/out.txt example_input_files/settings.txt
class Application {
    private static final int NUMBER_OF_REQUIRED_ARGS = 2;
    private static final int NUMBER_OF_OPTIONAL_ARGS = 1;
    public static void main(String[] args) {
        try{
            if (Objects.isNull(args)) {
                System.out.println("input args can't be null");
            } else if (args.length == NUMBER_OF_REQUIRED_ARGS) {
                new Knapsack(args[0], args[1]).run();
            } else if (args.length == NUMBER_OF_REQUIRED_ARGS + NUMBER_OF_OPTIONAL_ARGS) {
                new Knapsack(args[0], args[1], args[2]).run();
            } else {
                System.out.println(String.format(
                        "Expected are %d arguments, but found: %d", NUMBER_OF_REQUIRED_ARGS, args.length
                ));
            }
        } catch (IOException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
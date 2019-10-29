package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.knapsack.Knapsack;

import java.io.IOException;
import java.util.Objects;

public class Application {
    public static void main(String[] args) {
        if (Objects.isNull(args)) {
            System.out.println("Expected are two arguments");
            return;
        } else if (args.length != 2) {
            System.out.println(String.format("Expected are two arguments, but found: %d", args.length));
            return;
        }
        try {
            new Knapsack(args[0], args[1]).run();
        } catch (IOException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

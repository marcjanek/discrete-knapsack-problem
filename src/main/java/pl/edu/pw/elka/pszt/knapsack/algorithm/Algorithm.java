package pl.edu.pw.elka.pszt.knapsack.algorithm;

import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Population;

import java.util.List;

/**
 * The interface Algorithm.
 */
public interface Algorithm {
    /**
     * Calculate solution for input data.
     *
     * @return the string
     * @throws CloneNotSupportedException the clone not supported exception
     */
    String calculate() throws CloneNotSupportedException;

    /**
     * Gets old populations.
     *
     * @return the old populations
     */
    List<Population> getOldPopulations();
}

package it.uniroma1.ai.search.algorithm.local;

import it.uniroma1.ai.search.algorithm.SearchAlgorithm;
import it.uniroma1.ai.search.node.LocalSearchNode;
import it.uniroma1.ai.search.problem.AbstractLocalProblem;

import java.util.List;
import java.util.Random;

/**
 * Abstract base for local search algorithms.
 * Subclasses implement specific strategies (Steepest Ascent, Hill Climbing, Simulated Annealing, etc.).
 */
public abstract class AbstractLocalSearch<S, A>
        implements SearchAlgorithm< AbstractLocalProblem<S, A> , S > {

    private final boolean maximize;                 // By default, is true; otherwise: minimize.
    private final int useRestarts;                  // If 0, no restarts are used.

    // === Randomness ===
    private final Random randomInstance;

    /**
     * Full constructor; takes both parameters.
     */
    protected AbstractLocalSearch(boolean maximize, int restarts) {
        // Initialize fields.
        this.maximize = maximize;
        this.useRestarts = restarts;

        randomInstance = new Random();
    }

    /** Defaults: no restarts. */
    protected AbstractLocalSearch(boolean maximize) {
        this(maximize, 0);
    }

    /** Defaults: maximise. */
    protected AbstractLocalSearch(int restarts) {
        this(true, restarts);
    }

    /** Defaults: maximize, no restarts. */
    protected AbstractLocalSearch() {
        this(true, 0);
    }

    /**
     * Wrap the main "search method" such that it can be computed at execution time.
     */
    @Override
    public S search(AbstractLocalProblem<S, A> problem) {
        // TODO: Implement statistical information
        return doSearch(problem);
    }

    /**
     * Let's implement the "Steepest Ascent" algorithm:
     *      Ascent      => Maximise the value of neighbors.
     *      Descent     => Minimize the value of neighbors.
     */
    private S doSearch(AbstractLocalProblem<S, A> problem) {

        // Initial problem state.
        S initialState = problem.getInitialState();

        // Re-use the same variable (notice that: the evaluation of a state depends on the kind of problem).
        LocalSearchNode<S> currentNode = LocalSearchNode.createNode(initialState, problem.evaluate(initialState));

        // Continue until a local optimum is not found.
        while (true) {

            // Extension point 1: each algorithm decides how to pick the next state.
            // For example:
            //  - Steepest ascent   -> Choose the one with a higher value;
            //  - FCHC              -> Choose the one with a higher or equal value.
            LocalSearchNode<S> nextNode = selectNeighbor(currentNode, problem);

            // No better neighbor have been found: local optimum.
            if (nextNode == null)
                return currentNode.getState();
            else
                // Otherwise, continue with the new neighbor
                currentNode = nextNode;
        }
    }

    // === TEMPLATE METHOD Steps (methods to be implemented) ===

    /**
     * Extension point to select the next neighbor based on the policy adopted by the (specific) search algorithm.
     * By default, it returns null.
     */
    protected LocalSearchNode<S> selectNeighbor(LocalSearchNode<S> currentNode, AbstractLocalProblem<S, A> problem) {
        // Default behavior: return null.
        return null;
    }


    // === UTILITY METHODS ===

    /**
     * Compare two values according the optimization direction.
     */
    protected boolean isBetter(double a, double b) {
        return maximize ? a > b : a < b;
    }

    // === AUXILIARY METHODS ===

    /**
     * Know if the search algorithm maximizes or minimizes.
     */
    public boolean isMaximize() { return  maximize; }

    /**
     * If greater than 0, restarts are used.
     */
    protected boolean isUseRestarts() {
        return useRestarts > 0;
    }

    /**
     * Get the Random instance field.
     */
    protected Random getRandomInstance() {
        return randomInstance;
    }
}

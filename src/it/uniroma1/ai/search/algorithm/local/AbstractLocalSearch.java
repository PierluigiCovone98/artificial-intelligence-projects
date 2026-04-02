package it.uniroma1.ai.search.algorithm.local;

import it.uniroma1.ai.search.algorithm.SearchAlgorithm;
import it.uniroma1.ai.search.node.LocalSearchNode;
import it.uniroma1.ai.search.problem.AbstractLocalProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract base for local search algorithms.
 * Subclasses implement specific strategies (Steepest Ascent, Hill Climbing, Simulated Annealing, etc.).
 */
public abstract class AbstractLocalSearch<S, A>
        implements SearchAlgorithm< AbstractLocalProblem<S, A> , S > {

    private final boolean maximize;                 // By default, maximize; otherwise: minimize.
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
     * Wrap the main "search method" such that it can be computed execution time.
     */
    @Override
    public S search(AbstractLocalProblem<S, A> problem) {
        return doSearch(problem);
    }

    /**
     * Let's implement the "Steepest Ascent" algorithm:
     *      Ascent => Maximise the value of neighbors.
     */
    private S doSearch(AbstractLocalProblem<S, A> problem) {

        // Initial problem state.
        S initialState = problem.getInitialState();

        // Re-use the same variable.
        LocalSearchNode<S> currentNode = LocalSearchNode.createNode(initialState, problem.evaluate(initialState));

        while (true) {

            // 1. Call it once.
            S currentState = currentNode.getState();
            double currentValue = currentNode.getValue().doubleValue();

            // 2. We first need all possible neighbors
            List<S> neighbors = problem.getActions(currentState)
                    .stream()
                    .map(move -> problem.getResult(currentState, move))
                    .toList();

            // 3. Choose between "best" neighbors.
            // Extension point: each algorithm decides how to pick the next state.
            LocalSearchNode<S> nextNode = selectNeighbor(neighbors, currentNode, problem);

            if (nextNode == null)
                // More than one reason to return null
                return currentState;
            else
                currentNode = nextNode;
        }
    }

    // === TEMPLATE METHOD Steps (methods to be implemented) ===

    /**
     * Extension point to select the next neighbor based on the policy adopted by the search algorithm.
     * By default, it returns null.
     */
    protected LocalSearchNode<S> selectNeighbor(List<S> neighbors, LocalSearchNode<S> currentNode, AbstractLocalProblem<S, A> problem) {
        // Default behavior: return null.
        return null;
    }


    // === UTILITY METHODS ===

    /**
     * Compare two values according the optimization direction.
     * Please notice that:
     *  - "a" is the "current value" and
     *  - "b" is the "best value"
     */
    private boolean isBetter(double a, double b) {
        return maximize ? a < b : a > b;
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



}

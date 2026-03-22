package it.uniroma1.ai.statesearch.search;

import it.uniroma1.ai.statesearch.node.Node;
import it.uniroma1.ai.statesearch.frontier.Frontier;
import it.uniroma1.ai.statesearch.problem.AbstractProblem;

import java.util.*;

/**
 * Implements a search algorithm based on:
 *  1. The kind of used Frontier;
 *  2. If it has (or not) to save explored states.
 */
public abstract class AbstractSearchAlgorithm<S, A> {

    private final Frontier<S, A> frontier;
    private final boolean useExploredSet;       // Remember visited states?

    // === ALGORITHM INFORMATION & STATISTICS ===
    private final String algorithmName;
    private int iterations;                     // 0 by default
    private int maxFrontierSize;                // 0 by default
    private long executionTimeNS;


    /**
     * Constructor.
     * From the outside, I cannot create an instance of "AbstractSearchAlgorithm";
     * for this reason, when the "search()" method is invoked, the "extension points"
     * that are invoked are those one that are overridden in the specific subclass (if any).
     */
    protected AbstractSearchAlgorithm(Frontier<S, A> frontier, boolean useExploredSet, String algorithmName) {
        this.frontier = frontier;
        this.useExploredSet = useExploredSet;

        // Initialize Algorithm Information & Statistics
        this.algorithmName = algorithmName;
    }

    /**
     * Wrap the main "search method" such that it can be computed execution time.
     */
    public Node<S, A> search(AbstractProblem<S, A> problem) {
        // Reset statistics
        resetStatistics();

        // Initial time
        long startTime = System.nanoTime();

        // Actual search
        Node<S, A> searchResult = doSearch(problem);

        // Compute the execution time
        executionTimeNS = System.nanoTime() - startTime;

        return searchResult;
    }

    /**
     * Search the node that contains the objective state.
     * This is an implementation of the "Template Method" pattern:
     *  pieces of code that are common for every sub-implementation, remains in the (main) "search" method;
     *  those steps that are specific to a particular "search policy", are implemented in their own subclass.
     */
    private Node<S, A> doSearch(AbstractProblem<S, A> problem) {

        // Eventually prepare the explored data structure.
        Set<S> explored = null;

        if (useExploredSet) {
            explored = new HashSet<>();
        }

        Node<S, A> root = Node.createRoot(problem.getInitialState());

        // Check if the initial state of the problem is the objective one.
        if (problem.goalTest(problem.getInitialState()))
            return root;

        // If not, add the root to the frontier.
        frontier.add(root);

        // Start searching...
        while (!frontier.isEmpty()) {

            // UPDATE STATISTICS
            iterations++;

            Node<S, A> currentNode = frontier.remove();

            // Add it to the explored ones (if defined)
            if (useExploredSet)
                explored.add( currentNode.getState() );

            // STEP 1: Check if the current Node is the Objective one [min-cost,]
            Node<S, A> solution = onNodeExtracted(problem, currentNode);
            if (solution != null) {
                return solution;
            }

            // For each possible action reachable from the "currentNode.state"
            for (A action : problem.getActions(currentNode.getState())) {

                // 1. Create the child node
                S childState = problem.getResult(currentNode.getState(), action);
                double childStepCost = problem.getStepCost(currentNode.getState(), action).doubleValue();

                Node<S, A> childNode = Node.createChild(currentNode, childState, action, childStepCost);

                // Check the following:
                //  1. If we use or not use the explored set, we enter the first if-statement;
                //  2. Then, if the frontier:
                //      2.1. do not contain a node that has the same (current) "childState",
                //           extend if required and then, add the child node to the frontier (if not returned).
                //      2.2. contains a node that has the same (current) "childState", it could do something...
                if ( !useExploredSet || !explored.contains(childState) ) {
                    if (!frontier.containsState(childState)) {

                        // STEP 2: Optimization for algorithms like: [BFS, DFS, ...] .
                        solution = handleChild(problem, childNode);
                        if (solution != null) {
                            return solution;
                        }

                        // Add childNode to the frontier according to the specific policy.
                        frontier.add(childNode);

                    } else {

                        // STEP 3: Swap nodes in frontier if applicable[min-cost,].
                        //         Notice that "solution" is ignored in all cases.
                        swapNodes(childNode);

                    }
                }
            } // for

            // Update the max frontier size
            int currentFrontierSize = frontier.size();
            if (maxFrontierSize - currentFrontierSize < 0)
                maxFrontierSize = currentFrontierSize;

        } // while

        return null;
    }


    // === TEMPLATE METHOD Steps (methods to be implemented) ===

    /**
     * Extension point to check if the current extracted node contains an objective state.
     * By default, it returns null.
     */
    protected Node<S, A> onNodeExtracted(AbstractProblem<S, A> problem, Node<S, A> node) {
        // Default behavior: return null.
        return null;
    }

    /**
     * Extension point that allows to manage a child node, based on the used "search policy".
     */
    protected Node<S, A> handleChild(AbstractProblem<S, A> problem, Node<S, A> childNode) {
        // Default behavior: return null.
        return null;
    }

    /**
     * Extension point that allows to substitute the given Node with the correct one in frontier.
     */
    protected void swapNodes(Node<S, A> node) {
        // Default behavior: do nothing.
    }


    // === AUXILIARY METHODS ===

    /**
     * Only subclasses can get the "Frontier" instance.
     */
    protected Frontier<S, A> getFrontier() {
        return frontier;
    }

    /**
     * Only subclasses can get the "useExploredSet" instance.
     */
    protected boolean isUseExploredSet() {
        return useExploredSet;
    }

    /**
     * Get the algorithm name.
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Get the number of iterations.
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Get max frontier size.
     */
    public int getMaxFrontierSize() {
        return maxFrontierSize;
    }

    /**
     * Get the execution time in ms.
     */
    public long getExecutionTimeNs() {
        return executionTimeNS;
    }

    /**
     * Reset statistics if the same instance of the search algorithm is invoked more than once.
     */
    private void resetStatistics() {
        iterations = 0;
        maxFrontierSize = 0;
    }
}

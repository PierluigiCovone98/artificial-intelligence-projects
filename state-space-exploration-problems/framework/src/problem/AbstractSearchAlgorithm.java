package problem;

import java.util.*;

/**
 * Implements a search algorithm based on:
 *  1. The kind of used Frontier;
 *  2. If it has (or not) to save explored states.
 */
public abstract class AbstractSearchAlgorithm<S, A> {

    private final Frontier<S, A> frontier;
    private final boolean useExploredSet;

    /**
     * Constructor.
     */
    public AbstractSearchAlgorithm(Frontier<S, A> frontier, boolean useExploredSet) {
        this.frontier = frontier;
        this.useExploredSet = useExploredSet;
    }

    /**
     * Search the node that contains the objective state.
     * This is an implementation of the "Template Method" pattern:
     *  pieces of code that are common for every sub-implementation,
     *  remains in the (main) "search" method;
     *  those steps that are specific to a particular "search policy",
     *  are implemented in their own subclass.
     */
    public Node<S, A> search(AbstractProblem<S, A> problem) {

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

                // STEP 2 HERE: The actual "search policy" that differs from one algorithm to another.
                // Optimization for algorithms like: BFS, DFS, ... .
                // Notice that, at this point, we do not visit the child node
                // (we do not add it to the explored set).
                solution = handleChild(problem, childNode, explored);
                if (solution != null) {
                    return solution;
                }
            }
        }

        return null;
    }

    /**
     * Extension point to check if the current extracted node contains an objective state.
     * By default, it returns null.
     */
    protected Node<S, A> onNodeExtracted(AbstractProblem<S, A> problem, Node<S, A> node) {
        // Default behavior: return null
        return null;
    }

    /**
     * Extension point that allows to manage a child node, based on the used "search policy".
     */
    protected Node<S, A> handleChild(AbstractProblem<S, A> problem, Node<S, A> childNode, Set<S> explored) {
        // Default behavior: return null
        return null;
    }

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
}

package problem;

import java.util.*;

/**
 * Implements a search algorithm based on:
 *  1. The kind of used Frontier;
 *  2. If it has (or not) to save explored states.
 */
public class SearchAlgorithm<S, A> {

    private final Frontier<S, A> frontier;
    private final boolean useExploredSet;

    /**
     * Constructor.
     */
    public SearchAlgorithm(Frontier<S, A> frontier, boolean useExploredSet) {
        this.frontier = frontier;
        this.useExploredSet = useExploredSet;
    }

    /**
     * Search the node that contains the objective state.
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

            Node<S, A> node = frontier.remove();

            // Add it to the explored ones (if defined)
            if (useExploredSet)
                explored.add( node.getState() );

            // For each possible action reachable from the "node.state"
            for (A action : problem.getActions(node.getState())) {

                // 1. Create the child node
                S childState = problem.getResult(node.getState(), action);
                double childStepCost = (double) problem.getStepCost(node.getState(), action);

                Node<S, A> childNode = Node.createChild(node, childState, action, childStepCost);

                // 2. Check for redundant paths and state repetition:

                // Notice that:
                //  (a) If we use "explored", then the first part of the OR clause
                //      is evaluated ad false; then we check if the state is in the
                //      explored collection;
                //  (b) Otherwise, the OR clause is evaluated as true and that's ok.
                boolean isNew = !frontier.containsState(childState) &&
                        (!useExploredSet || !explored.contains(childState));

                if (isNew) {
                    if (problem.goalTest(childState))
                        return childNode;
                    else
                        frontier.add(childNode);
                }
            }
        }

        return null;
    }


}

package problem;

import java.util.Set;

public class BFSSearch<S, A> extends AbstractSearchAlgorithm<S, A> {


    /**
     * Constructor.
     */
    public BFSSearch(Frontier<S, A> frontier, boolean useExploredSet) {
        super(frontier, useExploredSet);
    }

    /**
     * Check if the child node has a state that is the objective.
     * This is an optimization for the BFS search algorithm,
     * because if a child state is the objective state,
     * the solution represents the optimal one (Not really sure).
     */
    @Override
    protected Node<S, A> handleChild(AbstractProblem<S, A> problem, Node<S, A> node, Set<S> explored) {

        // Save the child state
        S childState = node.getState();

        // Check the following:
        //  1. If the child node is not in "frontier" AND
        //  2. If the "useExploredSet" is:
        //      2.1. If "False", that's amazing.
        //      2.2. If "True", then check if the state is not in the explored set
        if ( !getFrontier().containsState(childState)
                &&
                (!isUseExploredSet() || !explored.contains(childState)) ) {
            if (problem.goalTest(childState))
                return node;

        }

        return null;

    }

}

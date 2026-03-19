package statesearch.search;

import statesearch.frontier.LifoFrontier;
import statesearch.node.Node;
import statesearch.problem.AbstractProblem;

/**
 * Depth First Search algorithm implementation.
 */
public class DFSearch<S, A> extends AbstractSearchAlgorithm<S, A> {

    /**
     * Constructor.
     */
    public DFSearch(LifoFrontier<S, A> frontier, boolean useExploredSet) {
        super(frontier, useExploredSet);
    }

    /**
     * Check if the child node has a state that is the objective (optimization for the DFS algorithm).
     * If not, return null.
     */
    @Override
    protected Node<S, A> handleChild(AbstractProblem<S, A> problem, Node<S, A> node) {

        if (problem.goalTest(node.getState()))
            return node;

        return null;
    }
}

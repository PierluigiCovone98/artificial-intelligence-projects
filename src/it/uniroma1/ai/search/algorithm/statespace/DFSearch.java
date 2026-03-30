package it.uniroma1.ai.search.algorithm.statespace;

import it.uniroma1.ai.search.frontier.LifoFrontier;
import it.uniroma1.ai.search.node.Node;
import it.uniroma1.ai.search.problem.AbstractStateSpaceProblem;

/**
 * Depth First Search algorithm implementation.
 */
public class DFSearch<S, A> extends AbstractStateSpaceSearch<S, A> {

    /**
     * Constructor.
     */
    public DFSearch(boolean useExploredSet) {
        super(new LifoFrontier<S, A>(), useExploredSet, "DFS");
    }

    /**
     * Check if the child node has a state that is the objective (optimization for the DFS algorithm).
     * If not, return null.
     */
    @Override
    protected Node<S, A> handleChild(AbstractStateSpaceProblem<S, A> problem, Node<S, A> node) {

        if (problem.goalTest(node.getState()))
            return node;

        return null;
    }
}

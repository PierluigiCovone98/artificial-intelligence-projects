package it.uniroma1.ai.search.algorithm.statespace;

import it.uniroma1.ai.search.node.StateSpaceSearchNode;
import it.uniroma1.ai.search.frontier.FifoFrontier;
import it.uniroma1.ai.search.problem.AbstractStateSpaceProblem;

/**
 * In the BFS algorithm, we have two possible scenarios:
 *  a. The step-cost is constants;
 *      => The returned solution is guaranteed to be the optimal one.
 *  b. The step-cost is not constant;
 *      => The returned solution is NOT guaranteed be the optimal one.
 */
public class BFSearch<S, A> extends AbstractStateSpaceSearch<S, A> {

    /**
     * Constructor.
     */
    public BFSearch( boolean useExploredSet) {
        super(new FifoFrontier<>(), useExploredSet, "BFS");
    }

    /**
     * Check if the child node has a state that is the objective (optimization for the BFS algorithm).
     * If not, return null.
     */
    @Override
    protected StateSpaceSearchNode<S, A> handleChild(AbstractStateSpaceProblem<S, A> problem,
                                                     StateSpaceSearchNode<S, A> node) {

        if (problem.goalTest(node.getState()))
            return node;

        return null;
    }

}

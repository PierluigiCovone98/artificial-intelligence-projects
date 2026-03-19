package statesearch.search;

import statesearch.node.Node;
import statesearch.frontier.FifoFrontier;
import statesearch.problem.AbstractProblem;

/**
 * In the BFS algorithm, we have two possible scenarios:
 *  a. The step-cost is constants;
 *      => The returned solution is guaranteed to be the optimal one.
 *  b. The step-cost is not constant;
 *      => The returned solution is NOT guaranteed be the optimal one.
 */
public class BFSearch<S, A> extends AbstractSearchAlgorithm<S, A> {

    /**
     * Constructor.
     */
    public BFSearch( boolean useExploredSet) {
        super(new FifoFrontier<>(), useExploredSet);
    }

    /**
     * Check if the child node has a state that is the objective (optimization for the BFS algorithm).
     * If not, return null.
     */
    @Override
    protected Node<S, A> handleChild(AbstractProblem<S, A> problem, Node<S, A> node) {

        if (problem.goalTest(node.getState()))
            return node;

        return null;
    }

}

package it.uniroma1.ai.search.algorithm.statespace;

import it.uniroma1.ai.search.frontier.NodeComparators;
import it.uniroma1.ai.search.frontier.PriorityFrontier;
import it.uniroma1.ai.search.node.StateSpaceSearchNode;
import it.uniroma1.ai.search.problem.AbstractStateSpaceProblem;

/**
 * Best First Greedy search algorithm implementation.
 * Notice that here it has no sense the "swap" operation due to the
 * fact that two nodes with the same state have the same value for h.
 */
public class BestFirstGreedySearch<S, A> extends AbstractStateSpaceSearch<S, A> {

    /**
     * Constructor.
     */
    public BestFirstGreedySearch(Heuristic<S> h, boolean useExploredSet) {
        super(new PriorityFrontier<>(NodeComparators.byHeuristic(h) ),
                useExploredSet,
                "Best First Greedy Search");
    }

    /**
     * Goal test at extraction: optimality is not guaranteed.
     */
    @Override
    protected StateSpaceSearchNode<S, A> onNodeExtracted(AbstractStateSpaceProblem<S, A> problem,
                                                         StateSpaceSearchNode<S, A> node) {

        // Check if the node contains an objective State
        if ( problem.goalTest(node.getState()) )
            return node;

        return null;
    }
}

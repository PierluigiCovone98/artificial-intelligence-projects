package it.uniroma1.ai.statesearch.search;

import it.uniroma1.ai.statesearch.frontier.NodeComparators;
import it.uniroma1.ai.statesearch.frontier.PriorityFrontier;
import it.uniroma1.ai.statesearch.node.Node;
import it.uniroma1.ai.statesearch.problem.AbstractProblem;

/**
 * Best First Greedy search algorithm implementation.
 * Notice that here it has no sense the "swap" operation due to the
 * fact that two nodes with the same state have the same value for h.
 */
public class BestFirstGreedySearch<S, A> extends AbstractSearchAlgorithm<S, A> {

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
    protected Node<S, A> onNodeExtracted(AbstractProblem<S, A> problem, Node<S, A> node) {

        // Check if the node contains an objective State
        if ( problem.goalTest(node.getState()) )
            return node;

        return null;
    }
}

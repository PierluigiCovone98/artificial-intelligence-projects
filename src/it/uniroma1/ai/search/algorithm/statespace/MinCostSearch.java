package it.uniroma1.ai.search.algorithm.statespace;

import it.uniroma1.ai.search.frontier.NodeComparators;
import it.uniroma1.ai.search.frontier.PriorityFrontier;
import it.uniroma1.ai.search.node.StateSpaceSearchNode;
import it.uniroma1.ai.search.problem.AbstractStateSpaceProblem;

/**
 * Min Cost Search algorithm.
 */
public class MinCostSearch<S, A> extends AbstractStateSpaceSearch<S, A> {

    /**
     * Constructor.
     */
    public MinCostSearch(boolean useExploredSet) {
        super(new PriorityFrontier<>(NodeComparators.byPathCost()),
                useExploredSet,
                "Min Cost Search");
    }

    /**
     * Goal test at extraction: guarantees optimality.
     */
    @Override
    protected StateSpaceSearchNode<S, A> onNodeExtracted(AbstractStateSpaceProblem<S, A> problem,
                                                         StateSpaceSearchNode<S, A> node) {

        // Check if the node contains an objective State
        if ( problem.goalTest(node.getState()) )
            return node;

        return null;
    }

    /**
     * Replace the node in frontier if the new one has a lower path cost.
     */
    @Override
    protected void swapNodes(StateSpaceSearchNode<S, A> node) {

        // 1. Save the frontier as a PriorityFrontier.
        PriorityFrontier<S, A> frontier = (PriorityFrontier<S, A>) getFrontier();

        // 2. Get the node that has the same "node.state".
        //    This could be null, but we know that, at this point it isn't.
        StateSpaceSearchNode<S, A> oldNode = frontier.getNode(node.getState());

        // 3. Swap nodes
        if ( (oldNode != null)
                && (node.getPathCost() < oldNode.getPathCost()) ) {
                frontier.removeNode(oldNode);
                frontier.add(node);
        }
    }

}

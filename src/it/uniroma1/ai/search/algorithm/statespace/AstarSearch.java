package it.uniroma1.ai.search.algorithm.statespace;

import it.uniroma1.ai.search.frontier.NodeComparators;
import it.uniroma1.ai.search.frontier.PriorityFrontier;
import it.uniroma1.ai.search.node.StateSpaceSearchNode;
import it.uniroma1.ai.search.problem.AbstractStateSpaceProblem;

/**
 * A* search algorithm implementation.
 * Notice that here it is meaningful the swap operation but,
 * because two nodes with the same state S have the same value for "h(n)",
 * the only value that differs in
 *                      f(n) = g(n) + h(n)
 * is the "g" cost.
 */
public class AstarSearch<S, A> extends AbstractStateSpaceSearch<S, A> {

    /**
     * Constructor.
     */
    public AstarSearch(Heuristic<S> h, boolean useExploredSet) {
        super(new PriorityFrontier<>( NodeComparators.byFFunction(h) ),
                useExploredSet,
                "A*");
    }

    /**
     * Goal test at extraction.
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
     * Replace the node in frontier if the new one has a lower path cost (f).
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

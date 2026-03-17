package statesearch.search;

import statesearch.frontier.PriorityFrontier;
import statesearch.node.Node;
import statesearch.problem.AbstractProblem;

/**
 * Min Cost Search algorithm.
 */
public class MinCostSearch<S, A> extends AbstractSearchAlgorithm<S, A> {

    /**
     * Constructor.
     */
    public MinCostSearch(PriorityFrontier<S, A> frontier, boolean useExploredSet) {
        super(frontier, useExploredSet);
    }


    /**
     * Goal test at extraction: guarantees optimality.
     */
    @Override
    protected Node<S, A> onNodeExtracted(AbstractProblem<S, A> problem, Node<S, A> node) {

        // Check if the node contains an objective State
        if ( problem.goalTest(node.getState()) )
            return node;

        return null;
    }

    /**
     * Replace the node in frontier if the new one has a lower path cost.
     */
    @Override
    protected Node<S, A> swapNodes(Node<S, A> node) {

        // 1. Save the frontier as a PriorityFrontier.
        PriorityFrontier<S, A> frontier = (PriorityFrontier<S, A>) getFrontier();

        // 2. Get the node that has the same "node.state".
        //    This could be null, but we know that, at this point it isn't.
        Node<S, A> oldNode = frontier.getNode(node.getState());

        // 3. Swap nodes
        if ( (oldNode != null)
                && (node.getPathCost() < oldNode.getPathCost()) ) {
                frontier.removeNode(oldNode);
                frontier.add(node);

                return oldNode;
        }

        return null;
    }

}

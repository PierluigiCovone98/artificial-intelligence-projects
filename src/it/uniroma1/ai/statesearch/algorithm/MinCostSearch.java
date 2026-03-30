package it.uniroma1.ai.statesearch.algorithm;

import it.uniroma1.ai.statesearch.frontier.NodeComparators;
import it.uniroma1.ai.statesearch.frontier.PriorityFrontier;
import it.uniroma1.ai.statesearch.node.Node;
import it.uniroma1.ai.statesearch.problem.AbstractProblem;

/**
 * Min Cost Search algorithm.
 */
public class MinCostSearch<S, A> extends AbstractSearchAlgorithm<S, A> {

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
    protected void swapNodes(Node<S, A> node) {

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
        }
    }

}

package it.uniroma1.ai.statesearch.frontier;

import it.uniroma1.ai.statesearch.node.Node;
import it.uniroma1.ai.statesearch.algorithm.Heuristic;

import java.util.Comparator;


/**
 * Factory methods for Node comparators used by PriorityFrontier.
 */
public class NodeComparators {

    /**
     * Prevent instantiation.
     */
    private NodeComparators() {
        // empty
    }


    /**
     * Compare nodes by path cost (ascending order).
     */
    public static <S, A> Comparator< Node<S, A> > byPathCost() {
        return Comparator.comparingDouble(Node::getPathCost);
    }

    /**
     * Compare nodes by heuristic "h" (ascending order).
     * It takes in input a specific instance of the functional interface "Heuristic" and,
     * based on that, it compares the state of the node.
     */
    public static <S, A> Comparator< Node<S, A> > byHeuristic(Heuristic<S> h) {
        return Comparator.comparingDouble( n ->  h.estimate(n.getState()) );
    }

    /**
     * Compare nodes by function f (ascending order), such that:
     *                  f(n) := g(n) + h(n)
     * where:
     *      1) g(n) denotes the pathCost of the node "n";
     *      2) h(n) denotes the heuristic valued for the node "n";
     */
    public static <S, A> Comparator< Node<S, A> > byFFunction(Heuristic<S> h) {
        return Comparator.comparingDouble( n ->  n.getPathCost() + h.estimate(n.getState()) );
    }

}

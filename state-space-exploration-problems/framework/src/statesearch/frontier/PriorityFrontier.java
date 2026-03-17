package statesearch.frontier;

import statesearch.node.Node;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Priority-based frontier.
 * Nodes are ordered according to the provided comparator.
 * Exposes additional methods (getNode, removeNode) to support node replacement.
 */
public class PriorityFrontier<S, A> implements Frontier<S, A> {

    private final PriorityQueue< Node<S, A> > frontier;

    /**
     * Constructor.
     * The given comparator determines the extraction order.
     */
    public PriorityFrontier(Comparator< Node<S, A> > nodeComparator) {
        this.frontier = new PriorityQueue<>(nodeComparator);
    }

    /**
     * Add a node to the priority frontier.
     */
    @Override
    public void add(Node<S, A> node) {
        this.frontier.add(node);
    }

    /**
     * Extract the highest-priority node (lowest value according to comparator).
     */
    @Override
    public Node<S, A> remove() {
        return this.frontier.poll();
    }

    /**
     * Check if the priority frontier is empty.
     */
    @Override
    public boolean isEmpty() {
        return this.frontier.isEmpty();
    }

    /**
     * Check if the frontier is empty.
     */
    @Override
    public boolean containsState(S state) {
        return this.frontier.stream()
                             .anyMatch( n -> n.getState().equals(state) );
    }

    /**
     * Get the Node in frontier that has "state" as "node.state" value, null otherwise.
     */
    public Node<S, A> getNode(S state) {
        return this.frontier.stream()
                            .filter(n -> n.getState().equals(state))
                            .findFirst()
                            .orElse(null);
    }

    /**
     * Removes a specific node from the frontier.
     */
    public boolean removeNode(Node<S, A> node) {
        return this.frontier.remove(node);
    }
}

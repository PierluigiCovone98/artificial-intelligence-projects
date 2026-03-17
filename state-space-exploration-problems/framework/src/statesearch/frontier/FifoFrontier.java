package statesearch.frontier;

import statesearch.node.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Wrap a FIFO frontier and exposes methods to interact with it.
 */
public class FifoFrontier<S, A> implements Frontier<S, A> {

    private final Queue<Node<S, A>> frontier;

    /**
     * Constructor.
      */
    public FifoFrontier() {
        this.frontier = new LinkedList<>();
    }

    /**
     * Add a node to the frontier.
     */
    @Override
    public void add(Node<S, A> node) {
        frontier.add(node);
    }

    /**
     * Removes the first element of the queue.
     */
    @Override
    public Node<S, A> remove() {
        return frontier.poll();
    }

    /**
     * Check if the queue is empty.
     */
    @Override
    public boolean isEmpty() {
        return frontier.isEmpty();
    }

    /**
     * Check if the queue contains a specific state.
     */
    @Override
    public boolean containsState(S state) {
        return frontier.stream().anyMatch(n -> n.getState().equals(state));
    }
}

package it.uniroma1.ai.search.frontier;

import it.uniroma1.ai.search.node.Node;

import java.util.Deque;
import java.util.ArrayDeque;


/**
 * Wrap a Lifo frontier and exposes methods to interact with it.
 */
public class LifoFrontier<S, A> implements Frontier<S, A> {

    private final Deque<Node<S, A>> frontier;

    /**
     * Constructor.
     */
    public LifoFrontier() {
        this.frontier = new ArrayDeque<>();
    }

    /**
     * Add a node to the frontier.
     */
    @Override
    public void add(Node<S, A> node) {
        frontier.push(node);
    }

    /**
     * Removes the first element of the deque.
     */
    @Override
    public Node<S, A> remove() {
        return frontier.pop();
    }

    /**
     * Check if the deque is empty.
     */
    @Override
    public boolean isEmpty() {
        return frontier.isEmpty();
    }

    /**
     * Check if the deque contains a specific state.
     */
    @Override
    public boolean containsState(S state) {
        return frontier.stream().anyMatch(n -> n.getState().equals(state));
    }

    /**
     * Get the dequeue size.
     */
    @Override
    public int size() {
        return frontier.size();
    }
}

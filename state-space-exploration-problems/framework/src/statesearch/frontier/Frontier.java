package statesearch.frontier;

import statesearch.node.Node;

/**
 * Minimal (required) contract to interact with a custom frontier.
 */
public interface Frontier<S, A> {

    /**
     * Add a node to the underlining collection.
     */
    void add(Node<S, A> node);

    /**
     * Remove a node from the underlining collection.
     */
    Node<S, A> remove();

    /**
     * Check if the underlining collection is empty.
     */
    boolean isEmpty();

    /**
     * Check if the underlining collection contains a specific state.
     */
    boolean containsState(S state);
}

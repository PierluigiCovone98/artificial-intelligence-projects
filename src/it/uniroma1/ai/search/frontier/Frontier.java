package it.uniroma1.ai.search.frontier;

import it.uniroma1.ai.search.node.StateSpaceSearchNode;

/**
 * Minimal (required) contract to interact with a custom frontier.
 */
public interface Frontier<S, A> {

    /**
     * Add a node to the underlining collection.
     */
    void add(StateSpaceSearchNode<S, A> node);

    /**
     * Remove a node from the underlining collection.
     */
    StateSpaceSearchNode<S, A> remove();

    /**
     * Check if the underlining collection is empty.
     */
    boolean isEmpty();

    /**
     * Check if the underlining collection contains a specific state.
     */
    boolean containsState(S state);

    // === Methods used for statistics ===

    /**
     * Get the size of the frontier.
     */
    int size();
}

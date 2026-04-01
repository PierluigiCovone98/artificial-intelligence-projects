package it.uniroma1.ai.search.node;

/**
 * Generic class to represent a Node in the Local Search family of algorithms.
 */
public class LocalSearchNode<S> {

    private S state;
    private Number value;

    /**
     * Private constructor does not allow wrong initializations from the outside.
     */
    private LocalSearchNode() {
        // empty
    }

    /**
     * Factory method to create a node.
     */
    public static <S> LocalSearchNode<S> createNode(S state, Number value) {
        LocalSearchNode<S> node = new LocalSearchNode<>();

        node.state = state;
        node.value = value;

        return node;
    }

    /**
     * Get the state.
     */
    public S getState() { return state; }

    /**
     * Get the value.
     */
    public Number getValue() { return value; }




}

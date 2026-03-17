package statesearch.node;

import java.util.Optional;

/**
 * Generic class to represent a Node in the Search Tree.
 */
public class Node<S, A> {

    private Node<S, A> parent;

    private S state;
    private A action;

    private int depth;
    private double pathCost;

    /**
     * Private constructor does not allow wrong initializations from the outside.
     */
    private Node () {
        // empty
    }

    /**
     * Factory method to create the root node.
     */
    public static <S, A> Node<S, A> createRoot(S state) {
        // 1. Create a node with default values
        Node<S, A> root = new Node<>();

        // 2. Setting fields
        root.parent = null;

        root.state = state;
        root.action = null;

        root.depth = 0;
        root.pathCost = 0;

        return root;
    }

    /**
     * Factory method to create a child.
     */
    public static <S, A> Node<S, A> createChild(Node<S, A> parent, S state, A action, double stepCost) {

        // 1. Create a node with default values
        Node<S, A> child = new Node<>();

        // 2. Setting fields
        child.parent = parent;

        child.state = state;
        child.action = action;

        child.depth = parent.getDepth() + 1;
        child.pathCost = parent.getPathCost() + stepCost;

        return child;
    }

    /**
     * Get parent.
     * Return an Optional instance if the parent is null.
     */
    public Optional< Node<S, A> > getParent() {
        return Optional.ofNullable(parent);
    }

    /**
     * Return the state.
     */
    public S getState() {
        return state;
    }

    /**
     * Get action.
     * Return an Optional instance if the acion is null.
     */
    public Optional<A> getAction() {
        return Optional.ofNullable(action);
    }

    /**
     * Get depth.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Get path cost.
     */
    public double getPathCost() {
        return pathCost;
    }
}

package it.uniroma1.ai.search.problem;

import java.util.List;

/**
 * Formulation of a generic search problem.
 */
public abstract class AbstractProblem<S, A> {

    // Common fields
    private final S initialState;

    /**
     * Constructor.
     * Initialize fields.
     */
    protected AbstractProblem(S initialState) {
        this.initialState = initialState;
    }

    /**
     * Return all possible actions (of type A) from a given state (of type S).
     */
    public abstract List<A> getActions(S state);

    /**
     * Return the result of applying an action (of type A) to a state (of type S).
     */
    public abstract S getResult(S state, A action);

    /**
     * Get initial state (of type S).
     */
    public S getInitialState() {
        return initialState;
    }

}
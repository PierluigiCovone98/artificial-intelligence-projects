package it.uniroma1.ai.statesearch.problem;

import java.util.List;

/**
 * Formulation of a generic "Space of State Exploration" problem.
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
     * Get the step cost for a given state (of type S) and the performed action (of type A).
     */
    public abstract Number getStepCost(S state, A action);

    /**
     * Get initial state (of type S).
     */
    public S getInitialState() {
        return initialState;
    }

    /**
     * Determine if the given state (of type S) satisfies the objective.
     */
    public abstract boolean goalTest(S state);

}
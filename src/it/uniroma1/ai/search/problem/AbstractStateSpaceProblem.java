package it.uniroma1.ai.search.problem;

public abstract class AbstractStateSpaceProblem<S, A> extends AbstractProblem<S, A> {

    /**
     * Constructor.
     */
    protected AbstractStateSpaceProblem(S initialState) {
        super(initialState);
    }

    /**
     * Get the step cost for a given state (of type S) and the performed action (of type A).
     */
    public abstract Number getStepCost(S state, A action);

    /**
     * Determine if the given state (of type S) satisfies the objective.
     */
    public abstract boolean goalTest(S state);
}

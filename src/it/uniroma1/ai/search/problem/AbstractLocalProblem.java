package it.uniroma1.ai.search.problem;

public abstract class AbstractLocalProblem<S, A> extends AbstractProblem<S, A> {

    /**
     * Constructor.
     */
    public AbstractLocalProblem(S initialState) {
        super(initialState);
    }

    /**
     * Evaluate the quality of a given state.
     * Higher values, indicate better states for maximization problems;
     * lower values, for minimization problems.
     */
    public abstract Number evaluate(S state);
}

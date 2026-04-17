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
     * The sub-problem must override it, and then specify
     * the way a certain state (for that problem) is evaluated.
     */
    public abstract Number evaluate(S state);
}

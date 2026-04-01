package it.uniroma1.ai.search.problem;

public abstract class AbstractLocalProblem<S, A> extends AbstractProblem<S, A> {

    /**
     * Constructor.
     */
    public AbstractLocalProblem(S initialState) {
        super(initialState);
    }
}

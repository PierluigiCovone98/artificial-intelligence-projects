package it.uniroma1.ai.search.algorithm.local;

import it.uniroma1.ai.search.algorithm.SearchAlgorithm;
import it.uniroma1.ai.search.problem.AbstractLocalProblem;

public abstract class AbstractLocalSearch<S, A>
        implements SearchAlgorithm< AbstractLocalProblem<S, A> , S > {

    private boolean useRestars;


    /**
     * Wrap the main "search method" such that it can be computed execution time.
     */
    @Override
    public S search(AbstractLocalProblem<S, A> problem) {
        return doSearch(problem);
    }


    private S doSearch(AbstractLocalProblem<S, A> problem) {
        return null;
    }


    // === AUXILIARY METHODS ===

    /**
     * Only subclasses can get the "useRestarts" value.
     */
    protected boolean isUseRestars() { return useRestars; }

}

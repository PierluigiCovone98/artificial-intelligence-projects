package it.uniroma1.ai.search.algorithm;

/**
 * Contract for any search algorithm that,
 * given a problem P, produces a result R.
 */
public interface SearchAlgorithm<P, R> {

    /**
     * Search for a solution R to the given problem P.
     */
    R search(P problem);

}

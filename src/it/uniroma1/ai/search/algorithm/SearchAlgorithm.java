package it.uniroma1.ai.search.algorithm;

/**
 * Contract for any search algorithm that,
 * given a problem P, produces a result R.
 */
public interface SearchAlgorithm<P, R> {

    R search(P problem);

}

package it.uniroma1.ai.search.algorithm.statespace;

/**
 * The heuristic is a simple function that return a cost for a given state.
 */
@FunctionalInterface
public interface Heuristic<S> {
    double estimate(S state);
}

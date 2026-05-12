package it.uniroma1.ai.search.algorithm.local;

import it.uniroma1.ai.search.node.LocalSearchNode;
import it.uniroma1.ai.search.problem.AbstractLocalProblem;

import java.util.*;

/**
 * Implementation of the "First Choice Hill Climbing" Algorithm.
 */
public class FirstChoiceHillClimbing<S, A> extends AbstractLocalSearch<S, A> {

    /**
     * Explicit constructor;
     * this is required because the default one calls the "default constructor",
     * that automatically set "maximise=true" and no restarts.
     */
    public FirstChoiceHillClimbing(boolean maximise, int restarts) {
        super(maximise, restarts);
    }

    /**
     * In First Choice Hill Climbing, the algorithm returns the first node that has a value
     * that is greater than or equal to "currentNode.value".
     */
    @Override
    protected LocalSearchNode<S> selectNeighbor(LocalSearchNode<S> currentNode, AbstractLocalProblem<S, A> problem) {

        // Useful information to keep hand-on
        S currentState = currentNode.getState();
        double valueCurrentNode = currentNode.getValue().doubleValue();

        // Now: if is there any state that has a better or equal value,
        // then returns the "related node". Notice that here it always
        // go to the neighbor if it has an equal value (and this can lead
        // to loops). To avoid it, we should introduce lateral moves
        // (simply a max number of times for which the lateral move is allowed).
        // We can say that by default it is: 100.
        List<A> moves = new ArrayList<>( problem.getActions( currentState ) );
        Collections.shuffle(moves, getRandomInstance());    // This makes the "First Choice", random

        Optional<S> betterNeighbor = moves.stream()
                .map(move -> problem.getResult(currentState, move))     // Here I have all the neighbors...
                .filter( neighbor -> {
                    // I want someone neighborState that is better or equal to "valueCurrentNode"
                    double valueNeighbor = problem.evaluate(neighbor).doubleValue();

                    /*
                    Here we should check:
                    if (valueCurrentNode==valueNeighbor)
                        decrease the number of actual lateral moves
                        (because we want "allowed lateral moves" and "actual lateral moves"

                     Notice that we should check somewhere if there are other lateral moves.
                     */
                    return valueCurrentNode==valueNeighbor || isBetter(valueNeighbor, valueCurrentNode);
                }
                ).findFirst();


        // If no betterNeighbors, we are in a local optimal
        if (betterNeighbor.isEmpty())
            return null;

        S neighborState = betterNeighbor.get();
        return LocalSearchNode.createNode(neighborState, problem.evaluate(neighborState));
    }

}

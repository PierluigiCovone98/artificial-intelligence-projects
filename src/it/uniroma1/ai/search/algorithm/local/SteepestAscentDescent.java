package it.uniroma1.ai.search.algorithm.local;

import it.uniroma1.ai.search.node.LocalSearchNode;
import it.uniroma1.ai.search.problem.AbstractLocalProblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Steepest Ascent/Descent Algorithm.
 */
public class SteepestAscentDescent<S, A> extends AbstractLocalSearch<S,A> {

    /**
     * Constructor with argument.
     * This is required because if not implemented, the default one is considered.
     * And the default constructor in the super class, create an instance that
     * maximise and has no restarts.
     */
    public SteepestAscentDescent(boolean maximise, int restarts) {
        super(maximise, restarts);
    }

    /**
     * In Steepest Ascent/Descent the algorithm explores the neighbor iff
     * it is evaluated with a "value" that is strictly better than "valueCurrentNode".
     */
    @Override
    protected LocalSearchNode<S> selectNeighbor(LocalSearchNode<S> currentNode, AbstractLocalProblem<S, A> problem) {

        S currentState = currentNode.getState();
        double valueCurrentNode = currentNode.getValue().doubleValue();

        // See all neighbors og the "currentNode.state"
        List<S> neighbors = problem.getActions(currentState)
                .stream()
                .map(move -> problem.getResult(currentState, move))
                .toList();


        // These are neighbors that have the "best value".
        List<S> bestNeighbors = new ArrayList<>();


        double bestValue = isMaximize() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

        // First we choose only those neighbors that have the "best value"
        for (S n : neighbors) {

            double valueNeighbor = problem.evaluate(n).doubleValue();

            // It enters this if-statement iff:
            //     [ (maximize = True) AND (valueNeighbor > bestValue) ]
            //                  OR
            //     [ (maximize = False) AND (valueNeighbor < bestValue) ]
            if ( isBetter(valueNeighbor, bestValue) ) {

                // Update "bestValue"
                bestValue = valueNeighbor;

                // Clear previous "best" neighbors and add the new one
                bestNeighbors.clear();
                bestNeighbors.add(n);

                // Otherwise, it means that best value <= valueNeighbor
            } else if (bestValue == valueNeighbor) {
                bestNeighbors.add(n);
            }
        }

        if ( isBetter(bestValue, valueCurrentNode) ) {

            // Randomly choose an element in the best neighborhood.
            S neighbor = bestNeighbors.get(getRandomInstance().nextInt(bestNeighbors.size()));

            return LocalSearchNode.createNode(neighbor, bestValue);
        }

        // Otherwise return null-
        return null;
    }


}

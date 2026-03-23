package it.uniroma1.ai.problems.romaniatrip;

import it.uniroma1.ai.statesearch.problem.AbstractProblem;

import java.util.List;
import java.util.ArrayList;

/**
 * Formulate the "Romania Trip" problem.
 * Here, Actions correspond to a State:
 *      Action -> "From the current State go to another State."
 * Notice that the objective is "built-in" the class.
 */
public class RomaniaTripToBucharestProblem extends AbstractProblem<State, State> {

    /**
     * Constructor.
     */
    public RomaniaTripToBucharestProblem(State initialState) {
        super(initialState);
    }

    /**
     * Return all possible actions (of type State) from a given State.
     * If no actions can be performed, the returned list is empty.
     */
    @Override
    public List<State> getActions(State s) {
        return new ArrayList<>( getWorld().getNearbyCities(s) );
    }

    /**
     * Return the result of applying an action (a State) to the State instance "s".
     */
    @Override
    public State getResult(State s, State a) {
        return a;
    }

    /**
     * Get the step cost for a given State and the performed action to it.
     */
    @Override
    public Integer getStepCost(State s, State a) {
        return getWorld().getDistanceBetweenCities(s, a);
    }

    /**
     * Goal test: check if the state is Bucharest.
     */
    @Override
    public boolean goalTest(State s) {
        return s.equals(State.BUCHAREST);
    }

    // === Utility methods ===
    /**
     *  Utility method to get what we can call: The Oracle.
     */
    private World getWorld() {
        return World.getInstance();
    }
}
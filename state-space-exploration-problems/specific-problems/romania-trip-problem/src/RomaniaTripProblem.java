import statesearch.problem.AbstractProblem;

import java.util.List;
import java.util.ArrayList;

/**
 * Formulate the "Romania Trip" problem.
 * Here, Actions correspond to a State:
 *      Action -> "From the current State go to another State."
 */
public class RomaniaTripProblem extends AbstractProblem<State, State> {

    /**
     * Constructor.
     */
    public RomaniaTripProblem(State initialState, State objective) {
        super(initialState, objective);
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

    // === Utility methods ===
    /**
     *  Utility method to get what we can call: The Oracle.
     */
    private World getWorld() {
        return World.getInstance();
    }
}

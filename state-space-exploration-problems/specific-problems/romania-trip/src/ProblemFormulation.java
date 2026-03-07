import java.util.ArrayList;
import java.util.List;

/**
 * Formulation of the specific problem of "exploring the Romania road map" (in a smart way).
 * Encapsulates the five components (according to slides):
 *      initial state, actions, transition model, goal test, and step cost.
 * We are assuming that ACTION is "a State returned from another State".
 */
public class ProblemFormulation {

    private final State initialState;

    private final State objective;

    /**
     * Constructor.
     */
    public ProblemFormulation(State initialState, State objective) {
        this.initialState = initialState;

        this.objective = objective;
    }

    /**
     * Returns the possible Actions for a given state.
     */
    public List<Action> getActions(State s) {

        List<Action> actions = new ArrayList<Action>();

        // For each reachable state from "s", create the corresponding action
        for ( State city : getWorld().getNearbyCities(s) ) {
            actions.add(new Action(s, city));
        }

        return actions;
    }

    /**
     * Transition model: returns the State resulting from applying
     * action "a" in state "s".
     */
    public State getResult(State s, Action a) {
        return a.getTo();
    }

    /**
     * Returns true if the given state satisfies the objective.
     */
    public boolean goalTest(State s) {
        return s.equals(this.objective);
    }

    /**
     * Returns the step cost between two adjacent states.
     */
    private Integer getStepCost( State a, State b ) {
        return getWorld().getDistanceBetween(a, b);
    }

    /*
    Here should be placed the public function that
    allows to compute the "path cost".
     */

    /**
     * Returns the initial state.
     */
    public State getInitialState() {
        return initialState;
    }

    /**
     * Returns a reference to the World.
     */
    private World getWorld() {
        return World.getInstance();
    }
}

import java.util.List;

/**
 * Formulation of the specific problem of "exploring the Romania road map" (in a smart way).
 * Encapsulates the five components (according to slides):
 *      initial state, actions, transition model, goal test, and step cost.
 * We are assuming that ACTION is "a State returned from another State".
 */
public class ProblemFormulation {

    private final State initialState;

    /*
    This allows the user to create multiple (different) instances of the problem,
    by setting different objectives (instead of hardcode one specific "objective"
    with a method).
     */
    private final State objective;

    /**
     * Constructor.
     */
    public ProblemFormulation(State initialState, State objective) {
        this.initialState = initialState;
        this.objective = objective;
    }

    /**
     * Returns the possible actions for a given state
     * (so: a list of states reachable from the State "s").
     */
    public List<State> getActions(State s) {
        return getWorld().getNearbyCities(s);
    }

    /**
     * Transition model: returns the State resulting from applying
     * action "a" in state "s".
     */
    public State getResult(State s, State a) {
        return a;
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
    private Integer getStepCost(State a, State b ) {
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

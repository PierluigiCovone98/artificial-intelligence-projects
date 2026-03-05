import java.util.List;

/**
 * Formulation of a specific exploration problem defined on the Romania road map.
 * Encapsulates the five components, according to slides:
 *      initial state, actions, transition model, goal test, and step cost.
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
     * Returns the possible actions for a given state.
     */
    public List<State> getActions(State s) {
        // Get nearby states (cities) from the oracle.
        return getOracle().getNearbyCities(s);
    }

    /**
     * Transition model: returns the state resulting from applying action a in state s.
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
    public Integer getCost(State a, State b ) {
        return getOracle().getDistanceBetween(a, b);
    }

    /**
     * Returns the initial state.
     */
    public State getInitialState() {
        return initialState;
    }

    /**
     * Returns a reference to the world oracle.
     */
    private World getOracle() {
        return World.getInstance();
    }
}

/**
 * Represents an action: moving from a source State to a destination State.
 */
public class Action {

    private final State from;
    private final State to;

    /**
     * Constructor.
     */
    public Action(State from, State to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Get the "from" State.
     */
    public State getFrom() {
        return from;
    }

    /**
     * Get the "from" State.
     */
    public State getTo() {
        return to;
    }

}

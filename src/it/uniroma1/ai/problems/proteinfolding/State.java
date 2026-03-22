package it.uniroma1.ai.problems.proteinfolding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A State in the "Protein Folding" problem (modeled to be solved with space state search algorithms)
 * is simply defined by:
 *  - A list of those coordinates of the 2D grid that have an amino acid placed on it;
 *  - The position for the last plaed amino acid.
 */
public class State {

    private final Map<Position, AminoAcid> grid;
    private final Position lastPlaced;


    /**
     * Private Constructor (Remember:states are "labels").
     */
    private State(Map<Position, AminoAcid> grid, Position lastPlaced) {
        // Initialize fields
        this.grid = grid;
        this.lastPlaced = lastPlaced;
    }

    /**
     * Create the initial state: first amino acid at (0,0).
     */
    public static State createInitialState(AminoAcid firstAminoAcid) {

        Map<Position, AminoAcid> grid = new HashMap<>();
        Position origin = new Position(0, 0);

        // Add the first amino acid to the grid.
        grid.put(origin, firstAminoAcid);

        return new State(grid, origin);
    }

    /**
     * Create a child state by placing a new amino acid at the given position.
     */
    public static State createChildState(State parent, Position position, AminoAcid aminoAcid) {
        // Notice: For different instances of the same class it is possible to
        //         access members declared "private" (they know the implementation)
        //         because of the same class.
        Map<Position, AminoAcid> grid = new HashMap<>(parent.grid);

        // Extend the grid by adding the new amino acid
        grid.put(position, aminoAcid);

        return new State(grid, position);
    }



    // === Public Interface ===

    /**
     * Position of the last placed amino acid.
     */
    public Position getLastPlaced() {
        return lastPlaced;
    }

    /**
     * Number of amino acids placed so far.
     */
    public int getPlacedAminoAcidCount() {
        return grid.size();
    }

    /**
     *  True if the given position is already occupied.
     */
    public boolean isOccupied(Position position) {
        return grid.containsKey(position);
    }

    /**
     * Amino acid at the given position, or null if empty.
     */
    public AminoAcid getAt(Position position) {
        return grid.get(position);
    }


    // === Override(s) ===

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        State state = (State) o;
        return this.grid.equals(state.grid) && this.lastPlaced.equals(state.lastPlaced);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grid, lastPlaced);
    }

}
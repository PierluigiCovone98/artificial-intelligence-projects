package it.uniroma1.ai.problems.proteinfolding;

import it.uniroma1.ai.search.algorithm.statespace.Heuristic;
import it.uniroma1.ai.search.problem.AbstractStateSpaceProblem;

import java.util.ArrayList;
import java.util.List;

/**
 * HP 2D Protein Folding modeled as a state-space search problem.
 * Given a sequence of amino acids (H/P), finds a conformation on a 2D grid
 * that minimizes energy (maximizes H-H contacts) using a complementary cost scheme (3 - c).
 */
public class ProteinFoldingStateSpaceProblem extends AbstractStateSpaceProblem<State, Action> {

    // World Knowledge
    private final AminoAcid[] proteinAminoAcids;

    /**
     * Private factory method to convert a String
     */
    private static AminoAcid[] stringToAminoAcid(String protein) {
        // Prepare the output
        int proteinLength = protein.length();

        AminoAcid[] result = new AminoAcid[proteinLength];
        // Split in an array of strings, once.
        String[] stringProteinAminoAcids = protein.split("");

        for (int i = 0; i < proteinLength; i++) {
            result[i] = AminoAcid.valueOf(stringProteinAminoAcids[i]);
        }

        return result;
    }

    /**
     * Public Constructor.
     * Invokes the private constructor with the already converted array of AminoAcids.
     */
    public ProteinFoldingStateSpaceProblem(String protein) {
        this( stringToAminoAcid(protein) );
    }

    /**
     * Private constructor.
     */
    private ProteinFoldingStateSpaceProblem(AminoAcid[] proteinAminoAcids) {
        // Create the initial state
        super(
                State.createInitialState( proteinAminoAcids[0] )
        );

        // Initialize the field
        this.proteinAminoAcids = proteinAminoAcids;
    }

    /**
     * Return the legal directions from the "state.lastPlaced" position.
     * A direction is "legal" if the neighboring cell is not already occupied.
     */
    @Override
    public List<Action> getActions(State state) {

        List<Action> actions = new ArrayList<>();

        // Preventing from returning actions when the protein is fully placed.
        if (state.getPlacedAminoAcidCount() == proteinAminoAcids.length)
            return actions;

        // Save last filled Position
        Position lastPlaced = state.getLastPlaced();

        for ( Action action : Action.values() ) {

            // Save the Position were the "action" led to
            Position neighbor = computeNextPosition(lastPlaced, action);

            // If not already placed, add the action
            if (!state.isOccupied(neighbor))
                actions.add(action);
        }

        return actions;
    }

    /**
     * Place the next amino acid in the sequence at the cell reached by the given action.
     */
    @Override
    public State getResult(State state, Action action) {

        // Save required information that I "cannot directly access".
        Position nextPosition = computeNextPosition(state.getLastPlaced(), action);

        // Notice: If I've placed n amino acids, the next one is exactly the n-th from the array.
        AminoAcid nextAminoacid =  getAminoAcidAt( state.getPlacedAminoAcidCount() );

        return State.createChildState(state, nextPosition, nextAminoacid);
    }

    /**
     * Know the "step cost" of going from the State "state" to the "next one" by performing Action "action".
     */
    @Override
    public Number getStepCost(State state, Action action) {

        Position lastPlaced = state.getLastPlaced();

        // 1. We need to know which is the "next" Position.
        Position nextPosition = computeNextPosition(lastPlaced, action);

        // 2. We also need to know which is the next amino acid to place
        AminoAcid nextAminoAcid = getAminoAcidAt( state.getPlacedAminoAcidCount() );


        // Define the cost as a "counter"
        int cost = 0;

        // 3. If the nextAminoAcid is "P" then all costs are the same
        if (nextAminoAcid.equals(AminoAcid.P))
            return cost;

        // 4. Let's consider all neighbors from "nextPosition"; we are considering: nextAminoAcid = "H".
        for (Action a : Action.values()) {

            Position nextPositionNeighbor = computeNextPosition(nextPosition, a);

            // Excluding the case of direct contact.
            if ( nextPositionNeighbor.equals(lastPlaced) )
                continue;

            // Increment the cost when the neighbor is empty or if a "P" amino acid is placed
            if ( !state.isOccupied(nextPositionNeighbor) || state.getAt(nextPositionNeighbor).equals(AminoAcid.P) )
                cost++;
        }

        return cost;
    }

    /**
     * Check if all amino acids of the given protein are placed.
     */
    @Override
    public boolean goalTest(State state) {
        return state.getPlacedAminoAcidCount() == proteinAminoAcids.length;
    }

    // === World Knowledge interaction ===

    /**
     * Query the world knowledge about the amino acid at position "n".
     * This method does not know the caller intention of knowing
     * "which is the next amino acid to place in the grid".
     */
    private AminoAcid getAminoAcidAt(int n) {
        if (n < 0 || n >= proteinAminoAcids.length)
            return null;
        return proteinAminoAcids[n];
    }

    // === Utility methods ===

    /**
     * Compute the "next position" from the "startingPosition".
     */
    private Position computeNextPosition(Position startingPosition, Action action) {
        return new Position( startingPosition.x() + action.getDx(),
                startingPosition.y() + action.getDy() );
    }


    // === HEURISTIC METHODS ===

    /**
     * Estimates remaining cost based on the number of "H" amino acids still to be placed.
     */
    public Heuristic<State> buildHeuristic() {

        int proteinLength = proteinAminoAcids.length;

        // It changes the future cost estimation.
        boolean lastIsH = proteinAminoAcids[proteinLength - 1] == AminoAcid.H;


        return state -> {

            // Save placed amino acids
            int placedCount = state.getPlacedAminoAcidCount();

            // Count H in the remaining (not yet placed) portion of the sequence
            int remainingH = 0;
            for (int i = placedCount; i < proteinLength; i++) {
                if (proteinAminoAcids[i] == AminoAcid.H)
                    remainingH++;
            }

            // The check on the "placed amino acids" avoid the case where,
            // when the last amino acid is H and all amino acids have been
            // placed, the heuristic returns "0-1".
            return (lastIsH && placedCount < proteinLength)
                    ? remainingH - 1
                    : remainingH;
        };
    }
}
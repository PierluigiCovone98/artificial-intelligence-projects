package it.uniroma1.ai.problems.proteinfolding;

import it.uniroma1.ai.statesearch.problem.AbstractProblem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ProteinFoldingProblem extends AbstractProblem<State, Action> {

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
    public ProteinFoldingProblem(String protein) {
        this( stringToAminoAcid(protein) );
    }

    /**
     * Private constructor.
     */
    private ProteinFoldingProblem(AminoAcid[] proteinAminoAcids) {
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

}
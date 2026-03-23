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
     * Constructor.
     */
    public ProteinFoldingProblem(String protein) {

        // Create the initial state
        super(
                State.createInitialState( stringToAminoAcid(protein)[0] )
        );

        // Initialize the field
        this.proteinAminoAcids =  stringToAminoAcid(protein);
    }


    /**
     *
     */
    @Override
    public List<Action> getActions(State state) {

        List<Action> actions = new ArrayList<>();

        // Save last filled Position
        Position lastPlaced = state.getLastPlaced();

        // Now I check which are possible neighbors (and related actions)
        Position above = new Position(lastPlaced.x(), lastPlaced.y()+1);
        if (!state.isOccupied(above))
            actions.add(Action.UP);

        Position below = new Position(lastPlaced.x(), lastPlaced.y()-1);
        if (!state.isOccupied(below))
            actions.add(Action.DOWN);

        Position right = new Position(lastPlaced.x()+1, lastPlaced.y());
        if (!state.isOccupied(right))
            actions.add(Action.RIGHT);

        Position left = new Position(lastPlaced.x()-1, lastPlaced.y());
        if (!state.isOccupied(left))
            actions.add(Action.LEFT);

        return actions;
    }

    // TODO: implements these methods
    @Override
    public State getResult(State state, Action action) {
        return null;
    }

    @Override
    public Number getStepCost(State state, Action action) {
        return null;
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

}
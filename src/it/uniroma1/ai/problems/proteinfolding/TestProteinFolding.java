package it.uniroma1.ai.problems.proteinfolding;

import it.uniroma1.ai.statesearch.agent.Agent;
import it.uniroma1.ai.statesearch.search.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Test the Protein Folding problem modeled as Space State Search Problem.
 */
public class TestProteinFolding {

    /**
     * Entry point for the test.
     */
    public static void main(String[] args) {

        // Get the protein from the input
        String protein = args[0];
        System.out.println("[Info] Protein Folding Problem");
        System.out.println("[Info] Inserted protein: " + protein + "\n");

        // Define the problem
        ProteinFoldingProblem problem = new ProteinFoldingProblem(protein);

        // === Test 1: BFS ===
        Agent<State, Action> agentBFS = new Agent<>(new BFSearch<>(true));
        List<Action> planBFS = agentBFS.findPlan(problem);
        System.out.println( agentBFS.getSearchReport(planBFS, problem) + printFoldingResult(problem, planBFS) );

        // === Test 2: DFS ===
        Agent<State, Action> agentDFS = new Agent<>(new DFSearch<>(true));
        List<Action> planDFS = agentDFS.findPlan(problem);
        System.out.println( agentDFS.getSearchReport(planDFS, problem) + printFoldingResult(problem, planDFS) );


        // === Test 3: Min Cost ===
        Agent<State, Action> agentMinCost = new Agent<>(new MinCostSearch<>(true));
        List<Action> planMinCost = agentMinCost.findPlan(problem);
        System.out.println( agentMinCost.getSearchReport(planMinCost, problem) + printFoldingResult(problem, planMinCost) );

    }

    /**
     * Build a FoldingResult and print grid, contacts, and energy.
     */
    private static String printFoldingResult(ProteinFoldingProblem problem, List<Action> plan) {

        // Security check
        if (plan == null)
            return "[Err] No solution to visualize.\n";

        // One place to get everything need about Protein Folding statistic
        FoldingResult result = new FoldingResult(problem, plan);

        // Print Statistics
        StringBuilder sb = new StringBuilder();
        sb.append("\t< Protein Folding Result >\n");
        sb.append( result.visualizeGrid() ).append("\n");
        sb.append("H-H Contacts:      ").append( result.getHHContacts() ).append("\n");
        sb.append("Energy:            ").append(result.getEnergy()).append("\n");

        return sb.toString();
    }

    /**
     * Precomputed result of a protein folding solution.
     * Reconstruct the plan to extract the final state, ordered positions, and H-H contact count.
     */
    private static class FoldingResult {

        private final State finalState;
        private final List<Position> orderedPositions;
        private final int hhContacts;

        /**
         * Constructor: replays the plan.
         */
        FoldingResult(ProteinFoldingProblem problem, List<Action> plan) {

            // Starts from the initial state
            State state = problem.getInitialState();
            List<Position> positions = new ArrayList<>();
            positions.add(state.getLastPlaced());

            for (Action action : plan) {
                state = problem.getResult(state, action);
                positions.add(state.getLastPlaced());
            }

            // Initialize fields
            this.finalState = state;
            this.orderedPositions = positions;
            this.hhContacts = computeHHContacts();
        }

        /**
         * Render the protein conformation as a 2D grid with connectors.
         */
        private String visualizeGrid() {

            // 1. Find bounds from actual positions
            int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

            for (Position p : orderedPositions) {

                int pX = p.x(), pY = p.y();

                minX = Math.min(minX, pX);
                maxX = Math.max(maxX, pX);
                minY = Math.min(minY, pY);
                maxY = Math.max(maxY, pY);
            }

            // 2. Create display grid (tripled horizontal, doubled vertical)
            int width  = (maxX - minX) * 3 + 1;
            int height = (maxY - minY) * 2 + 1;
            char[][] display = new char[height][width];

            for (char[] row : display)
                Arrays.fill(row, ' ');

            // 3. Place amino acids (lowercase for start)
            for (int i = 0; i < orderedPositions.size(); i++) {
                Position p = orderedPositions.get(i);
                int dx = (p.x() - minX) * 3;
                int dy = (maxY - p.y()) * 2;
                char c = finalState.getAt(p).toString().charAt(0);
                display[dy][dx] = (i == 0) ? Character.toLowerCase(c) : c;
            }

            // 4. Place connectors between consecutive amino acids
            for (int i = 0; i < orderedPositions.size() - 1; i++) {
                Position a = orderedPositions.get(i);
                Position b = orderedPositions.get(i + 1);

                if (a.y() == b.y()) {
                    // Horizontal connector: two dashes
                    int startX = (Math.min(a.x(), b.x()) - minX) * 3 + 1;
                    int cy = (2 * maxY - a.y() - b.y());
                    display[cy][startX] = '-';
                    display[cy][startX + 1] = '-';
                } else {
                    // Vertical connector: single pipe
                    int cx = (a.x() - minX) * 3;
                    int cy = (2 * maxY - a.y() - b.y());
                    display[cy][cx] = '|';
                }
            }

            // 5. Build output
            StringBuilder sb = new StringBuilder();
            sb.append("Protein Conformation (lowercase = start):\n\n");

            for (char[] row : display) {
                sb.append(new String(row).stripTrailing()).append("\n");
            }

            return sb.toString();
        }


        /**
         * Count H-H contacts between non-consecutive amino acids in the chain.
         */
        private int computeHHContacts() {

            int contacts = 0;


            for (int i = 0; i < orderedPositions.size(); i++) {

                // Get the i-th position
                Position p = orderedPositions.get(i);

                // Skip non-H amino acids
                if (finalState.getAt(p) != AminoAcid.H)
                    continue;

                // Check all 4 grid neighbors
                for (Action a : Action.values()) {
                    Position neighbor = new Position(p.x() + a.getDx(), p.y() + a.getDy());

                    // Check all 4 grid neighbors
                    if (finalState.getAt(neighbor) != AminoAcid.H)
                        continue;

                    // Find the neighbor's index in the chain
                    int j = orderedPositions.indexOf(neighbor);

                    // Count only if: on the grid, not consecutive in chain, and i < j (avoid double counting)
                    if (j != -1 && Math.abs(i - j) > 1 && i < j)
                        contacts++;
                }
            }

            return contacts;
        }

        /** Get the final state after all amino acids have been placed. */
        State getFinalState() { return finalState; }

        /** Get positions in the order they were placed during the folding. */
        List<Position> getOrderedPositions() { return orderedPositions; }

        /** Get the number of H-H topological (non-consecutive) contacts. */
        int getHHContacts() { return hhContacts; }

        /** Get the energy of the conformation (negated contact count). */
        int getEnergy() { return -hhContacts; }

    }   // class FoldingResult

}
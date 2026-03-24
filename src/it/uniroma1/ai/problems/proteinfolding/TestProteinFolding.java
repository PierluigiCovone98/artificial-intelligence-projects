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
        System.out.println("[Info] Inserted protein: " + protein);

        // Define the problem
        ProteinFoldingProblem problem = new ProteinFoldingProblem(protein);

        // === Test 1: BFS ===
        Agent<State, Action> agentBFS = new Agent<>(new BFSearch<>(true));
        List<Action> planBFS = agentBFS.findPlan(problem);
        System.out.println(agentBFS.getSearchReport(planBFS, problem) + visualizeProteinFoldingOnTheGrid(problem, planBFS));

        // === Test 2: DFS ===
        Agent<State, Action> agentDFS = new Agent<>(new DFSearch<>(true));
        List<Action> planDFS = agentDFS.findPlan(problem);
        System.out.println(agentDFS.getSearchReport(planDFS, problem) + visualizeProteinFoldingOnTheGrid(problem, planDFS));


        // === Test 3: Min Cost ===
        Agent<State, Action> agentMinCost = new Agent<>(new MinCostSearch<>(true));
        List<Action> planMinCost = agentMinCost.findPlan(problem);
        System.out.println(agentMinCost.getSearchReport(planMinCost, problem) + visualizeProteinFoldingOnTheGrid(problem, planMinCost));

    }


    /**
     * Helper method to visualize the protein folding on the 2D grid.
     */
    private static String visualizeProteinFoldingOnTheGrid(ProteinFoldingProblem problem, List<Action> plan) {

        // Security check
        if (plan == null)
            return "[Err] No solution to visualize.\n";

        // 1. Reconstruct actions to get the ordered sequence of positions, starting from the initial state.
        State state = problem.getInitialState();
        List<Position> sequentialPositions = new ArrayList<>();
        sequentialPositions.add(state.getLastPlaced());

        for (Action action : plan) {
            state = problem.getResult(state, action);
            sequentialPositions.add(state.getLastPlaced());
        }

        // "state" is now the final state (all amino acids are placed)

        // 2. Find bounds from actual positions
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

        for (Position p : sequentialPositions) {

            int pX = p.x(), pY = p.y();

            minX = Math.min(minX, pX);
            maxX = Math.max(maxX, pX);
            minY = Math.min(minY, pY);
            maxY = Math.max(maxY, pY);
        }

        // 3. Create display grid (doubled coordinates: amino acids at even, connectors at odd)
        int width  = (maxX - minX) * 3 + 1;
        int height = (maxY - minY) * 2 + 1;
        char[][] display = new char[height][width];

        for (char[] row : display)
            Arrays.fill(row, ' ');

        // 4. Place amino acids
        for (int i = 0; i < sequentialPositions.size(); i++) {
            Position p = sequentialPositions.get(i);
            int dx = (p.x() - minX) * 3;
            int dy = (maxY - p.y()) * 2;
            char c = state.getAt(p).toString().charAt(0);
            display[dy][dx] = (i == 0) ? Character.toLowerCase(c) : c;
        }

        // 5. Place connectors between consecutive amino acids
        for (int i = 0; i < sequentialPositions.size() - 1; i++) {
            Position a = sequentialPositions.get(i);
            Position b = sequentialPositions.get(i + 1);

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

        // 6. Build output
        StringBuilder sb = new StringBuilder();
        sb.append("Protein Conformation (lowercase = start):\n\n");
        for (char[] row : display) {
            sb.append(new String(row).stripTrailing()).append("\n");
        }

        return sb.toString();
    }

}
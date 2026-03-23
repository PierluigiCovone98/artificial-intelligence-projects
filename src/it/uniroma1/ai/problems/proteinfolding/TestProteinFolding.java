package it.uniroma1.ai.problems.proteinfolding;

import it.uniroma1.ai.statesearch.agent.Agent;
import it.uniroma1.ai.statesearch.search.*;

import java.util.List;

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
        System.out.println(agentBFS.getSearchReport(planBFS, problem));

        // === Test 2: DFS ===
        Agent<State, Action> agentDFS = new Agent<>(new DFSearch<>(true));
        List<Action> planDFS = agentDFS.findPlan(problem);
        System.out.println(agentDFS.getSearchReport(planDFS, problem));

        // === Test 3: Min Cost ===
        Agent<State, Action> agentMinCost = new Agent<>(new MinCostSearch<>(true));
        List<Action> planMinCost = agentMinCost.findPlan(problem);
        System.out.println(agentMinCost.getSearchReport(planMinCost, problem));
    }
}
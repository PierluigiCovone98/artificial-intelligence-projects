package it.uniroma1.ai.problems.romaniatrip;

import it.uniroma1.ai.statesearch.agent.Agent;

import it.uniroma1.ai.statesearch.search.*;

import java.util.List;

/**
 * Test if everything works.
 */
public class Test {


    public static void main(String[] args) {

        // === Romania Trip Problem ===
        RomaniaTripToBucharestProblem problem = new RomaniaTripToBucharestProblem(State.ARAD);

        // === Test 1: BFS
        Agent<State, State> agentBFS = new Agent<>( new BFSearch<>(true) );
        List<State> planBFS = agentBFS.findPlan(problem);
        System.out.println( agentBFS.getSearchReport(planBFS, problem) );

        // === Test 2: DFS
        Agent<State, State> agentDFS = new Agent<>(new DFSearch<>(true));
        List<State> planDFS = agentDFS.findPlan(problem);
        System.out.println( agentDFS.getSearchReport(planDFS, problem) );

        // === Test 3: Min Cost
        Agent<State, State> agentMinCost = new Agent<>(new MinCostSearch<>(true));
        List<State> planMinCost = agentMinCost.findPlan(problem);
        System.out.println( agentMinCost.getSearchReport(planMinCost, problem) );

        // === Heuristic for informed searches
        RomaniaDistances rd = RomaniaDistances.getInstance();
        Heuristic<State> h = state -> rd.getDistance(state, State.BUCHAREST);

        // === Test 4: Best First Greedy
        Agent<State, State> agentGreedy = new Agent<>(new BestFirstGreedySearch<>(h, true));
        List<State> planGreedy = agentGreedy.findPlan(problem);
        System.out.println( agentGreedy.getSearchReport(planGreedy, problem) );

        // === Test 5: A* ===
        Agent<State, State> agentAstar = new Agent<>(new AstarSearch<>(h, true));
        List<State> planAstar = agentAstar.findPlan(problem) ;
        System.out.println( agentAstar.getSearchReport(planAstar, problem) );
    }

}

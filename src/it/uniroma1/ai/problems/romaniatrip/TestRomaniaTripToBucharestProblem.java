package it.uniroma1.ai.problems.romaniatrip;

import it.uniroma1.ai.search.agent.StateSpaceAgent;

import it.uniroma1.ai.search.algorithm.statespace.*;

import java.util.List;

/**
 * Test if everything works.
 */
public class TestRomaniaTripToBucharestProblem {


    public static void main(String[] args) {

        // === Romania Trip Problem ===
        RomaniaTripToBucharestStateSpaceProblem problem = new RomaniaTripToBucharestStateSpaceProblem(State.ARAD);

        // === Test 1: BFS
        StateSpaceAgent<State, State> agentBFS = new StateSpaceAgent<>( new BFSearch<>(true) );
        List<State> planBFS = agentBFS.findPlan(problem);
        System.out.println( agentBFS.getSearchReport(planBFS, problem) );

        // === Test 2: DFS
        StateSpaceAgent<State, State> agentDFS = new StateSpaceAgent<>(new DFSearch<>(true));
        List<State> planDFS = agentDFS.findPlan(problem);
        System.out.println( agentDFS.getSearchReport(planDFS, problem) );

        // === Test 3: Min Cost
        StateSpaceAgent<State, State> agentMinCost = new StateSpaceAgent<>(new MinCostSearch<>(true));
        List<State> planMinCost = agentMinCost.findPlan(problem);
        System.out.println( agentMinCost.getSearchReport(planMinCost, problem) );

        // === Heuristic for informed searches
        RomaniaDistances rd = RomaniaDistances.getInstance();
        Heuristic<State> h = rd.buildHeuristic();

        // === Test 4: Best First Greedy
        StateSpaceAgent<State, State> agentGreedy = new StateSpaceAgent<>(new BestFirstGreedySearch<>(h, true));
        List<State> planGreedy = agentGreedy.findPlan(problem);
        System.out.println( agentGreedy.getSearchReport(planGreedy, problem) );

        // === Test 5: A* ===
        StateSpaceAgent<State, State> agentAstar = new StateSpaceAgent<>(new AstarSearch<>(h, true));
        List<State> planAstar = agentAstar.findPlan(problem) ;
        System.out.println( agentAstar.getSearchReport(planAstar, problem) );
    }

}

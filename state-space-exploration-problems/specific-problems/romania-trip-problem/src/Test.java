import statesearch.agent.Agent;

import statesearch.search.*;

/**
 * Test if everything works.
 */
public class Test {


    public static void main(String[] args) {

        // === Romania Trip Problem ===
        RomaniaTripProblem problem = new RomaniaTripProblem(State.ARAD, State.BUCHAREST);

        // === Test 1: BFS
        Agent<State, State> agentBFS = new Agent<>( new BFSearch<>(true) );
        System.out.println("[T1] BFS:");
        printSolution(agentBFS.findPlan(problem), problem.getInitialState());

        // === Test 2: DFS
        Agent<State, State> agentDFS = new Agent<>(new DFSearch<>(true));
        System.out.println("[T2] DFS:");
        printSolution(agentDFS.findPlan(problem), problem.getInitialState());

        // === Test 3: Min Cost
        Agent<State, State> agentMinCost = new Agent<>(new MinCostSearch<>(true));
        System.out.println("[T3] Min Cost:");
        printSolution(agentMinCost.findPlan(problem), problem.getInitialState());

        // === Heuristic for informed searches
        RomaniaDistances rd = RomaniaDistances.getInstance();
        Heuristic<State> h = state -> rd.getDistance(state, State.BUCHAREST);

        // === Test 4: Best First Greedy
        Agent<State, State> agentGreedy = new Agent<>(new BestFirstGreedySearch<>(h, true));
        System.out.println("[T4] Best First Greedy:");
        printSolution(agentGreedy.findPlan(problem), problem.getInitialState());

        // === Test 5: A* ===
        Agent<State, State> agentAstar = new Agent<>(new AstarSearch<>(h, true));
        System.out.println("[T5] A*:");
        printSolution(agentAstar.findPlan(problem), problem.getInitialState());
    }

    /**
     * Print a possible solution (sequence of actions).
     */
    private static void printSolution(java.util.List<State> plan, State initialState) {

        // Warn if there are no solutions.
        if (plan == null) {
            System.out.println("[Warn] No solution found.");
            return;
        }

        // Otherwise: format the solution.
        StringBuilder sb = new StringBuilder();
        sb.append(initialState);

        for (State action : plan) {
            sb.append(" -> ").append(action);
        }

        System.out.println(sb.toString());
    }

}

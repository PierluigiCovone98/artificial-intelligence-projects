import problem.*;

import java.util.List;

/**
 * Test if everything works.
 */
public class Test {

    public static void main(String[] args) {

        // === Romania Trip Problem ===
        //  - Initial State         :   Arad
        //  - Objective             :   Bucharest

        RomaniaTripProblem problemAradBucharest = new RomaniaTripProblem(State.ARAD, State.BUCHAREST);

        // === Test 1 ===
        //  - Frontier Strategy     :   FIFO (BSD Search)

        Frontier<State, State> fifoFrontier = new FifoFrontier<>();
        SearchAlgorithm<State, State> fifoSearch = new SearchAlgorithm<>(fifoFrontier, true);
        Agent<State, State> agentBFS = new Agent<>(fifoSearch);

        List<State> planBFS = agentBFS.findPlan(problemAradBucharest);
        System.out.println("[T1] FIFO Frontier:\n");
        printSolution(planBFS, problemAradBucharest.getInitialState());    // Initial state from the problem.



        // === Test 2 ===
        //  - Frontier Strategy     :   LIFO (DSD Search)

        Frontier<State, State> lifoFrontier = new LifoFrontier<>();
        SearchAlgorithm<State, State> lifoSearch = new SearchAlgorithm<>(lifoFrontier, true);
        Agent<State, State> agentDFS = new Agent<>(lifoSearch);

        List<State> planDFS = agentDFS.findPlan(problemAradBucharest);
        System.out.println("[T2] LIFO Frontier:\n");
        printSolution(planDFS, problemAradBucharest.getInitialState());    // Initial state from the problem.

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

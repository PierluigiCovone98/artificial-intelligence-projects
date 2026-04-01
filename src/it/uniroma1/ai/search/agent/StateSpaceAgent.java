package it.uniroma1.ai.search.agent;

import it.uniroma1.ai.search.node.StateSpaceSearchNode;
import it.uniroma1.ai.search.problem.AbstractProblem;
import it.uniroma1.ai.search.algorithm.statespace.AbstractStateSpaceSearch;
import it.uniroma1.ai.search.problem.AbstractStateSpaceProblem;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * An Agent instance needs to know which kind of search it has to perform.
 * It has the capability of retrieve solution from a given node.
 */
public class StateSpaceAgent<S, A> {

    private final AbstractStateSpaceSearch<S, A> searchAlgorithm;

    /**
     * Constructor.
     */
    public StateSpaceAgent(AbstractStateSpaceSearch<S, A> searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }

    /*
    Here it should be put the "overall method" that actually solves the problem.
    This means that, for a given problem:
        - It is planned a "sequence of actions" (aka: a solution)
        - Actions are taken.
    The method should look like:

        public void solve(AbstractProblem<S,A> problem, ??? perception) {

            // Find a plan and execute it
            List<A> plan = findPlan(problem);

            [...] execute_plan(plan);
        }


     So, notice that (for the moment) we're just defining the method that allows
     the agent to plan a solution.
     */

    /**
     * Method to plan a solution.
     */
    public List<A> findPlan(AbstractStateSpaceProblem<S, A> problem) {

        // 1. Search the objective node
        StateSpaceSearchNode<S, A> objectiveNode = searchAlgorithm.search(problem);

        // Return a plan for the problem (a solution).
        if (objectiveNode != null)
            return getSolution(objectiveNode);

        // There are no solutions for the problem.
        return null;
    }


    /**
     * Compose the solution from a given Node (the objective one).
     */
    private List<A> getSolution(StateSpaceSearchNode<S, A> node) {

        // Prepare the output.
        ArrayList<A> solution = new ArrayList<A>();

        // If the node is the "root", it does not enter the "while-loop":
        //  then, an empty list of actions is returned (the solution is: take no actions).
        while (node.getParent().isPresent()) {

            // Add the certainly present action.
            solution.add(node.getAction().get());

            // Update the "objectiveNode".
            node = node.getParent().get();
        }

        // Sort actions in chronological order
        Collections.reverse(solution);

        return solution;
    }

    /**
     * Build a formatted report with search statistics and solution.
     * Must be called after findPlan().
     */
    public String getSearchReport(List<A> plan, AbstractProblem<S, A> problem) {

        StringBuilder sb = new StringBuilder();

        sb.append("Algorithm:          ").append(searchAlgorithm.getAlgorithmName()).append("\n");
        sb.append("Time:               ")
                .append(String.format("%.6f", searchAlgorithm.getExecutionTimeNs() / 1_000_000_000.0))
                .append(" s\n");
        sb.append("Iterations:         ").append(searchAlgorithm.getIterations()).append("\n");
        sb.append("Max Frontier Size:  ").append(searchAlgorithm.getMaxFrontierSize()).append("\n");
        sb.append("Initial State:      ").append(problem.getInitialState()).append("\n");
        sb.append("Actions:            ").append(plan != null ? plan : "No solution found").append("\n");

        return sb.toString();
    }


}

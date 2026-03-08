package problem;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Concrete implementation of the BFS search algorithm
 */
public class BFSGraphSearch<S, A> {

    /**
     * Search the node that contains the objective state.
     */
    public Node<S, A> search(AbstractProblem<S, A> problem) {

        // Prepare data structures
        Queue<Node<S, A>> frontier = new LinkedList<>();        // FIFO
        List<S> explored = new LinkedList<>();


        Node<S, A> root = Node.createRoot(problem.getInitialState());

        // Check if the initial state of the problem is the objective one.
        if (problem.goalTest(problem.getInitialState()))
            return root;

        // If not, add the root to the frontier
        frontier.add(root);

        // Start searching
        while (!frontier.isEmpty()) {

            Node<S, A> node = frontier.poll();

            // Add it to the explored ones
            explored.add(node.getState());

            // For each possible action reachable from the node.state...
            for (A action : problem.getActions(node.getState())) {

                // 1. Create the child node
                S childState = problem.getResult(node.getState(), action);
                double childStepCost = (double) problem.getStepCost(node.getState(), action);

                Node<S, A> childNode = Node.createChild(node, childState, action, childStepCost);

                // 2. Remove redundant path and state repetition & checking for the objective state
                if (!explored.contains(childState) &&
                        frontier.stream().noneMatch(n -> n.getState().equals(childState))) {
                    // 2.1. Check if the childState is objective
                    if (problem.goalTest(childState))
                        return childNode;
                    else
                        frontier.add(childNode);
                }
            }
        }

        return null;
    }
}
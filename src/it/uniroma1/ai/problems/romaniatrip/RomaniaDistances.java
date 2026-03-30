package it.uniroma1.ai.problems.romaniatrip;

import it.uniroma1.ai.search.algorithm.statespace.Heuristic;

import java.util.HashMap;
import java.util.Map;

/**
 * Straight-line distances between cities in Romania.
 * Used as heuristic data for informed search algorithms.
 */
public class RomaniaDistances {

    // Singleton
    private static RomaniaDistances instance;

    // Knowledge about straight line distances to any State.
    private final Map<State, Map<State, Double>> distances;


    /**
     * Private constructor (to prevent instantiation).
     */
    private RomaniaDistances() {

        // Initialize the map.
        distances = new HashMap<>();

        // Add Bucharest.
        distances.put(State.BUCHAREST,  toBucharest() );

        // Add more heuristics below ...

    }

    /**
     * Get the instance of RomaniaDistances.
     */
    public static RomaniaDistances getInstance() {

        if (instance == null) {
            instance = new RomaniaDistances();
        }
        return instance;
    }

    /**
     * Create the heuristic that returns the distance to Bucharest, from a certain state.
     */
    public Heuristic<State> buildHeuristic() {
        return state -> distances.get(State.BUCHAREST).get(state);
    }

    // === UTILITY METHODS ===

    /**
     * Create and return the distances from any state to Bucharest.
     */
    private Map<State, Double> toBucharest() {

        Map<State, Double> toBucharestMap = new HashMap<>();

        toBucharestMap.put(State.ARAD, 366.0);
        toBucharestMap.put(State.BUCHAREST, 0.0);
        toBucharestMap.put(State.CRAIOVA, 160.0);
        toBucharestMap.put(State.DOBRETA, 242.0);
        toBucharestMap.put(State.EFORIE, 161.0);
        toBucharestMap.put(State.FAGARAS, 176.0);
        toBucharestMap.put(State.GIURGIU, 77.0);
        toBucharestMap.put(State.HIRSOVA, 151.0);
        toBucharestMap.put(State.IASI, 226.0);
        toBucharestMap.put(State.LUGOJ, 244.0);
        toBucharestMap.put(State.MEHADIA, 241.0);
        toBucharestMap.put(State.NEAMT, 234.0);
        toBucharestMap.put(State.ORADEA, 380.0);
        toBucharestMap.put(State.PITESTI, 100.0);
        toBucharestMap.put(State.RIMNICU_VILCEA, 193.0);
        toBucharestMap.put(State.SIBIU, 253.0);
        toBucharestMap.put(State.TIMISOARA, 329.0);
        toBucharestMap.put(State.URZICENI, 80.0);
        toBucharestMap.put(State.VASLUI, 199.0);
        toBucharestMap.put(State.ZERIND, 374.0);

        return toBucharestMap;
    }

}
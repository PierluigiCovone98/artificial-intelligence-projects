import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represent the "Romania road map" as a weighted undirected graph.
 * It acts like an oracle that can be queried to retrieve information through the public interface, which allows to:
 *  - Get cities that are reachable from a given one;
 *  - Get the distance between two cities.
 */
public class World {

    // Singleton
    private static World instance;

    // Romania Map representation.
    private final Map< State, Map<State,Integer> > romaniaRoadMap;

    /**
     * Private constructor (one World at time).
     */
    private World() {

        // 1. Create the empty map.
        romaniaRoadMap = new HashMap<>();

        // 2. Initialize empty adjacency maps for all cities.
        for (State city : State.values()) {
            romaniaRoadMap.put( city, new HashMap<>() );
        }

        // 3. Populate the Romania road map (based on the graph presented in slides).
        addRoad(State.ARAD, State.ZERIND, 75);
        addRoad(State.ARAD, State.SIBIU, 140);
        addRoad(State.ARAD, State.TIMISOARA, 118);
        addRoad(State.ZERIND, State.ORADEA, 71);
        addRoad(State.ORADEA, State.SIBIU, 151);
        addRoad(State.TIMISOARA, State.LUGOJ, 111);
        addRoad(State.LUGOJ, State.MEHADIA, 70);
        addRoad(State.MEHADIA, State.DOBRETA, 75);
        addRoad(State.DOBRETA, State.CRAIOVA, 120);
        addRoad(State.CRAIOVA, State.RIMNICU_VILCEA, 146);
        addRoad(State.CRAIOVA, State.PITESTI, 138);
        addRoad(State.SIBIU, State.RIMNICU_VILCEA, 80);
        addRoad(State.SIBIU, State.FAGARAS, 99);
        addRoad(State.RIMNICU_VILCEA, State.PITESTI, 97);
        addRoad(State.FAGARAS, State.BUCHAREST, 211);
        addRoad(State.PITESTI, State.BUCHAREST, 101);
        addRoad(State.BUCHAREST, State.GIURGIU, 90);
        addRoad(State.BUCHAREST, State.URZICENI, 85);
        addRoad(State.URZICENI, State.HIRSOVA, 98);
        addRoad(State.URZICENI, State.VASLUI, 142);
        addRoad(State.HIRSOVA, State.EFORIE, 86);
        addRoad(State.VASLUI, State.IASI, 92);
        addRoad(State.IASI, State.NEAMT, 87);
    }


    // === Public Interface to Interact with the World ==

    /**
     * Return nearby cities for a given city.
     */
    public List<State> getNearbyCities(State s) {
        return new ArrayList<State>( romaniaRoadMap.get(s).keySet() );
    }

    /**
     * Return a specific distance (the cost).
     */
    public Integer getDistanceBetweenCities(State a, State b) {
        return romaniaRoadMap.get(a).get(b);
    }

    // === "Structural" Methods ===

    /**
     * Get the singleton instance.
     */
    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    /**
     * Private helper method to easly fill the Romania road map.
     */
    private void addRoad(State a, State b, int cost) {
        romaniaRoadMap.get(a).put(b, cost);
        romaniaRoadMap.get(b).put(a, cost);
    }

    // === Utility methods ===

    /**
     * Pretty format the world.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("=== Romania Road Map ===\n\n");

        for (State city : State.values()) {

            // Specify the city...
            sb.append(city).append(":\n");

            for (Map.Entry<State, Integer> entry : romaniaRoadMap.get(city).entrySet()) {

                // ...and then its nearby city.
                sb.append("  -> ")
                        .append( entry.getKey() )
                        .append(" (")
                        .append(entry.getValue())
                        .append(" km)\n");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

}


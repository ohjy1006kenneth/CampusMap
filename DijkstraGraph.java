// === CS400 Spring 2024 File Header Information ===
// Name: Kenneth Oh
// Email: oh87@wisc.edu
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;

import java.util.Vector;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new PlaceholderMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        Node startNode = new Node(start);
        Node endNode = new Node(end);

        if (startNode == null || endNode == null) {
		    throw new NoSuchElementException("Start node or end node do not correspond to a graph node.");
		}

        PriorityQueue<SearchNode> searchNodes = new PriorityQueue<>();
        PlaceholderMap<Node, Node> visited = new PlaceholderMap<Node, Node>();

        searchNodes.add(new SearchNode(nodes.get(start), 0, null));

        
        while (!searchNodes.isEmpty()) {
            SearchNode current = searchNodes.poll();

            // Check if the current node is the end node
            if (current.node.data == endNode.data) {
                return current;
            }

            // check if the current node is visited
            if (visited.containsKey(current.node)) {
                continue;
            }
            
            visited.put(current.node, current.node);

            // search for the edges with lowest cost
            for (int i = 0; i < current.node.edgesLeaving.size(); i++) {
                Edge edge = current.node.edgesLeaving.get(i);
				Node next = edge.successor;

				if (!visited.containsKey(next)) {
					double cost = current.cost + edge.data.doubleValue();
					searchNodes.add(new SearchNode(next, cost, current));
				}
            }

        }
        throw new NoSuchElementException("No path from start to end is found.");

    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        SearchNode endNode = computeShortestPath(start, end);
        List<NodeType> dataList = new LinkedList<>();

        while (endNode != null) {
            dataList.add(0, endNode.node.data);
            endNode = endNode.predecessor;
        }

        return dataList;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode endNode = computeShortestPath(start, end);

        if (endNode == null) {
            return Double.NaN;
        }

        return endNode.cost;
    }

    // /**
    //  * Test functionality by tracing through a DijkstraGraph
    //  */
    // @Test
    // void test1() {
    //     DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();

    //     graph.insertNode("A");
	// 	graph.insertNode("B");
	// 	graph.insertNode("C");
    //     graph.insertNode("D");
    //     graph.insertNode("E");
    //     graph.insertEdge("A", "B", 6);
	// 	graph.insertEdge("A", "D", 1);
    //     graph.insertEdge("B", "C", 5);
    //     graph.insertEdge("E", "C", 5);
    //     graph.insertEdge("D", "E", 1);
    //     graph.insertEdge("B", "D", 4);
    //     graph.insertEdge("B", "E", 2);
        
    //     Assertions.assertEquals(List.of("A", "D", "E", "C"), graph.shortestPathData("A", "C"));
    //     Assertions.assertEquals(7, graph.shortestPathCost("A", "C"));
    // }

    // /**
    //  * Same graph as test 1 but with different start.
    //  */
    // @Test
    // void test2() {
    //     DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();

    //     graph.insertNode("A");
	// 	graph.insertNode("B");
	// 	graph.insertNode("C");
    //     graph.insertNode("D");
    //     graph.insertNode("E");
    //     graph.insertEdge("A", "B", 6);
	// 	graph.insertEdge("A", "D", 1);
    //     graph.insertEdge("B", "C", 5);
    //     graph.insertEdge("E", "C", 5);
    //     graph.insertEdge("D", "E", 1);
    //     graph.insertEdge("B", "D", 2);
    //     graph.insertEdge("B", "E", 2);
        
    //     Assertions.assertEquals(List.of("D", "E", "C"), graph.shortestPathData("D", "C"));
    //     Assertions.assertEquals(6, graph.shortestPathCost("D", "C"));
    // }

    // /**
    //  * Test functionality when there is no shortest path
    //  */
    // @Test
    // void test3() {
    //     DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();

    //     graph.insertNode("A");
	// 	graph.insertNode("B");
	// 	graph.insertNode("C");
    //     graph.insertNode("D");
    //     graph.insertNode("E");
    //     graph.insertEdge("A", "B", 6);
	// 	graph.insertEdge("A", "D", 1);
    //     graph.insertEdge("B", "C", 5);
    //     graph.insertEdge("E", "C", 5);
    //     graph.insertEdge("D", "E", 1);
    //     graph.insertEdge("B", "D", 2);
    //     graph.insertEdge("B", "E", 2);
        
    //     Assertions.assertThrows(NoSuchElementException.class, () -> graph.shortestPathCost("C", "E"));
    // }
}

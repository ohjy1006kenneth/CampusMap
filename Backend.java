// === CS400 Spring 2024 File Header Information ===
// Name: Kenneth Oh
// Email: oh87@wisc.edu
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;

public class Backend implements BackendInterface {
    private GraphADT<String, Double> graph;
    private List<String> allLocations = new ArrayList<String>();

    /*
    * Constructor for Backend
    * @param graph object to sture the backend's graph data
    */
    public Backend(GraphADT graph) {
        this.graph = graph;
    }

    /**
    * Loads graph data from a dot file.
    * @param filename the path to a dot file to read graph data from
    * @throws IOException if there was a problem reading in the specified file
    */
    public void loadGraphData(String filename) throws IOException {
        File file = new File(filename);
        if (file == null) {
            throw new IOException ("There was a problem reading in the specified file.");
        }
        Scanner sc = new Scanner(file);

        Pattern nodePattern = Pattern.compile("\"(.*?)\"");
        Pattern edgePattern = Pattern.compile("=(.*?)]");
        String pred = null;
        String succ = null;

        while (sc.hasNextLine()) {
            String[] arrOfStr = sc.nextLine().split("->", 2);
            if (arrOfStr.length == 2) {
                
                // Add pred node
                Matcher matcher = nodePattern.matcher(arrOfStr[0]);
                if (matcher.find()) {   
                    pred = matcher.group(1);
                    if (graph.insertNode(pred)) {
                        allLocations.add(pred);
                    }
                }

                // Add succ node
                matcher = nodePattern.matcher(arrOfStr[1]);
                if (matcher.find()) {   
                    succ = matcher.group(1);
                    if (graph.insertNode(succ)) {
                        allLocations.add(succ);
                    }
                }

                // Add edge
                matcher = edgePattern.matcher(arrOfStr[1]);
                if (matcher.find()) {   
                    Double weight = Double.parseDouble(matcher.group(1));
                    graph.insertEdge(pred, succ, weight);
                }
            }
        }
    }

    /**
    * Returns a list of all locations (nodes) available on the backend's graph.
    * @return list of all location names
    */
    public List<String> getListOfAllLocations() {
        return this.allLocations;
    }

  /**
   * Returns the sequence of locations along the shortest path from startLocation to endLocation, or
   * en empty list if no such path exists.
   * @param startLocation the start location of the path
   * @param endLocation the end location of the path
   * @return a list with the nodes along the shortest path from startLocation to endLocation, or
   *         an empty list if no such path exists
   */
  public List<String> findShortestPath(String startLocation, String endLocation) {
    return graph.shortestPathData(startLocation, endLocation);
  }

  /**
   * Returns the walking times in seconds between each two nodes on the shortest path from startLocation
   * to endLocation, or an empty list of no such path exists.
   * @param startLocation the start location of the path
   * @param endLocation the end location of the path
   * @return a list with the walking times in seconds between two nodes along the shortest path from
   *         startLocation to endLocation, or an empty list if no such path exists
   */
  public List<Double> getTravelTimesOnPath(String startLocation, String endLocation) {
    List<String> path = graph.shortestPathData(startLocation, endLocation);
    List<Double> cost = new ArrayList<Double>();

    for (int i = 0; i < (path.size() - 1); i++) {
        cost.add(graph.shortestPathCost(path.get(i), path.get(i + 1)));
    }

    return cost;
  }

  /**
   * Returns all locations that are reachable from startLocation in at most timesInSec walking time.
   * @param startLocation the maximum walking time for a destination to be included in the list
   */
  public List<String> getReachableLocations(String startLocation, double timesInSec) {
    List<String> reachableLocations = new ArrayList<String>();

    for (int i = 0; i < (allLocations.size() - 1); i++) {
        if (timesInSec <= graph.shortestPathCost(startLocation, this.allLocations.get(i))) {
            reachableLocations.add(this.allLocations.get(i));
        }
    }


    return reachableLocations;
  }
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Node {
  private int id;
  private List<Node> neighbors;
  private Map<Node, Integer> weights;

  public Node(int id) {
    this.id = id;
    neighbors = new ArrayList<>();
    weights = new HashMap<>();
  }

  public int getId() {
    return id;
  }

  public List<Node> getNeighbors() {
    return neighbors;
  }

  public void addNeighbor(Node neighbor, int weight) {
    neighbors.add(neighbor);
    weights.put(neighbor, weight);
  }

  public int getWeight(Node neighbor) {
    return weights.get(neighbor);
  }
}

class Graph {
  private List<Node> nodes;

  public Graph() {
    nodes = new ArrayList<>();
  }

  public void addNode(Node node) {
    nodes.add(node);
  }

  public Node getNodeById(int id) {
    for (Node node : nodes) {
      if (node.getId() == id) {
        return node;
      }
    }
    return null;
  }

  public List<Node> getNodes() {
    return nodes;
  }
}

class Dijkstra {

  public static void main(String[] args) {
    List<String> lines = readInputFromFile("cop3503-asn3-input.txt");
    if (lines.isEmpty()) {
      System.out.println("Input file is empty.");
      return;
    }

    int numVertices = Integer.parseInt(lines.get(0));
    int sourceVertex = Integer.parseInt(lines.get(1));
    int numEdges = Integer.parseInt(lines.get(2));

    List<String> edgeLines = lines.subList(3, 3 + numEdges);
    Graph graph = buildGraph(edgeLines);

    Node startNode = graph.getNodeById(sourceVertex);
    if (startNode == null) {
      System.out.println("Source vertex does not exist in the graph.");
      return;
    }

    Map<Node, Integer> distance = dijkstra(graph, startNode);
    Map<Node, Node> previous = calculatePreviousMap(graph, startNode);
    writeOutputToFile("cop3503-asn3-output-vishwanath-vignesh.txt", numVertices, distance, previous);
  }

  private static List<String> readInputFromFile(String filename) {
    List<String> lines = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(filename))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
          lines.add(line);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading input file: " + e.getMessage());
    }
    return lines;
  }

  private static Graph buildGraph(List<String> lines) {
    Graph graph = new Graph();

    for (String line : lines) {
      String[] parts = line.split("\\s+");
      int node1Id = Integer.parseInt(parts[0]);
      int node2Id = Integer.parseInt(parts[1]);
      int weight = Integer.parseInt(parts[2]);

      Node node1 = graph.getNodeById(node1Id);
      if (node1 == null) {
        node1 = new Node(node1Id);
        graph.addNode(node1);
      }

      Node node2 = graph.getNodeById(node2Id);
      if (node2 == null) {
        node2 = new Node(node2Id);
        graph.addNode(node2);
      }

      node1.addNeighbor(node2, weight);
      node2.addNeighbor(node1, weight);

      // Print the edge
      System.out.println("Edge added: " + node1Id + " -- " + node2Id + ", Weight: " + weight);
    }

    // Print the graph
    System.out.println("Graph:");
    for (Node node : graph.getNodes()) {
      System.out.print("Node " + node.getId() + ":");
      for (Node neighbor : node.getNeighbors()) {
        int weight = node.getWeight(neighbor);
        System.out.print(" (" + neighbor.getId() + ", " + weight + ")");
      }
      System.out.println();
    }

    return graph;
  }

  private static Map<Node, Integer> dijkstra(Graph graph, Node startNode) {
    Set<Node> visited = new HashSet<>();
    Map<Node, Integer> distance = new HashMap<>();
    Map<Node, Node> previous = new HashMap<>();

    // Initialize distance values to infinity
    for (Node node : graph.getNodes()) {
      distance.put(node, Integer.MAX_VALUE);
    }

    // Set distance of start node to 0
    distance.put(startNode, 0);

    while (!visited.containsAll(graph.getNodes())) {
      Node current = getMinDistanceNode(distance, visited);
      visited.add(current);

      for (Node neighbor : current.getNeighbors()) {
        if (!visited.contains(neighbor)) {
          int newDistance = distance.get(current) + current.getWeight(neighbor);

          if (newDistance < distance.get(neighbor)) {
            distance.put(neighbor, newDistance);
            previous.put(neighbor, current);
          }
        }
      }
    }

    // for unreachable nodes distance is -1
    for (Node node : graph.getNodes()) {
      if (node != startNode && !distance.containsKey(node)) {
        distance.put(node, -1);
        previous.put(node, null);
      }
    }

    return distance;
  }

  private static Node getMinDistanceNode(Map<Node, Integer> distance, Set<Node> visited) {
    Node minNode = null;
    int minDistance = Integer.MAX_VALUE;

    for (Map.Entry<Node, Integer> entry : distance.entrySet()) {
      Node node = entry.getKey();
      int dist = entry.getValue();

      if (dist < minDistance && !visited.contains(node)) {
        minNode = node;
        minDistance = dist;
      }
    }

    return minNode;
  }

  private static Map<Node, Node> calculatePreviousMap(Graph graph, Node startNode) {
//    Map<Node, Node> previous = new HashMap<>();
//    Queue<Node> queue = new LinkedList<>();
//
//    queue.add(startNode);
//    previous.put(startNode, null);
//
//    while (!queue.isEmpty()) {
//      Node current = queue.poll();
//
//      for (Node neighbor : current.getNeighbors()) {
//        if (!previous.containsKey(neighbor)) {
//          queue.add(neighbor);
//          previous.put(neighbor, current);
//        }
//      }
//    }
//
//    return previous;
    Set<Node> visited = new HashSet<>();
    Map<Node, Integer> distance = new HashMap<>();
    Map<Node, Node> previous = new HashMap<>();

    // Initialize distance values to infinity
    for (Node node : graph.getNodes()) {
      distance.put(node, Integer.MAX_VALUE);
    }

    // Set distance of start node to 0
    distance.put(startNode, 0);

    while (!visited.containsAll(graph.getNodes())) {
      Node current = getMinDistanceNode(distance, visited);
      visited.add(current);

      for (Node neighbor : current.getNeighbors()) {
        if (!visited.contains(neighbor)) {
          int newDistance = distance.get(current) + current.getWeight(neighbor);

          if (newDistance < distance.get(neighbor)) {
            distance.put(neighbor, newDistance);
            previous.put(neighbor, current);
          }
        }
      }
    }

    // for unreachable nodes distance is -1
    for (Node node : graph.getNodes()) {
      if (node != startNode && !distance.containsKey(node)) {
        distance.put(node, -1);
        previous.put(node, null);
      }
    }

    return previous;
  }

  private static void writeOutputToFile(String filename, int numVertices, Map<Node, Integer> distance,
      Map<Node, Node> previous) {
    try (FileWriter writer = new FileWriter(filename)) {
      writer.write(numVertices + "\n");

      List<Map.Entry<Node, Integer>> entries = new ArrayList<>(distance.entrySet());
      Collections.sort(entries, Comparator.comparingInt(entry -> entry.getKey().getId()));

      for (Map.Entry<Node, Integer> entry : entries) {
        Node node = entry.getKey();
        int dist = entry.getValue();
        int startNodeId;

            if (dist >= Integer.MAX_VALUE) {
                dist = -1; // Indicate unreachable nodes
                startNodeId = -1; // Set startNodeId to -1 for unreachable nodes
            } else if (dist == 0) {
                startNodeId = 0; // Start node itself
            } else {
                Node previousNode = previous.get(node);
                startNodeId = previousNode != null ? previousNode.getId() : -1;
            }

        writer.write(node.getId() + " " + dist + " " + startNodeId + "\n");
      }
    } catch (IOException e) {
      System.out.println("Error writing output file: " + e.getMessage());
    }
  }

}
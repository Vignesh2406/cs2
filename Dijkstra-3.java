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
    List<String> lines = readInputFromFile("cop3503-asn2-input.txt");
    if (lines.isEmpty()) {
      System.out.println("Input file is empty.");
      return;
    }

    int numVertices = Integer.parseInt(lines.get(0));
    int sourceVertexId = Integer.parseInt(lines.get(1));
    int numEdges = Integer.parseInt(lines.get(2));

    List<String> edgeLines = lines.subList(3, 3 + numEdges);
    Graph graph = buildGraph(edgeLines);

    Node sourceNode = graph.getNodeById(sourceVertexId);
    if (sourceNode == null) {
      System.out.println("Source vertex does not exist in the graph.");
      return;
    }

    // Dijkstra's Algorithm
    Map<Node, Integer> distance = dijkstra(graph, sourceNode);
    writeOutputToFileDijkstra("cop3503-asn2-output-Dijkstra.txt", numVertices, distance, sourceNode);

    // Bellman-Ford Algorithm
    int[][] distanceBF = bellmanFord(graph, sourceNode);
    Map<Node, Node> parentBF = calculatePreviousMap(distanceBF, graph.getNodes(), sourceNode);
    writeOutputToFileBellmanFord("cop3503-asn2-output-Bellman-Ford.txt", numVertices, distanceBF, parentBF, sourceNode,
        graph);

    // Floyd-Warshall Algorithm
    int[][] distanceFW = floydWarshall(graph);
    writeOutputToFileFloydWarshall("cop3503-asn2-output-Floyd-Warshall.txt", numVertices, distanceFW);
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
    }

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

  private static Map<Node, Node> calculatePreviousMap(int[][] distance, List<Node> nodes, Node sourceNode) {
    Map<Node, Node> parent = new HashMap<>();
    int sourceIndex = sourceNode.getId() - 1;

    for (Node node : nodes) {
      int nodeIndex = node.getId() - 1;
      if (distance[sourceIndex][nodeIndex] != Integer.MAX_VALUE) {
        parent.put(node, sourceNode);
      } else {
        for (Node neighbor : node.getNeighbors()) {
          int neighborIndex = neighbor.getId() - 1;
          if (distance[sourceIndex][neighborIndex] != Integer.MAX_VALUE) {
            parent.put(node, neighbor);
            break;
          }
        }
      }
    }

    return parent;
  }

  private static Map<Node, Integer> dijkstra(Graph graph, Node startNode) {
    Set<Node> visited = new HashSet<>();
    Map<Node, Integer> distance = new HashMap<>();

    // Initialize distance values to infinity for all nodes except the start node
    for (Node node : graph.getNodes()) {
      distance.put(node, node == startNode ? 0 : Integer.MAX_VALUE);
    }

    while (!visited.containsAll(graph.getNodes())) {
      Node current = getMinDistanceNode(distance, visited);
      visited.add(current);

      for (Node neighbor : current.getNeighbors()) {
        if (!visited.contains(neighbor)) {
          int newDistance = distance.get(current) + current.getWeight(neighbor);

          if (newDistance < distance.get(neighbor)) {
            distance.put(neighbor, newDistance);
          }
        }
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

  private static int[][] bellmanFord(Graph graph, Node sourceNode) {
    int numVertices = graph.getNodes().size();
    int[][] distance = new int[numVertices][numVertices];

    // Initialize distances to infinity
    for (int i = 0; i < numVertices; i++) {
      Arrays.fill(distance[i], Integer.MAX_VALUE);
      distance[i][i] = 0;
    }

    // Set distances based on existing edges
    for (Node node : graph.getNodes()) {
      int i = node.getId() - 1;
      for (Node neighbor : node.getNeighbors()) {
        int j = neighbor.getId() - 1;
        distance[i][j] = node.getWeight(neighbor);
      }
    }

    // Bellman-Ford algorithm
    for (int k = 0; k < numVertices; k++) {
      for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
          if (distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE
              && distance[i][k] + distance[k][j] < distance[i][j]) {
            distance[i][j] = distance[i][k] + distance[k][j];
          }
        }
      }
    }

    return distance;
  }

  private static void writeOutputToFileBellmanFord(String filename, int numVertices, int[][] distance,
      Map<Node, Node> parent, Node sourceNode, Graph graph) {
    try (FileWriter writer = new FileWriter(filename)) {
      writer.write(numVertices + "\n");

      for (Node node : graph.getNodes()) {
        int dist = distance[sourceNode.getId() - 1][node.getId() - 1];
        int parentId = (node == sourceNode) ? 0 : (parent.containsKey(node) ? parent.get(node).getId() : -1);

        writer.write(node.getId() + " " + dist + " " + parentId + "\n");
      }
    } catch (IOException e) {
      System.out.println("Error writing output file: " + e.getMessage());
    }
  }

  // New method for Floyd-Warshall output
  private static void writeOutputToFileDijkstra(String filename, int numVertices, Map<Node, Integer> distance,
      Node sourceNode) {
    try (FileWriter writer = new FileWriter(filename)) {
      writer.write(numVertices + "\n");

      List<Node> nodes = new ArrayList<>(distance.keySet());
      Collections.sort(nodes, Comparator.comparingInt(Node::getId));

      for (Node node : nodes) {
        int dist = distance.getOrDefault(node, -1);
        int parentId = (node == sourceNode) ? 0 : -1;
        Node parent = null;

        if (dist != 0) {
          // Find the parent node by tracing back using the distance map
          int minDistance = Integer.MAX_VALUE;
          for (Node neighbor : node.getNeighbors()) {
            if (distance.containsKey(neighbor) && distance.get(neighbor) < minDistance) {
              minDistance = distance.get(neighbor);
              parent = neighbor;
            }
          }
          parentId = parent != null ? parent.getId() : -1;
        }

        writer.write(node.getId() + " " + dist + " " + parentId + "\n");
      }
    } catch (IOException e) {
      System.out.println("Error writing Dijkstra output file: " + e.getMessage());
    }
  }

  private static void writeOutputToFileFloydWarshall(String filename, int numVertices, int[][] distance) {
    try (FileWriter writer = new FileWriter(filename)) {
      writer.write(numVertices + "\n");

      for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
          writer.write(distance[i][j] + " ");
        }
        writer.write("\n");
      }
    } catch (IOException e) {
      System.out.println("Error writing Floyd-Warshall output file: " + e.getMessage());
    }
  }

  private static int[][] floydWarshall(Graph graph) {
    int n = graph.getNodes().size();
    int[][] distance = new int[n][n];

    // Initialize distances between all pairs of vertices
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i == j) {
          distance[i][j] = 0;
        } else {
          distance[i][j] = Integer.MAX_VALUE;
        }
      }
    }

    // Set distances based on existing edges
    for (Node node : graph.getNodes()) {
      int i = node.getId() - 1;
      for (Node neighbor : node.getNeighbors()) {
        int j = neighbor.getId() - 1;
        distance[i][j] = node.getWeight(neighbor);
      }
    }

    // Floyd-Warshall algorithm
    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE
              && distance[i][k] + distance[k][j] < distance[i][j]) {
            distance[i][j] = distance[i][k] + distance[k][j];
          }
        }
      }
    }

    return distance;
  }
}

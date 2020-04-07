import java.util.*;

class Node {
    public int data;
    public List<Edge> neighbors;
    public boolean visited;
  
    public Node(int data) {
      this.data = data;
      this.neighbors = new ArrayList<>();
      this.visited = false;
    }
}

class Edge{
    public Node dest;
    public int weight;

    public Edge(Node n, int w){
        this.dest = n;
        this.weight = w;
    }
}



public class WeightedGraph 
{ 

    public static void main(String args[]) 
	{ 
        //Test C
        WeightedGraph graph = new WeightedGraph();
        System.out.println("testC - make random graph");

        WeightedGraph testC = graph.createRandomCompleteWeightedGraph(10);
        for(Node v : testC.vertices){  
            System.out.print(v.data + " ");
        }

        //Test D
        System.out.println("\n\ntestD - make linked list graph");
        WeightedGraph testD = graph.createLinkedList(10);
        for(Node v : testD.vertices){
            System.out.print(v.data + " ");
        }

        //Test E  - WARNING!!!!!!! - this runs infinite loop - unfound bug
        System.out.println("\n\ntestE - Dijkstras");
        WeightedGraph testE = graph.createLinkedList(10);
        HashMap<Node, Integer> toPrint =  testE.dijkstras(testE.vertices.get(1));
        
    }



    public List<Node> vertices;
    public WeightedGraph() {
        this.vertices = new ArrayList<>();
    }

    //B - i - done
    void addNode(final int nodeVal){ //adds new node to the graph
        vertices.add(new Node(nodeVal) );
    }

    //B - ii - done
    void addWeightedEdge(final Node first, final Node second, final int edgeWeight) { //adds directed weighted edge between first and second 
        Edge e = new Edge(second, edgeWeight);
        first.neighbors.add(e);
        
    }

    //B - iii - done
    void removeDirectedEdge(final Node first, final Node second){ //removes an directed weighted edge between first and second 
        for(Edge e : first.neighbors){
            if(e.dest.equals(second)){
                first.neighbors.remove(e);
            }
        }

    }

    //B - iv - done
    HashSet<Node> getAllNodes(){  //returns a set of all Nodes in the Graph
        HashSet<Node> allNodesInGraph = new HashSet<Node>();
        for(Node x: vertices){
            allNodesInGraph.add(x);
        }
        return allNodesInGraph;
         
    }

    //---------------------------------------------------------------------------------------------------

    //C - done 
    WeightedGraph createRandomCompleteWeightedGraph(final int num){
        //create num nodes with randomly assigned weighted (positive int), directed edges to EVERY OTHER NODE
        WeightedGraph graph = new WeightedGraph();
        Random rand = new Random();

        for(int i = 0; i <= num; i++){
            graph.addNode(i); //create all nodes in graph
        }

        for(int i = 0; i < graph.vertices.size(); i++){
            Node vertex = graph.vertices.get(i); //grabs first node

            for(int j = 0; j < graph.vertices.size(); j++){
                //iterate everything
                if(i == j){
                    continue; //skip this connection bc weight will be 0
                }
                else{
                    //for all other nodes in graph
                    //make an edge to be thrown into vertex.neighbors
                    Node tempNeighbor = graph.vertices.get(j); 
                    Edge e = new Edge(tempNeighbor, rand.nextInt(10));
                    vertex.neighbors.add(e);
                }
            }
        }
    

        return graph;
    }

    //D - done 
    WeightedGraph createLinkedList(final int num){
        //make num nodes, weighted graph (uniform weight)
        WeightedGraph graph = new WeightedGraph();

        for(int i = 0; i <= num; i++){
            if(i == 0){
                //base case - first node
                graph.addNode(i);
            }
            else{
                //already at least 1 node in graph
                graph.addNode(i);
                Edge e = new Edge(graph.vertices.get(i), 1);
                Node prevNode = graph.vertices.get(i-1);
                prevNode.neighbors.add(e);
            }
        }

        return graph;
    }

    //E - not done - not tested
    HashMap<Node, Integer> dijkstras(final Node start){
        HashMap<Node, Integer> fullMap = new HashMap<Node, Integer>();
        int whileBreaker = 0;
        Node curr = start;

        fullMap.put(curr, 0);

        while(fullMap.get(curr) != Integer.MAX_VALUE){
            curr.visited = true;
            

            if(whileBreaker == 100){ //CHANGE 100 BASED ON HOW MANY NODES IN GRAPH (multiply by 10) 
                break;                // so if 10 nodes in graph, then if should equal 100
            }

            int nextCurrVal = Integer.MAX_VALUE;
            for(Edge e : curr.neighbors){

                fullMap.putIfAbsent(e.dest, Integer.MAX_VALUE); //for all edges, if not in map, add it
                if(e.dest.visited == false){  //not visited
                    if((fullMap.get(curr) + e.weight) < fullMap.get(e.dest)){  //update distance
                        fullMap.put(e.dest, (fullMap.get(curr) + e.weight)); 
                    }

                    if(fullMap.get(e.dest) < nextCurrVal){  //find next smallest
                        System.out.print(curr.data + " ");
                        curr = e.dest;
                        nextCurrVal = fullMap.get(e.dest);
                    }
                }
                else{
                    //visited
                    System.out.print(whileBreaker + " ");
                    whileBreaker++;
                }

            }
        }

        return fullMap;
    }

    

}





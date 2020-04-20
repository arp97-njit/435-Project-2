import java.util.*;


/**
 * Graph class runs the main and creates the graph object
 * Functions:
 *  A I   - addNode
 *  A II  - addUndirectedEdge
 *  A III - removeUndirectedEdge
 *  A IV  - getAllNodes
 *  B     - createRandomUnweightedGraph
 *  C     - createLinkedList
 */
public class Graph 
{ 

    public List<Node> vertices;
    public HashSet<Node> allNodesInGraph;
        //code does not fully use this yet, work in progress
    public Graph() {
        this.vertices = new ArrayList<>();
        this.allNodesInGraph = new HashSet<Node>();
    }

    public static void main(String args[]) 
	{ 
        Graph graph = new Graph();
        GraphSearch gSearch = new GraphSearch();

        Graph testGraph = graph.createRandomUnweightedGraphIter(10);
        Graph testLinkedList = graph.createLinkedList(10);

        //A
        System.out.println("\nA - getHashSet");
        HashSet<Node> A = testGraph.getAllNodes();
        for(Node n: A){
            System.out.print(n.data + " ");
        }

        //B
        System.out.println("\nB - createRandomUnweightedGraphIter");
        for(Node n : testGraph.vertices){
            System.out.print(n.data + " :[" );
            for(Node neigh : n.neighbors){
                System.out.print(neigh.data + ", ");
            }
            System.out.print("]\n");
        }

        //C
        System.out.println("\nC - createLinkedList");
        for(Node n : testLinkedList.vertices){
            System.out.print(n.data + " ");
        }


        //D
        System.out.println("\n\nD - DFSRec");
        ArrayList<Node> testD = gSearch.DFSRec(testLinkedList.vertices.get(0), testLinkedList.vertices.get(3));
        for(Node n : testD){
            System.out.print(n.data + " ");
        }
        System.out.println("\n - actual graph");
        ArrayList<Node> testDrg = gSearch.DFSRec(testGraph.vertices.get(0), testGraph.vertices.get(2)); 
        for(Node n : testDrg){
            System.out.print(n.data + " ");
        }

        graph.rest(testGraph);
        graph.rest(testLinkedList);

        //E
        System.out.println("\n\nE - DFSIter");
        ArrayList<Node> testE = gSearch.DFSIter(testLinkedList.vertices.get(1), testLinkedList.vertices.get(4));
        for(Node n : testE){
            System.out.print(n.data + " ");
        }
        System.out.println("\n - actual graph");
        ArrayList<Node> testErg = gSearch.DFSRec(testGraph.vertices.get(0), testGraph.vertices.get(3)); 
        for(Node n : testErg){
            System.out.print(n.data + " ");
        }

        graph.rest(testGraph);
        graph.rest(testLinkedList);

        //F
        System.out.println("\n\nF - BFTRec");
        ArrayList<Node> testF = gSearch.BFTRec(testLinkedList);
        for(Node n : testF){
            System.out.print(n.data + " ");
        }
        System.out.println("\n - actual graph");
        ArrayList<Node> testFrg = gSearch.BFTRec(testGraph); 
        for(Node n : testFrg){
            System.out.print(n.data + " ");
        }

        graph.rest(testGraph);
        graph.rest(testLinkedList);

        //G
        System.out.println("\n\nG - BFTIter");
        ArrayList<Node> testG = gSearch.BFTIter(testLinkedList);
        for(Node n : testG){
            System.out.print(n.data + " ");
        }
        System.out.println("\n - actual graph");
        ArrayList<Node> testGrg = gSearch.BFTIter(testGraph); 
        for(Node n : testGrg){
            System.out.print(n.data + " ");
        }

        //H
        System.out.println("\n\nH - BFTRecLinkedList");
        ArrayList<Node> testH = gSearch.BFTRecLinkedList(8);
        for(Node n : testH){
            System.out.print(n.data + " ");
        }

        //I
        System.out.println("\n\nI - BFTIterLinkedList");
        ArrayList<Node> testI = gSearch.BFTIterLinkedList(10);
        for(Node n : testI){
            System.out.print(n.data + " ");
        }
		
    } 

    void rest(Graph testgraph){
        for(Node n : testgraph.vertices){
            n.visited = false;
        }
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------------------------

    //A - i - done
    void addNode(final int nodeVal){ 
        vertices.add(new Node(nodeVal) );
        allNodesInGraph.add(new Node(nodeVal) );
    }

    //A - ii - done
    void addUndirectedEdge(final Node first, final Node second) { 
        if((!first.neighbors.contains(second)) && (!second.neighbors.contains(first)) && (vertices.size() >= 2) && (!first.equals(second))){
            first.neighbors.add(second);
            second.neighbors.add(first);
        }
        
        
    }

    //A - iii - done
    void removeUndirectedEdge(final Node first, final Node second){ 
        if(first.neighbors.contains(second)){
            first.neighbors.remove(second);
        }
        if(second.neighbors.contains(first)){
            second.neighbors.remove(first);
        }

    }

    //A - iv - done
    HashSet<Node> getAllNodes(){  
        return allNodesInGraph;
         
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------

    //B - Done
    Graph createRandomUnweightedGraphIter(int n){
        
        Graph graph = new Graph();

        int counter = 0;
        Random rand = new Random();
        int randNum = rand.nextInt(n * 15);

        //create n nodes, counter denotes the actual nodes created
        while(counter != n){

            boolean isInGraph = createGraphHelper(graph, randNum);
            if(isInGraph == false){
                graph.addNode(randNum);
                counter ++;
            }

            //need at least 2 nodes to make connection
            if(graph.vertices.size() >= 2){
                //make sure the random indexes you will use to make a connection are not equal
                int randIndex1 = 0;
                int randIndex2 = 0;
                while(randIndex1 == randIndex2){ 
                    randIndex1 = rand.nextInt(graph.vertices.size());
                    randIndex2 = rand.nextInt(graph.vertices.size());
                }
                //connection
                graph.addUndirectedEdge(graph.vertices.get(randIndex1), graph.vertices.get(randIndex2));
            }
            randNum = rand.nextInt(n * 10);
        }

        return graph;

    }

    boolean createGraphHelper(Graph g, int randNum){
    //makes sure randNum is not a vertex in the current graph
        for(Node v :g.vertices){  
            if(v.data == randNum){
                return true;
            }
        }
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //C - done
    Graph createLinkedList(int n){
    
        Graph graph = new Graph();

        //instead of random values to insert, there won't be a duplicate if just enter 0-n
        for (int i = 0; i < n; i++){
            if(i == 0){
                //base case - add first node
                graph.addNode(i);
            }
            else{ //there is already at least 1 node in graph
                graph.addNode(i);
                graph.addUndirectedEdge(graph.vertices.get(i-1), graph.vertices.get(i)); //connection
            }
        }
        
        return graph;  //return the graph

    }

    
}

//-------------------------------------------------------------------------------------------------------------------------------------------------------

/** 
 *Class Node is an object that will be placed in the graph
 *Each Node has a value
 *A flag to denote if it has been visited or not
 *A list of neighbors - all neighbors are also Nodes 
 */
class Node {
    public int data;
    public List<Node> neighbors;
    public boolean visited;
  
    public Node(int data) {
      this.data = data;
      this.neighbors = new ArrayList<Node>();
      this.visited = false;
    }
}

//-------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 * GraphSeach class contains path finding algorithms
 * D - DFS - recurisve
 *      Does DFS with input start node, end node which we are trying to find
 * E - DFS - iterative 
 *      Does DFS with input start node, end node which we are trying to find
 * F - BFT - recursive
 *      Does BFT with input of a graph object
 * G - BFT - iterative
 *      Does BFT with input of a graph object
 * H - BFTRecLinkedList 
 *      Does a BFT recurively with an input of linked list
 * I - BFTIterlinkedList
 *      Does a BFT iteratively with an input of linked list
 */
class GraphSearch{

    //D - DONE
    ArrayList<Node> DFSRec(final Node start, final Node end){

        ArrayList<Node> returnPath = new ArrayList<Node>();

        dfsRecHelper(start, end, returnPath);

        if(returnPath.contains(end) == false){
            return null;
        }

        return returnPath;
    }

    void dfsRecHelper(final Node start, final Node end, ArrayList<Node> path){
        //mark as visited
        start.visited = true;
        path.add(start);

        if(start.data == end.data){
            //if we found our end, return 
            return;
        }
        //since we didn't find end, go through all neighbors of the current node as long as they weren't visited
        for(Node n : start.neighbors){
            if(n.visited == false && path.contains(n) == false){
                dfsRecHelper(n, end, path);
            }
        }

    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------

    // E - DONE
    ArrayList<Node> DFSIter(final Node start, final Node end){
        ArrayList<Node> returnPath = new ArrayList<Node>();
        Stack<Node> stack = new Stack<Node>(); 

        if(start.data == end.data){
            //if we found our end, return 
            returnPath.add(start);
            return returnPath;
        }

        //mark as visited
        start.visited = true;
        stack.push(start);

        while(!stack.isEmpty()){
            Node curr = stack.pop();
            returnPath.add(curr);

            if(curr.data == end.data){
                //found our end, so break out and return the path
                break;
            }
            //add all unvisited neighbors to the stack
            for (Node n : curr.neighbors){
                if(n.visited == false){
                    n.visited = true;
                    stack.push(n);
                }
            }
        }

        return returnPath;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------

    //F - DONE 

    ArrayList<Node> BFTRec(final Graph graph){

        Queue<Node> qBFTRec = new LinkedList<>();
        ArrayList<Node> returnPathBFTRec = new ArrayList<Node>();

        for(Node v : graph.vertices){  //goes through everything - handles disconnected graph
            if(v.visited == false){
                v.visited = true;
                qBFTRec.add(v);
                bftRecHelper(qBFTRec, returnPathBFTRec);
            }
        }

        return returnPathBFTRec;

    }

    void bftRecHelper(Queue<Node> q, ArrayList<Node> path){
        if(q.isEmpty()){
            return;
        }

        Node curr = q.poll();  //dequeue
        path.add(curr);

        for(Node n: curr.neighbors){  //go through ever neighbor and add unvisited nodes to the queue
            if(n.visited == false){
                n.visited = true;
                q.add(n);
            }
        }
        bftRecHelper(q, path);
        

    }

    //----------------------------------------------------------------------------------------

    // G - DONE
    ArrayList<Node> BFTIter(final Graph graph){
        ArrayList<Node> returnPath = new ArrayList<Node>();
        Queue<Node> q = new LinkedList<>();

        for(Node v: graph.vertices){ //goes through everything - handles disconnected graph

            if(v.visited == false){
                //mark as visited
                v.visited = true;
                q.add(v);

                while(q.isEmpty() == false){
                    Node curr = q.poll();  //curr will have the correct order
                    returnPath.add(curr); //returnPath gets each node

                    for(Node n: v.neighbors){ //go to all unvisited neighbors
                        if(n.visited == false){
                            n.visited = true;
                            q.add(n);  //add to queue
                        }
                    }
                }
            }
        }

        return returnPath;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------

    //H -DONE 
    ArrayList<Node> BFTRecLinkedList(final int n){
        Graph graph = new Graph();
        return BFTRec(graph.createLinkedList(n));
    }



    //----------------------------------------------------------------------------------------

    //I - DONE 
    ArrayList<Node> BFTIterLinkedList(final int n){
        Graph graph = new Graph();
        return BFTIter(graph.createLinkedList(n));

    }



}






import java.util.*;

class Node {
    public int data;
    public List<Node> neighbors;
    public boolean visited;
  
    public Node(int data) {
      this.data = data;
      this.neighbors = new ArrayList<>();
      this.visited = false;
    }
}

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
        //process v
        path.add(start);
        if(start.data == end.data){
            //if we found our end, make sure it was added to path and return path
            return;

        }
        for(Node n : start.neighbors){
            if(n.visited == false){
                dfsRecHelper(n, end, path);
            }
        }

    }


    // E - DONE
    ArrayList<Node> DFSIter(final Node start, final Node end){
        ArrayList<Node> returnPath = new ArrayList<Node>();
        Stack<Node> s = new Stack<Node>(); 

        if(start.data == end.data){
            returnPath.add(start);
            return returnPath;
        }

        start.visited = true;
        s.push(start);

        while(s.isEmpty() == false){
            Node curr = s.pop();
            returnPath.add(curr);
            if(curr.data == end.data){
                break;
            }
            for (Node n : curr.neighbors){
                if(n.visited == false){
                    n.visited = true;
                    s.push(n);
                }
            }
        }

        return returnPath;
    }

    //F - DONE ----------------------------------------------------------------------------

    ArrayList<Node> BFTRec(final Graph graph){

        Queue<Node> qBFTRec = new LinkedList<>();
        ArrayList<Node> returnPathBFTRec = new ArrayList<Node>();

        for(Node v : graph.vertices){  //goes through everything - 10,000
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


        Node curr = q.poll();
        path.add(curr);

        for(Node n: curr.neighbors){  //go through ever neighbor, so even in a linked list - 10,000
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
        for(Node v: graph.vertices){
            if(v.visited == false){
                v.visited = true;
                q.add(v);
                while(q.isEmpty() == false){
                    Node curr = q.poll();  //curr will have the correct order
                    
                    returnPath.add(curr); //returnPath gets each node

                    for(Node n: v.neighbors){
                        if(n.visited == false){
                            n.visited = true;
                            q.add(n);  //adds all neighbors to queue
                        }
                    }
                }
            }
        }

        return returnPath;
    }

    //H -DONE - kind of - depends on BFTRec ----------------------------------------------------------------------------
    ArrayList<Node> BFTRecLinkedList(final int n){
        //can screenshot to help explaination
        //should run a BFT recursively on a linked list
        //10,000 nodes in linked list
        Graph graph = new Graph();
        return BFTRec(graph.createLinkedList(n));
    }



    //----------------------------------------------------------------------------------------

    //I - DONE ----------------------------------------------------------------------------
    ArrayList<Node> BFTIterLinkedList(final int n){
        //should run BFT iteratively on linked list
        //10,000 nodes in linked list
        Graph graph = new Graph();
        return BFTIter(graph.createLinkedList(n));

    }



}



public class Graph 
{ 
    public List<Node> vertices;
    public Graph() {
        this.vertices = new ArrayList<>();
    }

    //A - i - done
    void addNode(final int nodeVal){ //adds new node to the graph
        vertices.add(new Node(nodeVal) );
    }

    //A - ii - done
    void addUndirectedEdge(final Node first, final Node second) { //adds undirected edge between first and second (and vice versa)
        //so bi - directional
        if((!first.neighbors.contains(second)) && (!second.neighbors.contains(first)) && (vertices.size() >= 2)){
            first.neighbors.add(second);
            second.neighbors.add(first);
        }
        
        
    }

    //A - iii - done
    void removeUndirectedEdge(final Node first, final Node second){ //removes an undirected edge between first and second (and vice versa)
        first.neighbors.remove(second);
        second.neighbors.remove(first);

    }

    //A - iv - done
    HashSet<Node> getAllNodes(){  //returns a set of all Nodes in the Graph
        HashSet<Node> allNodesInGraph = new HashSet<Node>();
        for(Node x: vertices){
            allNodesInGraph.add(x);
        }
        return allNodesInGraph;
         
    }

    //B - NOT DONE
    //FIX ----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    Graph createRandomUnweightedGraphIter(int n){
        
        Graph graph = new Graph();

        int counter = 0;
        Random rand = new Random();
        int randNum = rand.nextInt(n * 10);

        while(counter != n){

            boolean isInGraph = createGraphHelper(graph, randNum);
            if(isInGraph == false){
                graph.addNode(randNum);
                counter ++;
            }

            //need at least 2 nodes to make connection
            if(graph.vertices.size() >= 2){
                //2 or more nodes in graph
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

        for(Node v :g.vertices){  //makes sure randNum is not a vertex in the current graph
            if(v.data == randNum){
                return true;
            }
        }
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //C - done
    Graph createLinkedList(int n){
        //create graph with n nodes
        //each node has only 1 edge to the next node created
        //node 1 <-> node 2 <-> node 3 etc...
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

    

	public static void main(String args[]) 
	{ 
        Graph graph = new Graph();
        GraphSearch gSearch = new GraphSearch();

        Graph testB = graph.createRandomUnweightedGraphIter(5);
        //A
        System.out.println("\nA - getHashSet");
        HashSet<Node> B = testB.getAllNodes();
        for(Node n: B){
            System.out.print(n.data + " ");
        }

        //B
        System.out.println("\nB - createRandomUnweightedGraphIter");
        // Graph testB = graph.createRandomUnweightedGraphIter(5);
        for(Node n : testB.vertices){
            System.out.print(n.data + " :[" );
            for(Node neigh : n.neighbors){
                System.out.print(neigh.data + ", ");
            }
            System.out.print("]\n");
        }

        //C
        System.out.println("\nC - createLinkedList");
        Graph testC = graph.createLinkedList(3);
        for(Node n : testC.vertices){
            System.out.print(n.data + " ");
        }

        //D
        System.out.println("\n\nD - DFSRec");
        Graph testDGraph = graph.createLinkedList(4);
        ArrayList<Node> testD = gSearch.DFSRec(testDGraph.vertices.get(0), testDGraph.vertices.get(3));
        for(Node n : testD){
            System.out.print(n.data + " ");
        }
        System.out.println("\n - actual graph");
        Graph testDRealGraph = graph.createRandomUnweightedGraphIter(4);
        ArrayList<Node> testDrg = gSearch.DFSRec(testDRealGraph.vertices.get(0), testDRealGraph.vertices.get(2)); 
        for(Node n : testDrg){
            System.out.print(n.data + " ");
        }

        //E
        System.out.println("\n\nE - DFSIter");
        Graph testEGraph = graph.createLinkedList(5);
        ArrayList<Node> testE = gSearch.DFSIter(testEGraph.vertices.get(1), testEGraph.vertices.get(4));
        for(Node n : testE){
            System.out.print(n.data + " ");
        }
        System.out.println("\n - actual graph");
        Graph testERealGraph = graph.createRandomUnweightedGraphIter(5);
        ArrayList<Node> testErg = gSearch.DFSRec(testERealGraph.vertices.get(0), testERealGraph.vertices.get(3)); 
        for(Node n : testErg){
            System.out.print(n.data + " ");
        }

        //F
        System.out.println("\n\nF - BFTRec");
        Graph testFGraph = graph.createLinkedList(6);
        ArrayList<Node> testF = gSearch.BFTRec(testFGraph);
        for(Node n : testF){
            System.out.print(n.data + " ");
        }
        System.out.println("\n - actual graph");
        Graph testFRealGraph = graph.createRandomUnweightedGraphIter(4);
        ArrayList<Node> testFrg = gSearch.BFTRec(testFRealGraph); 
        for(Node n : testFrg){
            System.out.print(n.data + " ");
        }

        //G
        System.out.println("\n\nG - BFTIter");
        Graph testGGraph = graph.createLinkedList(7);
        ArrayList<Node> testG = gSearch.BFTIter(testGGraph);
        for(Node n : testG){
            System.out.print(n.data + " ");
        }
        System.out.println("\n - actual graph");
        Graph testGRealGraph = graph.createRandomUnweightedGraphIter(4);
        ArrayList<Node> testGrg = gSearch.BFTIter(testGRealGraph); 
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
}


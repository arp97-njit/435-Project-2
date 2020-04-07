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



public class DirectedGraph 
{ 

    public static void main(String args[]) 
	{ 
        //Test C
        DirectedGraph graph = new DirectedGraph();
        System.out.println("testC - make random graph\n");

        DirectedGraph testC = graph.createRandomDAGIter(10);
        for(Node v : testC.vertices){  
            System.out.print(v.data + " ");
        }

        //Test D - Kahns not implemented 

        //Test E
        System.out.println("testE - mDFS\n");
        DirectedGraph testE = graph.createRandomDAGIter(1000);
        topSort ts = new topSort();
        ArrayList<Node> toPrint =  ts.mDFS(testE);
        for(Node v : toPrint){
            System.out.print(v.data + " ");
        } 

    }



    public List<Node> vertices;
    public DirectedGraph() {
        this.vertices = new ArrayList<>();
    }

    //B - i - done
    void addNode(final int nodeVal){ //adds new node to the graph
        vertices.add(new Node(nodeVal) );
    }

    //B - ii - done
    void addDirectedEdge(final Node first, final Node second) { //adds directed edge between first and second 
        first.neighbors.add(second);
        
    }

    //B - iii - done
    void removeDirectedEdge(final Node first, final Node second){ //removes an directed edge between first and second 
        first.neighbors.remove(second);

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
    DirectedGraph createRandomDAGIter(final int num){
        //create n random nodes with randomly assigned unweighted, directed edges
        DirectedGraph graph = new DirectedGraph();

        int counter = 0;
        Random rand = new Random();
        int randNum = rand.nextInt(num * 10);

        while(counter != num){ //create all random nodes
            boolean isInGraph = createGraphHelper(graph, randNum);
            if(isInGraph == false){
                graph.addNode(randNum);
                counter ++;
            }
            randNum = rand.nextInt(num * 10);
        }
        
        //make DAG connections
        for(int i = 0; i < graph.vertices.size()-2; i++){

            int randNumberOfConnections = rand.nextInt(graph.vertices.size() - i ); //try to make this many connections
            Node n = graph.vertices.get(i);
            int tempCounter = 0;
            int randIndex = rand.nextInt(graph.vertices.size() - i - 1) + i + 1;  //here is an random index from current to the end


            while(tempCounter != randNumberOfConnections){

                if(!n.neighbors.contains(graph.vertices.get(randIndex))){   
                    graph.addDirectedEdge(n, graph.vertices.get(randIndex));
                    tempCounter ++;
                }
                    
                randIndex = rand.nextInt(graph.vertices.size() - i - 1) + i + 1;
                    
            }
            
        }


        return graph;
    }

    boolean createGraphHelper(DirectedGraph g, int randNum){
        for(Node v : g.vertices){
            if(v.data == randNum){
                return true;
            }
        }
        return false;
    }

}


class topSort{


    //D - not done
    //call this method on createRandomDAGIter(1000)
    //should do valid topological sort
    ArrayList<Node> Kahns(final DirectedGraph graph){
        ArrayList<Node> path = new ArrayList<Node>();
        

        return path;
    }

    //-----------------------------------------------------------------------------------------

    //E - done
    //call this method on createRandomDAGIter(1000)
    ArrayList<Node> mDFS(final DirectedGraph graph){
        ArrayList<Node> path = new ArrayList<Node>();
        Stack<Node> stack = new Stack<Node>();

        for(Node v : graph.vertices){ //go through all nodes in graph
            if(v.visited == false){
                mDFSHelper(v, stack);
            }
        }

        while(!stack.empty()){ //empty the stack 
            path.add(stack.pop());
        }

        return path;
    }

    void mDFSHelper(Node n, Stack<Node> s){  //does DFS on all neighbors
        n.visited = true; 
        for(Node neigh : n.neighbors){
            if(neigh.visited == false){
                mDFSHelper(neigh, s);
            }
        }
        s.push(n); //once processed, push onto stack 
    }
}


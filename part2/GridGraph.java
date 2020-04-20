import java.lang.reflect.Array;
import java.util.*;

class GridNode {
    public int data;
    public List<GridNode> neighbors;
    public boolean visited;
    public int x;
    public int y;
    public boolean needToBackTrack;
  
    public GridNode(int x, int y,int data) {
      this.data = data;
      this.x = x;
      this.y = y;
      this.neighbors = new ArrayList<>();
      this.visited = false;
      this.needToBackTrack = false;
    }
}




public class GridGraph 
{ 

    public static void main(String args[]) 
	{ 
        //B test
        System.out.println("B test------------");
        int Bsize = 5;
        GridGraph testB = new GridGraph(Bsize);
        GridGraph graphB = testB.createRandomGridGraph(Bsize);

        for(int x = 0; x < Bsize; x++){
            for(int y = 0; y < Bsize; y++){
                System.out.print(graphB.grid[x][y].data + " ");
            }
            System.out.println("");

        }
        HashSet<GridNode> printHash = graphB.getAllNodes();
        for(GridNode n : printHash){
            System.out.print(n.data + " [");
            for(GridNode v : n.neighbors){
                System.out.print(v.data +", ");
            }
            System.out.print("]\n");

        }

        //D test - WARNING!!!!!!! - this runs infinite loop - unfound bug
        System.out.println("D test------------");
        int Dsize = 100;
        GridGraph testD = new GridGraph(Dsize);
        GridGraph graphD = testD.createRandomGridGraph(Dsize);
        
        GridNode sourceNode = graphD.grid[0][0];
        GridNode destNode = graphD.grid[99][99];

        ArrayList<GridNode> printPath= graphD.astar(sourceNode, destNode);
        for(GridNode n : printPath){
            System.out.print(n.data + " ");
        }


    }

    public GridNode [][] grid;


    public GridGraph(int n) {
        this.grid = new GridNode[n][n];
    }

    //A - i - done
    void addNode(final int x, final int y, final int nodeVal){ //adds new node to the graph

        grid[x][y] = new GridNode(x, y, nodeVal);

    }

    //A - ii - done
    void addUndirectedEdge(final GridNode first, final GridNode second) { //adds undirected edge between first and second 

        if(first.x == (second.x-1)){ //check if neighbor - left
            first.neighbors.add(second);
            second.neighbors.add(first);
            
        }
        else if(first.x == (second.x+1)){ //neighbor - right
            first.neighbors.add(second);
            second.neighbors.add(first);
            
        }
        else if(first.y == (second.y+1)){ //neighbor - up
            first.neighbors.add(second);
            second.neighbors.add(first);
        }
        else if(first.y == (second.y-1)){ //neighbor - down
            first.neighbors.add(second);
            second.neighbors.add(first);
        }
        
    }

    //A - iii - done
    void removeUndirectedEdge(final GridNode first, final GridNode second){ //removes an undirected edge between first and second 
        first.neighbors.remove(second);
        second.neighbors.remove(first);

    }

    //A - iv - done
    HashSet<GridNode> getAllNodes(){  //returns a set of all Nodes in the Graph
        HashSet<GridNode> allNodesInGraph = new HashSet<GridNode>();
        for(int x = 0; x < grid.length; x++){  //go through x values
            for(int y = 0; y < grid.length; y++){ //go through y values
                allNodesInGraph.add(grid[x][y]);
            }
        }

        return allNodesInGraph;
         
    }

    //---------------------------------------------------------------------------------------------------

    //B - done 
    GridGraph createRandomGridGraph(int n){
        GridGraph gridgraph = new GridGraph(n);
        Random rand = new Random();
        ArrayList<Integer> tempRandomValuesAdded = new ArrayList<Integer>();

        for(int x = 0; x < n; x++){
            
            for(int y = 0; y < n; y++){
                int randNodeVal = getRandHelper(n, tempRandomValuesAdded);
                tempRandomValuesAdded.add(randNodeVal);
                gridgraph.addNode(x, y, randNodeVal);  //made new node
            }
        }

        for(int x = 0; x < n; x++){  //connect the nodes
            
            for(int y = 0; y < n; y++){

                if(x+1 < n){ //right node valid
                    if(rand.nextInt(2) == 1){
                        gridgraph.addUndirectedEdge(gridgraph.grid[x][y], gridgraph.grid[x+1][y]);
                    }

                }
                if(y+1 < n){ //node below valid
                    if(rand.nextInt(2) == 1){
                        gridgraph.addUndirectedEdge(gridgraph.grid[x][y], gridgraph.grid[x][y+1]);
                    }

                }
                
            }
        }


        return gridgraph;
    }

    int getRandHelper(int n, ArrayList<Integer> currVals){
        Random rand = new Random();
        int rtnVal = rand.nextInt(n*10);

        while(currVals.contains(rtnVal)){
            rtnVal = rand.nextInt(n*10);

        }

        return rtnVal;
    }

    //--------------------------------------------------------------------------------
    //D - not done 
    ArrayList<GridNode> astar(final GridNode sourceNode, final GridNode destNode){
        ArrayList<GridNode> path = new ArrayList<GridNode>();
        HashMap<GridNode, ArrayList<Integer>> map = new HashMap<>();

        ArrayList<Integer> temp = new ArrayList<>();             
        GridNode curr = sourceNode;                              
        temp.add(0);  //g                                        
        temp.add(calculateManhattan(curr, destNode)); //h
        map.put(curr, temp);

        int counter = 0;

        while(!curr.equals(destNode)){
            counter ++;
            if(counter == 2000){ 
                System.out.println("No Path, Sorry");    //CHANGE THIS VALUE BASED ON INPUT SO IT DOESN'T RUN FOREVER
                break;
            }
            curr.visited = true;
            path.add(curr);

            int visitedCounter = 0;
            for(GridNode neigh : curr.neighbors){
                if(neigh.visited == true){
                    visitedCounter++;
                }
            }
            if(visitedCounter == curr.neighbors.size()){
                curr.needToBackTrack = true;
                path = new ArrayList<GridNode>();
                curr = sourceNode;
            }
            else{
                int nextSmallest = Integer.MAX_VALUE;
                for(GridNode neighbor : curr.neighbors){  //get and update all neighbors
                    if(neighbor.visited == false && neighbor.needToBackTrack == false){
                        ArrayList<Integer> addToHash = new ArrayList<>();
                        addToHash.add(map.get(curr).get(0) + 1); //update g
                        addToHash.add(calculateManhattan(neighbor, destNode));//get h
                        map.put(neighbor, addToHash);

                        if(nextSmallest > (addToHash.get(0) + addToHash.get(1))){  //find next curr
                            curr = neighbor;
                            nextSmallest = addToHash.get(0) + addToHash.get(1);
                        }
                    }
                }
            }
            
        }


        return path;
    }

    int calculateManhattan(GridNode c, GridNode d){
        int h = Math.abs(c.x - d.x) + Math.abs(c.y - d.y);
        return h;
    }

   
}




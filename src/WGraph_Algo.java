package ex1.src;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import ex1.src.WGraph_DS.NodeInfo;

public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph g;

    /**
     * constructor to create a new graph.
     */
    public WGraph_Algo() {
        g = new WGraph_DS();
    }

    /**
     * initialize the graph.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Compute a deep copy of this graph.
     *
     * @return gCopy
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS gCopy = new WGraph_DS(); // create a new WGraph_DS
        node_info graphNode, niNode;
        Iterator<node_info> iteg = g.getV().iterator(); // run on the HashMap gr
        while (iteg.hasNext()) {
            graphNode = iteg.next();
            if (!gCopy.hasNodeByKey(graphNode.getKey())) { // if the graph copy does not contain this node already
                gCopy.addNode(graphNode.getKey()); // add the deep copy node to the copy graph
                gCopy.getNode(graphNode.getKey()).setTag(graphNode.getTag()); // set his tag
                gCopy.getNode(graphNode.getKey()).setInfo(graphNode.getInfo()); // set his info
            }
            Iterator<node_info> iteNi = g.getV(graphNode.getKey()).iterator(); // run on the neighbors HashMap in node_info
            while (iteNi.hasNext()) {
                niNode = iteNi.next();
                if (!gCopy.hasNodeByKey(niNode.getKey())) {
                    gCopy.addNode(niNode.getKey()); // add a deep copy of the node neighbor to the neighbors HashMap of the graph copy node
                    gCopy.getNode(niNode.getKey()).setTag(niNode.getTag());
                    gCopy.getNode(niNode.getKey()).setInfo(niNode.getInfo());
                }
                gCopy.connect(graphNode.getKey(), niNode.getKey(), g.getEdge(graphNode.getKey(), niNode.getKey())); // connect the node with his neighbors with the same weight
            }
        }
        gCopy.setMcAndEdgeCounter(g.getMC(), g.edgeSize()); // copy the number of changes and edges from the original graph to the copy one.
        return gCopy;
    }


    /**
     * setting all the nodes in the graph to tag = 0 and info to "white"
     */
    public void setZeroWhite() {
        node_info in;
        Iterator<node_info> ini = g.getV().iterator(); // run on the HashMap gr
        while (ini.hasNext()) {
            in = ini.next();
            in.setTag(0);
            in.setInfo("white");
        }
    }

    /**
     * setting all the nodes in the graph to tag = "infinity" and info to "white"
     */
    public void setInfinityWhite() {
        node_info in;
        Iterator<node_info> ini = g.getV().iterator(); // run on the HashMap gr
        while (ini.hasNext()) {
            in = ini.next();
            in.setTag(Double.POSITIVE_INFINITY);
            in.setInfo("white");
        }
    }

    /**
     * this algorithm is a method to check if graph is connected
     * first all the nodes are white. the first node turn to black and all his neighbors turn to grey
     * after that each neighbor turn to black and so on.
     * if all the nodes after the BFS are black then the graph are connected, else not.
     * white node - unvisited node
     * grey node - a neighbor of black node
     * black node - visited and in the list
     *
     * @param node
     */
    public void BFS(node_info node) {
        LinkedList<node_info> lst = new LinkedList<>(); // create a new list represents queue of nodes that connected each other.
        Iterator<node_info> iteNi; // run on the HashMap Ni
        node_info niPointer;
        node.setInfo("black"); // set the info of the first node to black,
        lst.add(node); // add the first node to the list
        while (lst.size() != 0) { // while the list is not empty
            node = lst.poll(); // node = the first node in the list, and poll him from the list
            node.setInfo("black"); // set this node to black
            iteNi = g.getV(node.getKey()).iterator(); // run on his neighbors
            while (iteNi.hasNext()) { // run on all his neighbors
                niPointer = iteNi.next();
                if (niPointer.getInfo().equals("white")) { // if the neighbor isn't visit yet set him to grey
                    niPointer.setInfo("grey");
                    niPointer.setTag(node.getTag() + 1); // set his tag bigger in 1 than the previous one
                    lst.add(niPointer); // add him to the list
                }
            }
        }
    }

    /**
     * this algorithm is a method to find the shortest paths between nodes in a graph
     * first all the nodes are white and their tag set to infinity.
     * the first node turn to black with tag 0 and all his neighbors turn to grey
     * after that each neighbor turn to black and so on.
     * white node - unvisited node
     * grey node - a neighbor of black node
     * black node - visited and in the list
     *
     * @param node
     */
    public void Dijkstra(NodeInfo node) {
        PriorityQueue<node_info> pq = new PriorityQueue<>();
        node.setTag(0);
        Iterator<node_info> iteNi; // run on the HashMap Ni
        NodeInfo niPointer;
        node.setInfo("black"); // set the info of the first node to black,
        pq.add(node); // add the first node to the list
        while (pq.size() != 0) { // while the list is not empty
            node = (NodeInfo) pq.poll(); // node = the first node in the list, and poll him from the list
            node.setInfo("black"); // set this node to black
            iteNi = g.getV(node.getKey()).iterator(); // run on his neighbors
            while (iteNi.hasNext()) { // run on all his neighbors
                niPointer = (NodeInfo) iteNi.next();
                if (niPointer.getTag() > node.getTag() + g.getEdge(node.getKey(), niPointer.getKey())) { // if the tag of the node is bigger then the previews node + the weight of the edge - update the tag of the node
                    niPointer.setTag(node.getTag() + g.getEdge(node.getKey(), niPointer.getKey()));
                    niPointer.setFather(node);
                }
                if (niPointer.getInfo().equals("white")) { // if the neighbor isn't visit yet set him to grey
                    niPointer.setInfo("grey");
                    pq.add(niPointer); // add him to the list
                }
            }
        }
    }


    /**
     * Returns true if and only if (iff) there is a valid path from every node to each other node.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if (g.nodeSize() == 1 || g.nodeSize() == 0)
            return true; // if the node size of the graph is 0 or 1 than the graph is connected.
        if (g.edgeSize() < g.nodeSize() - 1)
            return false; // if the number of the edges are smaller than the number of the nodes -1 than the graph cannot be connected.
        setZeroWhite(); // set all the nodes in the graph to tag = 0 and info to "white"
        node_info start; // the starting node of the algo BFS
        Iterator<node_info> iteV = g.getV().iterator(); // run on the nodes of the graph
        if (iteV.hasNext()) {
            start = iteV.next(); // set node start to the first node that the algo BFS will run
            BFS(start); // call BFS
        }
        int NumOfBlacks = 0; // counting the numbers of black nodes
        iteV = g.getV().iterator(); // run on the nodes of the graph
        while (iteV.hasNext()) {
            start = iteV.next();
            if (start.getInfo().equals("black")) NumOfBlacks++; // counting the numbers of black nodes
        }
        return (NumOfBlacks == g.nodeSize()); // if the number of black nodes == number of nodes than the graph is connected

    }

    /**
     * returns the length of the shortest path between src to dest
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the distance between the nodes
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (g.getNode(src) == null || g.getNode(dest) == null) return -1; // if one of the nodes does not exist return
        if (src == dest) return 0;
        setInfinityWhite(); // set all the nodes in the graph to tag = infinity and info to "white"
        NodeInfo s = (NodeInfo) g.getNode(src); // the src node
        s.setTag(0); // set tag for the src node to 0
        Dijkstra(s); // call Dijkstra with src node as start node
        return g.getNode(dest).getTag(); // return the tag of the dest node that signify on the distance of the shortest path
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return List revers
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        double size = shortestPathDist(src, dest);
        if (size == -1) return null;
        node_info e = g.getNode(dest);
        ArrayList<node_info> lst = new ArrayList<>(); // list represent the path
        lst.add(e);
        if (size == 0) return lst; // if the list is empty return
        NodeInfo E = (NodeInfo) e;
        while (E.getFather() != null) { // while we did not get to the root node
            NodeInfo Dad = E.getFather(); // the father of the node
            node_info dad = (node_info) Dad;
            lst.add(dad); // add him to the list
            E = Dad;
        }
        ArrayList<node_info> reversLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            reversLst.add(lst.get(lst.size() - i - 1)); // revers the list
        }
        return reversLst;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter outs = new PrintWriter(fw);
            outs.println("Size: " + g.nodeSize() + ", " + "Edges: " + g.edgeSize());
            Iterator<node_info> iteg = g.getV().iterator();
            while (iteg.hasNext()) {
                node_info gPointer = iteg.next();
                outs.print("[ " + gPointer.getKey() + " , " + gPointer.getTag() + " , " + gPointer.getInfo() + " ] --> ");
                Iterator<node_info> iteNi = g.getV(gPointer.getKey()).iterator();
                while (iteNi.hasNext()) {
                    node_info niPointer = iteNi.next();
                    outs.print("[ " + niPointer.getKey() + " , " + g.getEdge(gPointer.getKey(), niPointer.getKey()) + " ] ");
                }
                outs.println();


            }
            outs.close();
            fw.close();

        } catch (Exception e) {
            System.err.println("File not saved!");
            return false;
        }

        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        WGraph_DS graphLoad = new WGraph_DS();
        try {
            Scanner scan = new Scanner(new File(file));
            int line = 0;
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                ArrayList<String> data = new ArrayList<>();
                if (line > 0) {
                    int i = 9;
                    StringTokenizer tok = new StringTokenizer(s);
                    while (tok.hasMoreTokens()) {
                        data.add(tok.nextToken());
                    }
                    NodeInfo node = new NodeInfo(Integer.parseInt(data.get(1)), Double.parseDouble(data.get(3)), data.get(5));
                    graphLoad.addNode(node);
                    int LineSize = data.size();
                    while (i < LineSize) {
                        graphLoad.connect(node.getKey(), Integer.parseInt(data.get(i)), Double.parseDouble(data.get(i + 2)));
                        i = i + 5;
                    }
                }
                //System.out.println(" Graph Size :" +graphLoad.nodeSize()+" Edge Size :"+graphLoad.edgeSize());
                line++;
            }
            init(graphLoad);
            scan.close();
        } catch (Exception e) {
            System.err.println("File does not load!");
            return false;
        }
        return true;
    }


}
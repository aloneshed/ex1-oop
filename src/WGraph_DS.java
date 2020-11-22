package ex1.src;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class WGraph_DS implements weighted_graph {

    protected static class NodeInfo implements node_info, Comparable<node_info> {
        private int key;
        private HashMap<Integer, OrderedPair<node_info, Double>> ni;
        private String info = "white";
        private double tag;
        private NodeInfo father;

        /**
         * constructor to create a new node and new Hashmap for its Neighbors.
         */
        public NodeInfo() {
            this.key = counter++;
            ni = new HashMap();
        }

        /**
         * constructor to create a new node with given key and new Hashmap for its Neighbors.
         */
        public NodeInfo(int key) {
            this.key = key;
            ni = new HashMap();
            father = null;
        }

        /**
         * copy constructor that deep copy node without its Neighbors.
         */
        public NodeInfo(node_info copy) {
            this.key = copy.getKey();
            this.info = copy.getInfo();
            this.tag = copy.getTag();
            this.ni = new HashMap<>();
        }

        /**
         * constructor to create a new node with given key, tag, info and new Hashmap for its Neighbors.
         */
        public NodeInfo(int key, double tag, String info) {
            this.key = key;
            this.tag = tag;
            this.info = info;
            this.ni = new HashMap<>();
        }

        /**
         * return a collection with all the Neighbor nodes of specific node.
         */
        public Collection<node_info> getNi() {
            Collection<node_info> niCol = new LinkedList<>();
            node_info nd;
            Iterator<OrderedPair<node_info, Double>> ite = ni.values().iterator();
            while (ite.hasNext()) {
                nd = ite.next().getLeft();
                niCol.add(nd);
            }
            return niCol;
        }

        /**
         * return true iff the this node and the node with the key are neighbors.
         *
         * @param key
         */
        public boolean hasNi(int key) {
            return ni.containsKey(key);
        }

        /**
         * adds the node_data t to this node_data.
         *
         * @param t
         */
        public void addNi(node_info t, Double w) {
            if (t != null) {
                OrderedPair op = new OrderedPair(t, w);
                ni.put(t.getKey(), op);
            }
        }

        /**
         * remove the edge this key.
         *
         * @param node
         */
        public void removeNode(node_info node) {
            if (hasNi(node.getKey())) {
                ni.remove(node.getKey());
            }
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            if (s != " ")
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        public NodeInfo getFather() {
            return this.father;
        }

        public void setFather(NodeInfo father) {
            this.father = father;
        }

        @Override
        public int compareTo(node_info other) {
            if (tag < other.getKey()) return -1;
            else if (tag == other.getTag()) return 0;
            else return 1;
        }

        public boolean equalsNode(NodeInfo other) {
            if (this == null || other == null) return false;
            if (this.tag == other.tag && this.info.equals(other.getInfo()) && this.ni.size() == other.ni.size()) {
                return true;
            }
            return false;
        }

        public String toString() {
            return key + " ";
        }
    }

    private HashMap<Integer, node_info> gr;
    private static int counter = 0;
    private int edgeCounter = 0;
    private int mc = 0;
    private double weight = 0.0;

    /**
     * constructor to create a new Hashmap represents graph.
     */
    public WGraph_DS() {
        gr = new HashMap<>();
        counter = 0;
    }
    /**
     * return the node_info by the key,
     *
     * @param key - the node_id
     * @return the node_info by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return gr.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        NodeInfo n1 = (NodeInfo) getNode(node1);
        NodeInfo n2 = (NodeInfo) getNode(node2);
        if (n1 != null && n2 != null && node1 != node2 && n1.hasNi(node2) && n2.hasNi(node1)) // check if the nodes are not equals, if they neighbors and if one of them isn't null
            return true;
        return false;
    }

    /**
     * return the weight of the edge (node1, node1).
     * if there is no edge return -1.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            NodeInfo n1 = (NodeInfo) getNode(node1);
            return n1.ni.get(node2).getRight();
        }
        return -1;
    }

    /**
     * add a new node to the graph with a given key.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!gr.containsKey(key)) {
            NodeInfo node = new NodeInfo(key);
            gr.put(key, node);
            mc++;
        }
    }

    /**
     * add a new node to the graph with the given node_info node.
     * @param node
     */
    public void addNode(NodeInfo node) {
        if (node != null && !gr.containsKey(node.getKey())) {
            gr.put(node.getKey(), node);
        }
    }

    /**
     * Connect an edge between node1 and node2.
     * if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        NodeInfo n1 = (NodeInfo) getNode(node1);
        NodeInfo n2 = (NodeInfo) getNode(node2);
        if (n1 != null && n2 != null && node1 != node2 && w >= 0) { //check if one of them isn't null and if w >= 0.

            if (hasEdge(node1, node2) && w != getEdge(node1, node2)) { // if there is an edge between node1 and node 2 and the weight of the edge is different than the given weight - update the edge weight.
                n1.ni.get(node2).setRight(w);
                n2.ni.get(node1).setRight(w);
                mc++;
            }
            if (!hasEdge(node1, node2)) { // if there is no edge between them - connect.
                n1.addNi(n2, w);
                n2.addNi(n1, w);
                edgeCounter++;
                mc++;
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     *
     * @return Collection<node_info>
     */
    @Override
    public Collection<node_info> getV() {
        return this.gr.values(); // return the pointers of the Hashmap
    }

    /**
     * This method return a collection of the
     * collection representing all the nodes connected to node_id
     *
     * @return Collection<node_info>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        NodeInfo n1 = (NodeInfo) getNode(node_id);
        return n1.getNi(); // return the neighbors of specific node
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * * @return tmp.
     * * @param key
     */
    @Override
    public node_info removeNode(int key) {
        NodeInfo tmp = (NodeInfo) getNode(key); // the node by the given key
        if (tmp == null) return null;
        NodeInfo nd;
        Iterator<node_info> ite = tmp.getNi().iterator(); // run on the neighbors of the given node
        while (ite.hasNext()) {
            nd = (NodeInfo) ite.next();
            nd.removeNode(tmp); // remove the given node from all his neighbors.
            edgeCounter--;
            mc++;
        }
        tmp.getNi().clear(); // remove all the neighbors from the Hashmap neighbors of this node
        gr.remove(key); // remove the node from the graph
        mc++;
        return tmp;
    }

    /**
     * Delete the edge from the graph between 2 given nodes.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        NodeInfo n1 = (NodeInfo) getNode(node1); // node1 by the given key
        NodeInfo n2 = (NodeInfo) getNode(node2); // node2 by the given key
        if (n1 != null && n2 != null && hasEdge(node1, node2) && !n1.equals(n2)) { // if both of them arent null and there is a edge between them and they are not equals.
            n1.removeNode(n2); // remove N2 from N1's neighbors list
            n2.removeNode(n1); // remove N1 from N2's neighbors list
            edgeCounter--;
            mc++;
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return gr.size();
    }

    /**
     * return the number of edges in the graph.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return edgeCounter;
    }

    /**
     * return the number changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return mc;
    }

    /**
     * set number of changes in the graph and number of edges (for the copy constructor)
     *
     * @param mc
     * @param edgeCounter
     */
    public void setMcAndEdgeCounter(int mc, int edgeCounter) {
        this.mc = mc;
        this.edgeCounter = edgeCounter;
    }

    /**
     * return true if the graph contains the specific node by key
     *
     * @param nodeCopy
     * @return true if the graph contains the node
     */
    public boolean hasNodeByKey(int nodeCopy) {
        return gr.containsKey(nodeCopy);
    }

    /**
     * return true if the graph is equals to the other graph.
     *
     * @param other
     * @return true if the graphs are equals
     */
    public boolean equals(Object other) {
        WGraph_DS g = this;
        if (other.getClass() != g.getClass()) return false; // if the Object other isn't a graph
        WGraph_DS gOther = (WGraph_DS) other;
        if (g == null || gOther == null) { // if one of them null return false
            System.out.println("null");
            return false;
        }
        if (g.nodeSize() != gOther.nodeSize()) { // if the node size of the graphs are different
            System.out.println("different node size");
            return false;
        }
        if (g.edgeSize() != gOther.edgeSize()) {  // if the edge size of the graphs are different
            System.out.println("different edge size");
            return false;
        }
        Iterator<node_info> iteg = g.getV().iterator(); // run on the nodes of this graph
        while (iteg.hasNext()) {
            NodeInfo gPointer = (NodeInfo) iteg.next();
            NodeInfo otherPointer = (NodeInfo) gOther.getNode(gPointer.getKey());
            if (otherPointer == null || !gPointer.equalsNode(otherPointer)) { // if the node of this graph is not found on the other graph or they are not equals return false
                System.out.println("different nodes at key: " + gPointer.getKey());
                return false;
            }
            Iterator<node_info> iteNi = getV(gPointer.getKey()).iterator(); // run on the neighbors of this node and search them on the other graph
            while (iteNi.hasNext()) {
                NodeInfo niPointer = (NodeInfo) iteNi.next();
                if (getEdge(niPointer.getKey(), gPointer.getKey()) != gOther.getEdge(niPointer.getKey(), gPointer.getKey())) { // if the weight of the edge on this graph is different from the weight no the other graph, return false
                    return false;
                }
            }
        }
        return true;
    }
}



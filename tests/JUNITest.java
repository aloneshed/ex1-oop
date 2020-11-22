package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class JUNITest {

    WGraph_DS g = new WGraph_DS();


    @BeforeEach
    void BuildAndClear() {
        Iterator<node_info> ite = g.getV().iterator();
        while (ite.hasNext()) {
            g.removeNode(ite.next().getKey());
        }
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);

        g.connect(0,1,2.5);
        g.connect(0,2,4.4);
        g.connect(1,2,0.25);
        g.connect(1,3,3.15);
        g.connect(2,3,1);

    }

    @Test
    void equals() {
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);

        g.connect(0,1,2.5);
        g.connect(0,2,4.4);
        g.connect(1,2,0.25);
        g.connect(1,3,3.15);
        g.connect(2,3,1);

        WGraph_Algo A = new WGraph_Algo();
        A.init(g);
        WGraph_DS gOther = (WGraph_DS) A.copy();


        boolean flag = g.equals(gOther);

        assertTrue(flag);

        g.removeNode(1);

        flag = g.equals(gOther);

        assertFalse(flag);

        gOther.removeNode(1);

        flag = g.equals(gOther);

        assertTrue(flag);


    }

    @Test
    void getNode() {
        node_info n1 = g.getNode(0);
        node_info n2 = g.getNode(1);
        node_info n3 = g.getNode(2);
        node_info n4 = g.getNode(3);
        node_info n5 = g.getNode(0);


        assertSame(n1, n5);
        assertNotSame(n1, n2);

    }

    @Test
    void hasEdge() {
//        Iterator<node_info> ite = g.getV().iterator();
//        while (ite.hasNext()) {
//            g.removeNode(ite.next().getKey());
//        }
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);

        g.connect(0,1,2.5);
        g.connect(0,2,4.4);
        g.connect(1,2,0.25);
        g.connect(1,3,3.15);
        g.connect(2,3,1);

        WGraph_Algo A = new WGraph_Algo();
        A.init(g);

        assertTrue(g.hasEdge(0,1));
        assertTrue(g.hasEdge(0,2));
        assertTrue(g.hasEdge(2,1));
        assertTrue(g.hasEdge(3,1));
        boolean flag = A.isConnected();
        assertTrue(flag);
        g.addNode(4);
        flag = A.isConnected();
        assertFalse(flag);
        assertFalse(g.hasEdge(3,4));
        g.connect(3,4,2.2);
        flag = A.isConnected();
        assertTrue(flag);



    }

    @Test
    void getEdge() {
        g.connect(0,1,2.5);
        g.connect(0,2,4.4);
        g.connect(1,2,0.25);
        g.connect(1,3,3.15);
        g.connect(2,3,1);

        double edge02 = g.getEdge(0,2);
        double edge12 = g.getEdge(1,2);

        boolean flag02 = (edge02 == 4.4);
        boolean flag12 = (edge12 == 0.25);

        assertTrue(flag02);
        assertTrue(flag12);

    }

    @Test
    void addNode() {
        g.addNode(null);
        g.addNode(3);
        g.addNode(0);
        assertTrue(g.nodeSize() == 4);
    }

    @Test
    void connect() {
        node_info n0 = g.getNode(0);
        node_info n1 = g.getNode(1);
        System.out.println(n0.getKey());
        System.out.println(n1.getKey());

        g.connect(0, 1, 2);
        assertTrue(g.hasEdge(n0.getKey(), n1.getKey()));
        assertTrue(g.hasEdge(n1.getKey(), n0.getKey()));
        assertFalse(g.hasEdge(n1.getKey(), n1.getKey()));
        g.connect(0, 2, 3);
        g.connect(1, 3, 4.4);
        assertFalse(g.hasEdge(0, 3));
        assertTrue(g.hasEdge(0, 1));
        assertTrue(g.hasEdge(0, 2));
        assertTrue(g.hasEdge(0, 1));
        assertTrue(g.hasEdge(1, 0));


    }

    @Test
    void getV() {
        Iterator<node_info> ite = g.getV().iterator();
        while (ite.hasNext()){
            node_info node = ite.next();
        }

    }


    @Test
    void removeNode() {
        g.connect(0, 1, 2);
        g.connect(0, 2, 3);
        g.connect(1, 3, 4.4);

        g.removeNode(1);
        assertFalse(g.hasEdge(0, 1));
        assertFalse(g.hasEdge(1, 3));

        g.removeNode(100);
        assertFalse(g.hasEdge(100, 1));
    }

    @Test
    void removeEdge() {
        g.connect(0, 1, 2);
        g.connect(0, 2, 3);
        g.connect(1, 3, 4.4);

        g.removeEdge(1, 3);
        assertFalse(g.hasEdge(1, 3));
        assertFalse(g.hasEdge(3, 1));
        assertFalse(g.hasEdge(2, 2));

    }

    @Test
    void nodeSize() {
        assertEquals(g.nodeSize(), 4);

        g.connect(0, 1, 2);
        g.connect(0, 2, 3);
        g.connect(1, 3, 4.4);

        g.removeNode(0);
        assertEquals(g.nodeSize(), 3);

        g.removeNode(0);
        assertEquals(g.nodeSize(), 3);

        g.removeNode(15);
        assertEquals(g.nodeSize(), 3);
    }

    @Test
    void edgeSize() {
        WGraph_DS g1 = new WGraph_DS();

        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);
        g1.addNode(5);
        g1.addNode(6);
        g1.addNode(7);
        g1.addNode(8);


        g1.connect(0,1,4);
        g1.connect(1,2,8);
        g1.connect(2,3,7);
        g1.connect(3,4,9);
        g1.connect(4,5,10);
        g1.connect(5,6,2);
        g1.connect(6,7,1);
        g1.connect(7,8,7);
        g1.connect(7,0,8);
        g1.connect(1,7,11);
        g1.connect(2,5,4);
        g1.connect(3,5,14);
        g1.connect(2,8,2);
        g1.connect(8,6,6);

//        g.connect(0, 1, 2);
//        g.connect(0, 2, 3);
//        g.connect(1, 3, 4.4);
//        assertEquals(g.edgeSize(), 3);
//
//        g.removeEdge(1, 3);
//        assertEquals(g.edgeSize(), 2);
//        g.removeEdge(1, 8);
//        assertEquals(g.edgeSize(), 2);
    }

    @Test
    void getMC() {
        g.removeNode(0);
        g.removeNode(1);
        g.removeNode(2);
        g.removeNode(3);

        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);


        g.connect(0,1,4);
        g.connect(1,2,8);
        g.connect(2,3,7);
        g.connect(3,4,9);
        g.connect(4,5,10);
        g.connect(5,6,2);
        g.connect(6,7,1);
        g.connect(7,8,7);
        g.connect(7,0,8);
        g.connect(1,7,11);
        g.connect(2,5,4);
        g.connect(3,5,14);
        g.connect(2,8,2);
        g.connect(8,6,6);
        assertEquals(g.getMC(), 41);
        g.connect(3,5,100);
        assertEquals(g.getMC(), 42);

//        assertEquals(g.getMC(), 0);
//        g.connect(0, 1, 2);
//        g.connect(0, 2, 3);
//        g.connect(1, 3, 4.4);
//        assertEquals(g.edgeSize(), 3);
//
//        g.removeEdge(1, 3);
//        assertEquals(g.getMC(), 4);
//        g.removeEdge(1, 8);
//        assertEquals(g.getMC(), 4);
    }

    @Test
    void copy() {
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        WGraph_DS copy = (WGraph_DS)algo.copy();
        boolean flag = g.equals(copy);
        assertTrue(flag);

        g.addNode(4);
        g.addNode(5);
        g.connect(4,5,10.12);

        assertFalse(algo.isConnected());
        g.connect(4,3,2);
        assertTrue(algo.isConnected());
    }

    @Test
    void copy2() {
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        WGraph_DS copy = (WGraph_DS)algo.copy();
        boolean flag = g.equals(copy);
        assertTrue(flag);

        g.addNode(4);
        g.addNode(5);
        g.connect(4,5,10.12);

        assertFalse(algo.isConnected());
        g.connect(4,3,2);
        assertTrue(algo.isConnected());
    }

    @Test
    void isConnected() {
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        assertTrue(algo.isConnected());
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        assertFalse(algo.isConnected());
        g.connect(4,5,11.3);
        g.connect(4,6,24.12);
        g.connect(6,7,5.02);
        g.connect(7,3,7.64);
        assertTrue(algo.isConnected());

    }

    @Test
    void shortestPathDist() {
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        g.removeNode(0);
        g.removeNode(1);
        g.removeNode(2);
        g.removeNode(3);

        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);

        g.connect(0,1,4);
        g.connect(1,2,8);
        g.connect(2,3,7);
        g.connect(3,4,9);
        g.connect(4,5,10);
        g.connect(5,6,2);
        g.connect(6,7,1);
        g.connect(7,8,7);
        g.connect(7,0,8);
        g.connect(1,7,11);
        g.connect(2,5,4);
        g.connect(3,5,14);
        g.connect(2,8,2);
        g.connect(8,6,6);

        assertEquals(algo.shortestPathDist(0,1),4);
        assertEquals(algo.shortestPathDist(0,2),12);
        assertEquals(algo.shortestPathDist(0,3),19);
        assertEquals(algo.shortestPathDist(0,4),21);
        assertEquals(algo.shortestPathDist(0,5),11);
        assertEquals(algo.shortestPathDist(0,6),9);
        assertEquals(algo.shortestPathDist(0,7),8);
        assertEquals(algo.shortestPathDist(0,8),14);

    }

    @Test
    void shortestPath() {
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        g.removeNode(0);
        g.removeNode(1);
        g.removeNode(2);
        g.removeNode(3);

        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);

        g.connect(0,1,4);
        g.connect(1,2,8);
        g.connect(2,3,7);
        g.connect(3,4,9);
        g.connect(4,5,10);
        g.connect(5,6,2);
        g.connect(6,7,1);
        g.connect(7,8,7);
        g.connect(7,0,8);
        g.connect(1,7,11);
        g.connect(2,5,4);
        g.connect(3,5,14);
        g.connect(2,8,2);
        g.connect(8,6,6);

        LinkedList<node_info> lst = new LinkedList<>();
        lst.add(g.getNode(0));
        lst.add(g.getNode(1));
        assertEquals(lst,algo.shortestPath(0,1));
        lst.clear();
        lst.add(g.getNode(0));
        lst.add(g.getNode(1));
        lst.add(g.getNode(2));
        assertEquals(lst,algo.shortestPath(0,2));
        lst.clear();
        lst.add(g.getNode(0));
        lst.add(g.getNode(7));
        lst.add(g.getNode(6));
        lst.add(g.getNode(5));
        lst.add(g.getNode(4));
        assertEquals(lst,algo.shortestPath(0,4));
        assertEquals(algo.shortestPathDist(0,4),21);
    }

    @Test
    void save() {
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        g.addNode(4);
        g.addNode(5);
        assertTrue(algo.save("test1.txt"));
    }

    @Test
    void load() {
        g.removeNode(0);
        g.removeNode(1);
        g.removeNode(2);
        g.removeNode(3);

        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        assertTrue(algo.load("test1.txt"));

        WGraph_DS copy = (WGraph_DS)algo.copy();

        WGraph_DS check = new WGraph_DS();
        check.addNode(0);
        check.addNode(1);
        check.addNode(2);
        check.addNode(3);

        check.connect(0,1,2.5);
        check.connect(0,2,4.4);
        check.connect(1,2,0.25);
        check.connect(1,3,3.15);
        check.connect(2,3,1);

        assertNotEquals(check,copy);
        check.addNode(4);
        check.addNode(5);
        assertEquals(check,copy);

    }


}
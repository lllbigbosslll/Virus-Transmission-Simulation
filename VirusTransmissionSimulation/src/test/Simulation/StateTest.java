package test.Simulation;

import Simulation.Node;
import Simulation.State;

import org.jfree.data.xy.XYSeriesCollection;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
* State Tester. 
* 
* @author <Authors name> 
* @since <pre>12月 9, 2020</pre> 
* @version 1.0 
*/ 
public class StateTest {


    @Test
    public void test07() throws Exception {
        State state = new State(1, false, false, false, 1, null);
        assertNotNull(state);
    }

    @Test
    public void test08() throws Exception {
        State state = new State(1, false, false, false, 1, null);
        assertEquals(state.peopleDensity, 1, 0);
        assertEquals(state.hasMaskingPolicy, false);
        assertEquals(state.hasTestingPolicy, false);
        assertEquals(state.hasQuarantinePolicy, false);
    }

    @Test
    public void test09() throws Exception {
        Node[][] nodes = new Node[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                nodes[i][j] = new Node(i, j, false);
            }
        }
        State state = new State(1, false, false, false, 1, nodes);

        assertEquals(state.getNodeMap()[0][0].getX(), 0);
        assertEquals(state.getNodeMap()[0][0].getY(), 0);
        assertEquals(state.getNodeMap()[9][9].getX(), 9);
        assertEquals(state.getNodeMap()[9][9].getY(), 9);
    }

    @Test
    public void test10() throws Exception{
        Node[][] n = new Node[100][100];
        State state = new State(4, false, false, false, 1, n);
        Node[][] nodes = State.generateNodemap(100, 4);
        assertEquals(nodes.length * nodes[0].length, 10000);
        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                if (nodes[i][j].isFlag()){
                    assertNotNull(nodes[i][j].getPerson());
                }
            }
        }
    }

    @Test
    public void test11() throws Exception{
        Node[][] n = State.generateNodemap(1000, 4);
        State state = new State(4, false, false, false, 10, n);
        assertTrue(state.getPositiveCount() == 20);
    }

    @Test
    public void test12() throws Exception{
        Node[][] n = State.generateNodemap(1000, 4);
        State state = new State(4, false, false, false, 10, n);
        state.simulate();
        for(int i = 0; i < 500; i++){
            assertTrue(state.getxList().get(i) == i);
            assertTrue(state.getyList().get(i) >= 0);
        }
        assertTrue(state.getxList().size() == 500);
        assertTrue(state.getyList().size() == 500);
    }

    @Test
    public void test13() throws Exception{
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        List<Integer> list4 = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            list1.add(1);
            list2.add(2);
            list3.add(3);
            list4.add(4);
        }

        XYSeriesCollection collection = State.GetCollection(list1, list2, list3, list4);
        assertTrue(collection.getSeries().size() == 2);
        assertTrue(collection.getSeries(0).getX(0).intValue() == 1);
        assertTrue(collection.getSeries(0).getY(0).intValue() == 2);
        assertTrue(collection.getSeries(1).getX(0).intValue() == 3);
        assertTrue(collection.getSeries(1).getY(0).intValue() == 4);
    }



}

package test.Simulation; 

import Simulation.Node;

import org.junit.Test;
import static org.junit.Assert.*;

/** 
* Node Tester. 
* 
* @author <Authors name> 
* @since <pre>12月 9, 2020</pre> 
* @version 1.0 
*/ 
public class NodeTest { 



@Test
public void test04() throws Exception {
    Node node = new Node(1,1,false);
    assertNotNull(node);
} 


@Test
public void test05() throws Exception {
    Node node = new Node(1,1,false);
    assertEquals(node.getX(), 1);
    assertEquals(node.getY(), 1);
} 


@Test
public void test06() throws Exception {
    Node node = new Node(2,2,false);
    assertEquals(node.getX(), 2);
    assertEquals(node.getY(), 2);
    assertFalse(node.isFlag());
}

} 

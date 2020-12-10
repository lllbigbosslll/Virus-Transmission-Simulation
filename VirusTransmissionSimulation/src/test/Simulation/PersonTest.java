package test.Simulation; 

import Simulation.Person;

import org.junit.Test;


import static org.junit.Assert.*;

/** 
* Person Tester. 
* 
* @author <Authors name> 
* @since <pre>12月 9, 2020</pre> 
* @version 1.0 
*/ 
public class PersonTest { 



@Test
public void test01() throws Exception{
    Person person = new Person(1, 1);
    assertNotNull(person);
}

@Test
public void test02() throws Exception{
    Person person = new Person(1, 1);
    assertEquals(person.getX(), 1);
    assertEquals(person.getY(), 1);
}

@Test
public void test03() throws Exception{
    Person person = new Person(2, 2);
    assertEquals(person.getX(), 2);
    assertEquals(person.getY(), 2);
    assertEquals(person.getPositiveRemainDays(), 0);
    assertFalse(person.isHealed());
    assertFalse(person.isPositive());
}



}

/**
 * TestRestockList.java
 *
 * 	Version:
 *	$Id:$
 *
 *	Revisions:
 *	$Log:$
 */

/**
 *
 *	@author	jimiford
 */
package model;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.DAOTest;

/**
 * @author jimiford
 *
 */
public class RestockingListTest {

	public static final String TEST_FOLDER = DAOTest.TEST_FOLDER;
	private LineItem one;
	private LineItem two;
	private LineItem three;
	private LineItem neg1;
	private LineItem neg2;
	private RestockingList list;

	/**
	 * 
	 * 
	 * void
	 */
	@Before
	public void setUp() {
		try {
			one = new LineItem("Snickers", 123L, 10, 5);
			two = new LineItem("Pop Tarts", 124L, 11, 13);
			three = new LineItem("Tofu", 125L, 12, 4);
			neg1 = new LineItem("Tofu", 125L, 12, -4);
			neg2 = new LineItem("Pop Tarts", 124L, 11, -13);
			list = new RestockingList();
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	/**
	 * 
	 * 
	 * void
	 */
	@After
	public void tearDown() {
	}

	@Test
	public void testZeroInitialSize() {
		try {
			assertEquals("Initially, the restocking list should have a size " +
					"of zero.", 0, list.getLineItems().size());
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testPrintOut() {
		try {
			one.setPrice(125);
			two.setPrice(225);
			three.setPrice(100);
			list.addLine(one);
			list.addLine(two);
			list.addLine(three);
			list.printList();
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testToString() {
		try {
			one.setPrice(125);
			two.setPrice(225);
			three.setPrice(100);
			list.addLine(one);
			list.addLine(two);
			list.addLine(three);
			System.out.println(list);
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testAddLine() {
		try {
			one.setPrice(125);
			two.setPrice(225);
			three.setPrice(100);
			list.addLine(one);
			assertEquals("The first index should equal 'one'.", one, 
					list.getLineItems().get(0));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testRemoveLineTwo() {
		try {
			one.setPrice(125);
			two.setPrice(225);
			three.setPrice(100);
			list.addLine(one);
			list.addLine(two);
			list.addLine(three);
			list.removeLineItem(one);
			assertEquals("The second index should equal 'three'.", three,
					list.getLineItems().get(1));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testSort() {
		try {
			System.out.println("_________________SORTING_________________");
			list.addLine(one);
			list.addLine(neg1);
			list.addLine(two);
			list.addLine(neg2);
			list.addLine(three);
			assertEquals("Neg1 should have a quantity of -4.", -4, 
					neg1.getQty());
//			System.out.println(list.getSortedLines());
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

}

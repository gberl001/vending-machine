/**
 * TestProduct.java
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

import java.util.Collections;
import java.util.LinkedList;


import model.Product.TYPE;

import org.junit.Test;

import dao.DAOTest;

/**
 * @author jimiford
 *
 */
public class ProductTest {

	public static final String TEST_FOLDER = DAOTest.TEST_FOLDER;


	@Test
	public void testCopyConstructor() {
		try {
			Product one = new Product("Snickers", TYPE.CANDY_BAR, 12345L);
			Product two = new Product(one);
			assertEquals("The two products should be identical.", one, two);
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testNotEqualProducts() {
		try {
			Product one = new Product("Snickers", TYPE.CANDY_BAR, 12345L);
			Product two = new Product("Snickers", TYPE.CANDY_BAR, 1234L);
			assertFalse("The two products should be different.", 
					one.equals(two));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testEqualProducts() {
		try {
			Product one = new Product("Snickers", TYPE.CANDY_BAR, 12345L);
			Product two = new Product("Snickers", TYPE.CANDY_BAR, 12345L);
			assertTrue("The two products should be identical.", 
					one.equals(two));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testEqualProducts2() {
		try {
			Product one = new Product("Snickers", TYPE.CANDY_BAR, 12345L);
			assertTrue("The two products should be identical.", 
					one.equals(one));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testDeepCopy() {
		try {
			Product one = new Product("Snickers", TYPE.CANDY_BAR, 123L);
			Product two = new Product(one);
			one.setType(TYPE.CANDY);
			assertTrue("one should by CANDY", 
					one.getType().equals(TYPE.CANDY));
			assertTrue("two should by CANDY_BAR", 
					two.getType().equals(TYPE.CANDY_BAR));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

	@Test
	public void testSort() {
		try {
			LinkedList<Product> list = new LinkedList<Product>();
			LinkedList<Product> list2 = new LinkedList<Product>();
			Product one = new Product("Alphabet Soup", TYPE.MISC, 12346L);
			Product two = new Product("Skittles", TYPE.CANDY, 12314L);
			Product three = new Product("Snickers", TYPE.CANDY_BAR, 52327L);
			list.add(one);
			list.add(two);
			list.add(three);
			list2.add(three);
			list2.add(one);
			list2.add(two);
			Collections.sort(list2);
			assertEquals("The two lists should be equally sorted.", 
					list.get(0), list2.get(0));
			assertEquals("The two lists should be equally sorted.",
					list.get(1), list2.get(1));
			assertEquals("The two lists should be equally sorted.",
					list.get(2), list2.get(2));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

}

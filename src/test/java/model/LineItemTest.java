package model;

import static org.junit.Assert.*;


import model.Product.TYPE;

import org.junit.Test;
/**
 * A class for testing LineItem
 * @author dxs3203
 *
 */
public class LineItemTest {

	@Test
	public void testConstructor() {
		Product p = new Product("Doritos", TYPE.CHIPS, 123L);
		LineItem li = new LineItem(p.getName(), p.getUPC(), 10, 5);
		try{
			assertEquals("Doritos", li.getProductName());
			assertEquals(10, li.getLocation());
			assertEquals(5, li.getQty());
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}

}

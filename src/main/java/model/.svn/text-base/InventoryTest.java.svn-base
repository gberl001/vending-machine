/**
 * TestInventory.java
 */
package model;

import static org.junit.Assert.*;


import model.Product.TYPE;

import org.junit.Test;

import exceptions.SlotOverflowException;

/**
 * A test class for Inventory
 * 
 * @author dxs3203
 *
 */
public class InventoryTest {
	private Inventory in;
	private Inventory in2;
	private Product p1;
	private Product p2;
	
	@Test
	public void testDefaultConstructor() {
		in = new Inventory();
		try{
			assertEquals("The number of rows should equal 8: ", 
					     8, in.getNumRows());
			assertEquals("The number of cols should equal 5: ",
					     5, in.getNumCols());
			assertEquals("The capacity of each slots should be 15: ",
					     15, in.getSlotAt(10).getCapacity());
		} catch( Exception e){
			fail("Exception was thrown: " + e.getMessage());
		}	
	}

	@Test
	public void testTwoArgsConstructor(){
		in = new Inventory(5,6);
		try {
			assertEquals("The number of rows should equal 5: ",
					     5, in.getNumRows());
			assertEquals("The number of cols should equal 6: ",
					     6, in.getNumCols());
			assertEquals("The capacity of each slots should be 15:  ", 
					     15, in.getSlotAt(10).getCapacity());
		} catch( Exception e){
			fail("Exception was thrown: " + e.getMessage());
		}
	}
	
	@Test
	public void testThreeArgsConstructor(){
		in = new Inventory(5,6,10);
		try {
			assertEquals("The number of rows should equal 5: ",
					     5, in.getNumRows());
			assertEquals("The number of cols should equal 6: ",
					     6, in.getNumCols());
			assertEquals("The capacity of each slots should be 10: ", 
					     10, in.getSlotAt(10).getCapacity());
		} catch( Exception e){
			fail("Exception was thrown: " + e.getMessage());
		}
	}
	
	@Test
	public void testCopyConstructor(){
		p1 = new Product("Doritos", TYPE.CHIPS, 123L);
	    p2 = new Product("Kit-Kat", TYPE.CANDY_BAR, 234L);
		in = new Inventory(5,6,10);
		
		try {
			in.getSlotAt(10).addProduct(p1);
			in.getSlotAt(10).addProduct(p1);
			in.getSlotAt(20).addProduct(p2);
		} catch (SlotOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		in2 = new Inventory(in);
		try {
			assertTrue("The slots map should be the same: ",
					in.viewSlots().equals(in2.viewSlots()));
			assertTrue("The capacity shoud be the same: ", 
					in.getCapacityAt(10) == in2.getCapacityAt(10));
		} catch( Exception e){
			fail("Exception was thrown: " + e.getMessage());
		}*/
	}
	
	@Test
	public void testAddProductAt1(){
		/*
		in = new Inventory();
		p1 = new Product("Doritos", TYPE.CHIPS, 123L);
		in.addProductAt(p1, 10);
		try{
			assertTrue("Product returned from location \"10\" echelon \"0\"" +
					   " should be equal to the product that was added to " +
					   "that location" ,in.viewProductAt(10, 0).equals(p1));
		} catch (Exception e){
			fail("Exception was thrown: " + e.getMessage());
		}*/
	}
	@Test
	public void testAddProductAt2(){
		/*
		//3x3 vending machine with depth of 5
		in = new Inventory(3,3,5);
		p1 = new Product("Doritos", TYPE.CHIPS, 123L);
		p2 = new Product("Kit-Kat", TYPE.CANDY_BAR, 234L);
		in.addProductAt(p1, 10);
		in.addProductAt(p1, 10);
		in.addProductAt(p1, 10);
		in.addProductAt(p1, 10);
		in.addProductAt(p1, 10);
		try{
			//slots filled with Doritos.
			assertTrue("Product returned from location \"10\" echelon \"4\"" +
					   " should be equal to the product that was added to " +
					   "that location" ,in.viewProductAt(10, 4).equals(p1)); 
			//Try to add product to full slot
			in.addProductAt(p2, 10);
			//test that the product in position 4 is unchanged
			assertTrue("Product returned from location \"10\" echelon \"4\""+
					  " is unchanged after attempting to add product to full "+
					  "slot" ,in.viewProductAt(10, 4).equals(p1)); 
		} catch (Exception e){
			fail("Exception was thrown: " + e.getMessage());
		}*/
	}
	@Test
	public void testRemoveProductAt(){
		/*
		//3x3 vending machine with depth of 5
		in = new Inventory(3,3,5);
		p1 = new Product("Doritos", TYPE.CHIPS, 123L);
		p2 = new Product("Kit-Kat", TYPE.CANDY_BAR, 234L);
		in.addProductAt(p1, 10);
		in.addProductAt(p2, 10);
		try{
			assertTrue("Product returned from location \"10\" echelon \"0\"" +
					   " should be equal to the product that was added to " +
					   "that location" ,in.viewProductAt(10, 0).equals(p1)); 
			assertTrue("Product returned from location \"10\" echelon \"1\"" +
					   " should be equal to the product that was added to " +
					   "that location" ,in.viewProductAt(10, 1).equals(p2)); 
			
			//remove product in first location. products behind 
			//should all move up one space.
			in.removeProductAt(10, 0);
			assertTrue("Product in slot \"10\" echelon \"0\" should equal " +
					   "product previously in echelon \"1\": " ,
					   in.viewProductAt(10, 0).equals(p2)); 
		} catch (Exception e){
			fail("Exception was thrown: " + e.getMessage());
		}*/
	}
	
	@Test
	public void testViewProductAt(){
		/*
		in = new Inventory();
		p1 = new Product("Doritos", TYPE.CHIPS, 123L);
		p2 = new Product("Kit-Kat", TYPE.CANDY_BAR, 234L);
		in.addProductAt(p1, 10);
		in.addProductAt(p2, 10);
		try{
			assertTrue("Product returned from location \"10\" echelon \"0\"" +
					   " should be equal to the product that was added to " +
					   "that location" ,in.viewProductAt(10, 0).equals(p1));
			assertTrue("Product returned from location \"10\" echelon \"1\"" +
					   " should be equal to the product that was added to " +
					   "that location" ,in.viewProductAt(10, 1).equals(p2));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}*/
	}
	
	@Test
	public void testSetAndGetCostAt() {
		in = new Inventory();
		in.getSlotAt(10).setCost(100);
		try {
			assertEquals("The cost at location \"10\" should be $1.00",100, in.getSlotAt(10).getCost());
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetNumProductsAt() {
		/*
		in = new Inventory();
		in.addProductAt(p1, 10);
		in.addProductAt(p2, 10);
		in.addProductAt(p2, 10);
		in.addProductAt(p2, 10);
		in.addProductAt(p2, 10);
		in.addProductAt(p2, 10);
		try {
			assertEquals("The number of products at location \"10\" should" +
					     " be 6: ",6, in.getNumProductsAt(10));
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}*/
	}
	
	@Test
	public void testGetCapacityAt() {
		in = new Inventory(5,6,7);
		try {
			assertEquals("The capacity at location \"10\"(along with all " +
					     "other locations should be 7: ",7, in.getSlotAt(10).getCapacity());
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetRowAndCols() {
		in = new Inventory(5,6,10);
		try {
			assertEquals("The number of rows is 5: ", 5, in.getNumRows());
			assertEquals("The number of cols is 6: ", 6, in.getNumCols());
		} catch (Exception e) {
			fail("Exception was thrown: " + e.getMessage());
		}
	}
}

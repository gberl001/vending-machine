/**
 * TestVendingMachine.java
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


import model.Product.TYPE;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import control.Controller;
import exceptions.EmptySlotException;
import exceptions.OverwriteException;
import exceptions.SlotOverflowException;

/**
 * @author jimiford
 *
 */
public class VendingMachineTest {

	private Controller control = new Controller();
	private VendingID id;
	private VendingMachine test;
	private Product prod1;
	private Product prod2;
	private Product prod3;
	private Product prod4;
	private LineItem line1;
	private LineItem line2;
	private LineItem line3;
	private LineItem line4;
	
	@Before
	public void setup() {
		//id = new VendingID("test1");
		control = new Controller();
		test = new VendingMachine(id, control);
		test.setSaveMe(false);
		prod1 = new Product("Snickers", TYPE.CANDY_BAR, 1L);
		prod2 = new Product("Skittles", TYPE.CANDY, 2L);
		prod3 = new Product("Lolly Plop", TYPE.CANDY, 3L);
		prod4 = new Product("Jimmies", TYPE.MISC, 4L);
		line1 = new LineItem(prod1.getName(), prod1.getUPC(), 10, 4);
		line2 = new LineItem(prod2.getName(), prod2.getUPC(), 11, 5);
		line3 = new LineItem(prod3.getName(), prod3.getUPC(), 12, 3);
		line4 = new LineItem(prod4.getName(), prod4.getUPC(), 13, 7);
		try {
			control.addProduct(prod1);
		} catch (OverwriteException e) {	}
		try {
			control.addProduct(prod2);
		} catch (OverwriteException e) {	}
		try {
			control.addProduct(prod3);
		} catch (OverwriteException e) {	}
		try {
			control.addProduct(prod4);
		} catch (OverwriteException e) {	}
	}
	
	@After
	public void tearDown() {
		control.deleteProduct(prod1.getUPC());
		control.deleteProduct(prod2.getUPC());
		control.deleteProduct(prod3.getUPC());
		control.deleteProduct(prod4.getUPC());
	}
	
	@Test
	public void testAddAndFinishRestockingList() {
		//assertEquals("Restock list should be null.", null, 
		//      test.getRestockList());
		assertEquals("Products should not exist yet.", 0, 
				test.getNumProductsAt(10));
		assertEquals("Products should not exist yet.", 0, 
				test.getNumProductsAt(11));
		assertEquals("Products should not exist yet.", 0, 
				test.getNumProductsAt(12));
		RestockingList list = new RestockingList();
		list.addLine(line1);
		list.addLine(line2);
		list.addLine(line3);
		test.setRestockingList(list);
		assertEquals("Restock list should be set now.", list, 
				test.getRestockList());
		try {
			test.finishRestockList();
		} catch (SlotOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		//assertEquals("Restock list should be null.", null, 
		//      test.getRestockList());
		assertEquals("4 Snickers at slot 10.", 4, 
				test.getNumProductsAt(10));
		assertEquals("5 Skittles at slot 11.", 5, 
				test.getNumProductsAt(11));
		assertEquals("3 Lolly Poops at slot 12.", 3, 
				test.getNumProductsAt(12));
		
		try {
			assertEquals("4 Snickers at slot 10.", prod1, 
					test.getInventory().getSlotAt(10).getProductAt(0));
		} catch (EmptySlotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals("5 Skittles at slot 11.", prod2, 
					test.getInventory().getSlotAt(11).getProductAt(0));
		} catch (EmptySlotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals("3 Lolly Poops at slot 12.", prod3, 
					test.getInventory().getSlotAt(12).getProductAt(0));
		} catch (EmptySlotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemovalOfProducts() {
		/*
		test.addProductAt(prod1, line1.getLocation(), line1.getQty());
		test.addProductAt(prod2, line2.getLocation(), line2.getQty());
		test.addProductAt(prod3, line3.getLocation(), line3.getQty());
		test.addProductAt(prod4, line4.getLocation(), line4.getQty());*/
		//assertEquals("Restock list should be null.", null, 
		//      test.getRestockList());
		assertEquals("4 Snickers at slot 10.", line1.getQty(), 
				test.getNumProductsAt(10));
		assertEquals("5 Skittles at slot 11.", line2.getQty(),
				test.getNumProductsAt(11));
		assertEquals("3 Lolly Poops at slot 12.", line3.getQty(),
				test.getNumProductsAt(12));
		assertEquals("7 Jimmies at slot 13.", line4.getQty(), 
				test.getNumProductsAt(13));
		
	}

}

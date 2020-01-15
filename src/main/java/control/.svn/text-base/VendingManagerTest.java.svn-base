/**
 * TestVendingManager.java
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
package control;

import static org.junit.Assert.*;


import model.VendingID;
import model.VendingID.State;
import model.VendingMachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.OverwriteException;

/**
 * @author jimiford
 *
 */
public class VendingManagerTest {

	private VendingID id;
	private VendingMachine machine;
	private Controller man;
	
	/**
	 * @throws java.lang.Exception
	 * void
	 */
	@Before
	public void setUp() {
		try {
			id = new VendingID("removeMe", State.NY, "Rochester", 14617, 
					"372 Pinegrove Ave", "Bedroom");
			man = new Controller();
			machine = new VendingMachine(id, man);
		} catch (Exception e) {
			fail("Exception was caught: " + e.getMessage());
		}
	}

	/**
	 * @throws java.lang.Exception
	 * void
	 */
	@After
	public void tearDown() {
		try {
		man.deleteVendingMachine(machine.getID().getId());
		} catch (Exception e) {
			fail("Exception was caught: " + e.getMessage());
		}
	}

	@Test
	public void testAddMachine() {
		try {
			man.addVendingMachine(machine);
		} catch (OverwriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//assertTrue("Vending Machine should be added.", 
		//		man.vendingMachineExists(machine.getID()));
	}

	@Test
	public void testRemoveMachine() {
		try {
			man.addVendingMachine(machine);
		} catch (OverwriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//assertTrue("Vending Machine should be added.", 
		//		man.vendingMachineExists(machine.getID()));
		man.deleteVendingMachine(machine.getID().getId());
		//assertFalse("Vending Machine should no longer exist.",
		//		man.vendingMachineExists(machine.getID()));
	}
	
}

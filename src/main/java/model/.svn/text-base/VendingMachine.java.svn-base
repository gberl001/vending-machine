/**
 * 
 */
package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import control.Controller;
import exceptions.EmptySlotException;
import exceptions.ExpiredProductException;
import exceptions.InsufficientProductsException;
import exceptions.InvalidLocationException;
import exceptions.RecalledProductException;
import exceptions.SlotOverflowException;

/**
 * VendingMachine acts as a Model in an MVC model.  It keeps track
 * of the data and performs calculations and manipulations with data.
 * 
 * @author jhf3617
 */
public class VendingMachine implements Serializable, 
		Comparable<VendingMachine> {

	public static enum STATE {
		READY,
		RESTOCK
	}
	private static final long serialVersionUID = 5226287890509251737L;
	private Inventory inventory;
	private RestockingList restockList;
	private VendingID id;
	private transient TransactionList currentTransaction;
	private boolean saveMe = true;
	private transient Controller controller;
	private STATE currentState;

	
	/**
	 * Creates a default size vending machine with an ID and a
	 * controller
	 * @param id	info about its whereabouts and unique identifier
	 * @param controller	the object that controls the vm
	 */
	public VendingMachine(VendingID id, Controller controller) {
		this(id, controller, Inventory.MIN_ROWS, Inventory.MIN_COLS, Slot.MAX_CAPACITY);
	}
	
	/**
	 * Creates a vending machine of specified dimensions
	 * @param id	info about its whereabouts and unique identifier
	 * @param controller	the object that controls the vm
	 * @param rows	number of rows
	 * @param cols	number of columns
	 * @param capacity	capacity of each slot
	 */
	public VendingMachine(VendingID id, Controller controller, 
			int rows, int cols, int capacity) {
		this.id = id;
		this.controller = controller;
		this.restockList = null;
		this.inventory = new Inventory(rows, cols, capacity);
		this.currentState = STATE.READY;
	}
	
	
	
	/*
	 * ***********************************************************
	 * ***********VendingMachine Methods Follow*************
	 * ***********************************************************
	 */
	
	/**
	 * Get the ID tag for the vending machine
	 * @return the ID for this vending machine
	 */
	public VendingID getID() {
		return id;
	}
	
	public void setSaveMe(boolean b) {
		this.saveMe = b;
		saveState();
	}
	
	public void setState(STATE state) {
		this.currentState = state;
		saveState();
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public boolean isLocked() {
		return !(currentState == STATE.READY);
	}
	
	/*
	 * Compare Vending Machines based on ID
	 */
	@Override
	public int compareTo(VendingMachine o) {
		int retVal = 0;
		retVal += this.id.getId().compareTo(o.id.getId());
		return retVal;
	}
	
	
	public static Comparator<VendingMachine> FruitNameComparator = 
			new Comparator<VendingMachine>() {

		@Override
		public int compare(VendingMachine v1, VendingMachine v2) {
			String v1ID = v1.id.getId();
			String v2ID = v2.id.getId();
			
			return v1ID.compareTo(v2ID);
		}
	
	}; 
	
	private static Date simulatedDate = new Date(); // Initialize to today
	
	public static Date getSimulatedDate() {
		return simulatedDate;
	}
	
	public void setSimulatedDate(Date date) {
		VendingMachine.simulatedDate = date;
	}
	
	/*
	 * ***********************************************************
	 * ***********Customer Transaction Methods Follow*************
	 * ***********************************************************
	 */

	/**
	 * Add a product to the current Transaction.  Will add a new or 
	 * alter qty of a line item in the transaction.
	 * @param loc	the location of the product 
	 * @throws RecalledProductException
	 * @throws ExpiredProductException
	 * @throws EmptySlotException
	 */
	public void addToOrder(int loc) throws RecalledProductException,
	                                       ExpiredProductException,
	                                       EmptySlotException,
	                                       InvalidLocationException{
		// Check if we're ready
		if (isLocked()) {
			throw new IllegalStateException("Machine is locked");
		}
		int row = loc/10;
		int col = loc%10;
		if( loc > 9 && loc < 160){
			if((row > this.getNumRows()) || ((col + 1) > this.getNumCols())){
				throw new InvalidLocationException("Invalid Location");
			}
			else if(this.getInventory().getSlotAt(loc).getNumProducts() < 1){
				throw new EmptySlotException("Slot Empty");
			}
		} 
		else {
			throw new InvalidLocationException("Invalid Location");
		}
		
		// First check what is already here from this location
		int count = 0;
		if (currentTransaction != null) {
			for(LineItem line : currentTransaction.getLineItems()) {
				if (line.getLocation() == loc) {
					count += line.getQty();
				}
			}
		}
		// Now, the desired product is going to be from echelon count
		try {
			Product p = inventory.getSlotAt(loc).getProductAt(count);
		
			// Check if it's expired 
			if (p.isExpired()) {	
				throw new ExpiredProductException("Product is expired");
			} 
			// Check if it's recalled
			else if(controller.findProduct(
					p.getUPC()).
					isRecalled()) {
				throw new RecalledProductException("Product is recalled");
			}
			else {
				// Now see if a Line Item for this location and product already exists
				boolean found = false;
				if (currentTransaction != null) {  // If transaction is null, no lines exist at all
					for(LineItem line : currentTransaction.getLineItems()) {
						if (line.getLocation() == loc && line.getUPC() == p.getUPC()) {
							// Get the price per
							int price = line.getPrice()/line.getQty();
							// Increment qty and price
							line.setQty(line.getQty() + 1);
							line.setPrice(line.getPrice()+price);
							found = true;
							break;
						}
					}
				}
				// If we didn't find a match then we need to create a new line
				if (!found) {
					LineItem newLine = new LineItem(p.getName(), p.getUPC(), loc, 1);
					// Set the total cost for products in this line item
					newLine.setPrice(this.getCostAt(loc));
					// Add the line item to the Transaction
					if (currentTransaction == null) {
						currentTransaction = new TransactionList(new Date());
					}
					currentTransaction.addLine(newLine);
				}
			}
		}
		catch(EmptySlotException e){ 
			System.out.println("the slot is empty");
			throw new EmptySlotException("Slot is empty");
		}
	}
	
	
	/**
	 * Remove a product from the Transaction
	 * @param upc the upc of the product to remove
	 */
	public void removeFromOrder(int loc) {
		this.removeFromOrder(loc, 1);
	}
	
	/**
	 * Remove a qty of products from the Transaction
	 * @param upc the upc of the product
	 * @param qty the number of that product to remove
	 */
	public void removeFromOrder(int loc, int qty) {
		// Check if we're ready
		if (isLocked()) {
			throw new IllegalStateException("Machine is locked");
		}
		// Check that there are enough to remove
		// Remove that number
		//   -if the qty matches the LineItem total, remove the whole LineItem
		// Then, most importantly, put them back into inventory
		System.err.println("Need to implement code");
	}
	
	
	/**
	 * Get a copy of the Transaction object for viewing purposes
	 * @return a copy of the Transaction object
	 */
	public TransactionList getTransaction() {
		// Check if we're ready
		if (isLocked()) {
			throw new IllegalStateException("Machine is locked");
		}
		return currentTransaction;
	}

	/**
	 * Complete the Transaction.  Logs Transaction line items in the global DB.
	 * @throws IOException	if log file cannot be accessed.
	 */
	public void completeTransaction() throws IOException {
		// Check if we're ready
		if (isLocked()) {
			throw new IllegalStateException("Machine is locked");
		}
		if (currentTransaction == null) {
			return;
		}
		// Log (id, date, prodName, type, upc, qty, location, price)
		for (LineItem i : currentTransaction.getLineItems()) {
			Product p = controller.findProduct(i.getUPC());
			String[] iData = new String[] {
					String.valueOf(this.id.getId()),
					String.valueOf(currentTransaction.getDate()),
					p.getName(), 
					String.valueOf(p.getType()),
					String.valueOf(p.getUPC()), 
					String.valueOf(i.getQty()), 
					String.valueOf(i.getLocation()),
					String.valueOf(i.getPrice())
					};
			controller.writeToTransactionLog(iData);
			// Now remove qty number of products from inventory
			for (int j = 0; j < i.getQty(); j++) {
				inventory.getSlotAt(i.getLocation()).removeProductAt(0);
			}
			

		}
		currentTransaction = null;
		saveState();
	}

	/**
	 * Cancel the current Transaction
	 */
	public void cancelTransaction() {
		// Check if we're ready
		if (isLocked()) {
			throw new IllegalStateException("Machine is locked");
		}
		currentTransaction = null;
	}
	
	/*
	 * ***********************************************************
	 * **************RestockingList Methods Follow****************
	 * ***********************************************************
	 */
	
	/**
	 * Get the RestockingList object for this vending machine.  It will
	 * be null if one has not yet been created.
	 * @return the RestockingList object
	 */
	public RestockingList getRestockList() {
		if (this.restockList == null) {
			this.restockList = new RestockingList();
		}
		return this.restockList;
	}
	
	public void setRestockingList(RestockingList list) {
		// Check if we're ready
		if (isLocked()) {
			throw new IllegalStateException("Machine is locked");
		}
		this.restockList = list;
		saveState();
	}
	
	/**
	 * Automate restocking process
	 * void
	 * @throws SlotOverflowException 
	 */
	public void finishRestockList() throws SlotOverflowException {
		Iterator<LineItem> itr = this.restockList.getLineItems().iterator();
		LinkedList<LineItem> removeItems = new LinkedList<LineItem>();
		LinkedList<LineItem> addItems = new LinkedList<LineItem>();
		LineItem tempLineItem = null;
		Product tempProduct = null;
		int slotNumber = 0;
		int startEchelon = 0;
		int qty = 0;
		int price = 0;
		
		//split the LineItems into removing and adding
		while(itr.hasNext()) {
			tempLineItem = itr.next();
			if(tempLineItem.getQty() < 0) {
				removeItems.add(tempLineItem);
			} else {
				addItems.add(tempLineItem);
			}
		}
		
		//deal with removing first
		itr = removeItems.iterator();
		while(itr.hasNext()) {
			tempLineItem = itr.next();
//			tempProduct = ProductCatalog.viewProduct(tempLineItem.getProductUPC());
			slotNumber = tempLineItem.getLocation();
			startEchelon = tempLineItem.getStartEchelon();
			qty = Math.abs(tempLineItem.getQty());
			for(int i = 0; i < qty; i++) {
				inventory.getSlotAt(slotNumber).removeProductAt(startEchelon);
			}
		}
		
		//then deal with adding items
		itr = addItems.iterator();
		while(itr.hasNext()) {
			tempLineItem = itr.next();
			tempProduct = controller.findProduct(tempLineItem.getUPC());
			qty = tempLineItem.getQty();
			price = tempLineItem.getPrice();
			slotNumber = tempLineItem.getLocation();
//			startEchelon = tempLineItem.getStartEchelon();
			try {
				this.addProductAt(tempProduct, slotNumber, qty);
			} catch (InvalidLocationException e) {
				e.printStackTrace();
			}
			this.setCostAt(slotNumber, price);
		}
		
		this.restockList = null;
	}
	
	//*******************************************************************
	//*******************************************************************
	//****************Event Handlers Follow******************************
	//*******************************************************************
	//*******************************************************************
	
	private void saveState() {
		if (saveMe) {
			controller.updateVendingMachine(this.getID().getId(), this);
		}
	}
	
	/*
	 * ***********************************************************
	 * *****************Inventory Methods Follow******************
	 * ***********************************************************
	 */
	
	/**
	 * Get the inventory object for viewing purposes
	 * @return a copy of the Inventory object
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	
	/**
	 * Add a product to a specific location.
	 * @param p		the product to add
	 * @param slotLocation	the location for which to add the product
	 * @throws SlotOverflowException 
	 */
	public void addProductAt(Product p, int slotLocation) throws SlotOverflowException {
		try {
			this.addProductAt(p, slotLocation, 1);
		} catch (InvalidLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void removeProductAt(int loc) {
		inventory.getSlotAt(loc).removeProductAt(0);
	}
	
	
	public void removeProductAt(int loc, int qty, long upc) throws InsufficientProductsException, InvalidLocationException {
		int count = 0;
		long tmpUPC = 0;
		try{
			this.getCapacityAt(loc);
		}catch(Exception e){
			throw new InvalidLocationException("Invalid Location");
		}
		for(int i = 0; i < this.getCapacityAt(loc); i++){
			try {
				tmpUPC = this.getInventory().getSlotAt(loc).getProductAt(i).getUPC();
			} catch (EmptySlotException e) {
				tmpUPC = 0;
			}
			if(tmpUPC == upc){
				count++;
			}
		}	
	
		if(count < qty){;
			throw new InsufficientProductsException("Request to remove more products than available in slot");
		}
		for(int i = 0; i < qty; i++){
			for(int j = 0; j < this.getInventory().getNumProducts(); j++){
				try {
					tmpUPC = this.getInventory().getSlotAt(loc).getProductAt(j).getUPC();
					if(tmpUPC == upc){
						this.getInventory().getSlotAt(loc).removeProductAt(j);
						break;
					}
				} catch (EmptySlotException e) {}
			}
		}	
	}
	
	/**
	 * Add a number of products to a specific location.  If there is not
	 * enough space to add all products, no products will be added
	 * @param p		the product to add
	 * @param slotLocation	the location for which to add the product
	 * @param qty	the number of products to add
	 * @throws SlotOverflowException 
	 */
	public void addProductAt(Product p, int slotLocation, int qty) throws SlotOverflowException, InvalidLocationException {
		// Make sure there is adequate space for the batch
		try{
			this.getCapacityAt(slotLocation);
		}catch(Exception e){
			throw new InvalidLocationException("Invalid Location");
		}
		Slot s = inventory.getSlotAt(slotLocation);
		if (s.getCapacity() - s.getNumProducts() < qty) {
			// If there are not enough, don't add any just throw an exception
			throw new SlotOverflowException("No products added, insufficient space");
		} else {
			for (int i = 0; i < qty; i++) {
				s.addProduct(p);
			}
		}
		saveState();
	}
	
	/**
	 * View the first Product at a specific location
	 * @param slotLocation the location for which to view a product
	 * @return	a copy of the product at that location in the first slot
	 */
	public Product getProductAt(int slotLocation) {
		return this.getProductAt(slotLocation, 0);
	}
	
	/**
	 * View Product at a specific location and echelon
	 * @param slotLocation 		the location for which to view a product
	 * @param echelon	the echelon (depth) for which to view a product
	 * @return	a copy of the product at that location and depth
	 */
	public Product getProductAt(int slotLocation, int echelon) {
		Product p = null;
		try{
			p = inventory.getSlotAt(slotLocation).getProductAt(echelon);
		}
		catch (EmptySlotException e){ }
		return p;
	}
	
	/**
	 * Set the cost for a particular location
	 * @param slotLocation 	the location for which to set the cost
	 * @param cost	the cost to set at that location
	 */
	public void setCostAt(int slotLocation, int cost) {
		if(cost < 0) {
			cost = -cost;
		}
		inventory.getSlotAt(slotLocation).setCost(cost);
		saveState();
	}
	
	/**
	 * Get the cost for products at a location
	 * @param slotLocation the location for which to retrieve the cost
	 * @return the cost at that location
	 */
	public int getCostAt(int slotLocation) {
		return inventory.getSlotAt(slotLocation).getCost();
	}
	
	/**
	 * Get the capacity at a given location
	 * @param slotLocation the capacity for that location
	 */
	public int getCapacityAt(int slotLocation) {
		return inventory.getSlotAt(slotLocation).getCapacity();
	}
	
	/**
	 * Get a list of inventory locations
	 * @return the list of inventory locations
	 */
	public List<Integer> listLocations() {
		return inventory.listLocations();
	}
	
	/**
	 * Get the number of products in a location
	 * @param slotLocation	the location for which to count the number of products
	 * @return the number of products in a location
	 */
	public int getNumProductsAt(int slotLocation) {
		return inventory.getSlotAt(slotLocation).getNumProducts();
	}
	
	/**
	 * Get the number of rows in this vending machine
	 * @return the number of rows
	 */
	public int getNumRows() {
		return inventory.getNumRows();
	}
	
	/**
	 * Get the number of columns in this vending machine
	 * @return the number of columns
	 */
	public int getNumCols() {
		return inventory.getNumCols();
	}
	
	/**
	 * Everyone needs a toString()
	 */
	@Override
	public String toString() {
		return "Vending Machine ID: " + id.getId();
	}
	
}

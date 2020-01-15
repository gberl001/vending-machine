package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dao.DAOManager;
import exceptions.OverwriteException;
import model.Product;
import model.VendingMachine;

public class Controller {

	public static final int NUM_DIGITS = 10;
	
	private DAOManager dao;
	private List<ActionListener> listeners;
	private static final String DB_DIR = "database";
	public static final String VM_PATH =  DB_DIR + "/machines.db";
	public static final String LOG_PATH =  DB_DIR + "/transaction_log.csv";
	public static final String CATALOG_PATH =  DB_DIR + "/catalog.db";
	
	/**
	 * No args constructor
	 */
	public Controller() {
		dao = DAOManager.getInstance();
	}
	
	/**
	 * Retrieve a vending machine
	 * @param id the id of the vending machine to retrieve
	 * @return the desired vending machine
	 */
	public VendingMachine findVendingMachine(String id) {
		Map<String, VendingMachine> machines = loadVM();
		VendingMachine retVal = machines.get(id);
		retVal.setController(this);
		retVal.setSaveMe(true);
		return retVal;
	}

	/**
	 * Add a vending machine to storage
	 * @param machine the machine to add
	 * @throws OverwriteException when trying to add a vending machine with the
	 * same ID as one that already exists.
	 */
	public void addVendingMachine(VendingMachine machine) throws OverwriteException {
		HashMap<String, VendingMachine> machines = loadVM();
		if (machines == null) {
			machines = new HashMap<String, VendingMachine>();
		}
		if (machines.get(machine.getID().getId()) != null) {
			throw new OverwriteException("Vending Machine already exists: " + machine.getID().getId());
		}
		machines.put(machine.getID().getId(), machine);
		saveVM(machines);
		fireEvent(this, "addVendingMachine");
	}
	
	/**
	 * Update an existing vending machine.  This will overwrite the stored
	 * version of the vending machine
	 * @param oldID the old id, this is necessary in the event that an id is 
	 * changed
	 * @param machine the vending machine to update
	 */
	public void updateVendingMachine(String oldID, VendingMachine machine) {
		HashMap<String, VendingMachine> machines = loadVM();
		machines.remove(machine.getID());
		machines.put(machine.getID().getId(), machine);
		saveVM(machines);
		fireEvent(this, "updateVendingMachine");
	}

	/**
	 * Delete a vending machine from storage.
	 * @param id the id of the vending machine to remove
	 */
	public void deleteVendingMachine(String id) {
		HashMap<String, VendingMachine> machines = loadVM();
		machines.remove(id);
		saveVM(machines);
		fireEvent(this, "removeVendingMachine");
	}

	/**
	 * Get the last integer id of a machine, use this to get a suggested next
	 * auto id.
	 * @return the max id.
	 */
	public int getMaxID() {
		HashMap<String, VendingMachine> machines = loadVM();
		Iterator<String> itr = machines.keySet().iterator();
		int max = -1;
		int temp = max;
		int previous = temp;
		while(itr.hasNext()) {
			try{
				previous = temp;
				temp = Integer.parseInt(itr.next());
				if(temp > max) {
					max = temp;
				}
			} catch(NumberFormatException e){
				temp = previous;
			}
			
		}
		return max;
	}
	
	/**
	 * List all vending machines in storage
	 * @return a list of vending machines
	 */
	public List<VendingMachine> listVendingMachines() {
		Map<String, VendingMachine> machines = loadVM();
		List<VendingMachine> retVal = 
				new ArrayList<VendingMachine>(machines.values());
		for(VendingMachine vm : retVal) {
			vm.setController(this);
		}
		Collections.sort(retVal);
		return retVal;
	}
	
	/**
	 * Write a line in the CSV log file
	 * @param fields an array of fields to write as a line.
	 * @throws IOException if the log file cannot be accessed.
	 */
	public void writeToTransactionLog(String[] fields) throws IOException {
		ensureDirectory();
		dao.appendToCSV(fields, LOG_PATH);
		fireEvent(this, "writeToLog");
	}
	
	/**
	 * Read contents of the CSV log file
	 * @return a list of arrays containing the file contents
	 * @throws IOException if the log file cannot be accessed
	 */
	public List<String[]> readTransactionLog() throws IOException {
		ensureDirectory();
		return dao.readCSV(LOG_PATH);
	}
	
	/**
	 * Utility method to load the vending machines hashmap
	 * @return a hashmap of the stored vending machines
	 */
	private synchronized HashMap<String, VendingMachine> loadVM() {
		ensureDirectory();
		HashMap<String, VendingMachine> retVal = null;
		if(new java.io.File(VM_PATH).exists()) {
			retVal = dao.thaw(VM_PATH);
		} else {
			retVal = new HashMap<String, VendingMachine>();
		}
		return retVal;
	}
	
	/**
	 * Utility method to save the vending machines hashmap
	 * @param products the hashmap to be saved
	 */
	private synchronized void saveVM(HashMap<String, VendingMachine> products) {
		ensureDirectory();
		dao.freeze(products, VM_PATH);
	}
	

	//**************PRODUCT STUFF******************************
	
	/**
	 * Retrieve a product
	 * @param upc the upc of the product to retrieve
	 * @return the desired product
	 */
	public Product findProduct(long upc) {
		Map<Long, Product> products = loadProducts();
		return products.get(upc);
	}

	/**
	 * Add a product to storage
	 * @param product the product to add
	 * @throws OverwriteException when trying to add a product with the same 
	 * upc as one that already exists.
	 */
	public void addProduct(Product product) throws OverwriteException {
		HashMap<Long, Product> products = loadProducts();
		if (products == null) {
			products = new HashMap<Long, Product>();
		}
		if (products.get(product.getUPC()) != null) {
			throw new OverwriteException("Product already exists: " + product);
		}
		products.put(product.getUPC(), product);
		saveProducts(products);
		fireEvent(this, "addProduct");
	}
	
	/**
	 * Update an existing product.  This will overwrite the stored version of 
	 * the product
	 * @param oldUPC the old upc, this is necessary in the event that a upc is 
	 * changed for an existing product
	 * @param product the product to update
	 */
	public void updateProduct(long oldUPC, Product product) {
		HashMap<Long, Product> products = loadProducts();
		products.remove(product.getUPC());
		products.put(product.getUPC(), product);
		saveProducts(products);
		fireEvent(this, "updateProduct");
	}

	/**
	 * Delete a product from storage.
	 * @param upc the upc of the product to remove
	 */
	public void deleteProduct(long upc) {
		HashMap<Long, Product> products = loadProducts();
		products.get(upc).setActive(false);
		saveProducts(products);
		fireEvent(this, "removeProduct");
	}
	
	/**
	 * List all products in storage
	 * @return a list of products
	 */
	public List<Product> listProducts() {
		Map<Long, Product> products = loadProducts();
		List<Product> retVal = new ArrayList<Product>(products.values());
		Collections.sort(retVal);
		return retVal;
	}
	
	/**
	 * Utility method to load the products hashmap
	 * @return a hashmap of the stored products
	 */
	private synchronized HashMap<Long, Product> loadProducts() {
		ensureDirectory();
		HashMap<Long, Product> retVal = null;
		if(new java.io.File(CATALOG_PATH).exists()) {
			retVal = dao.thaw(CATALOG_PATH);
		} else {
			retVal = new HashMap<Long, Product>();
		}
		return retVal;
	}
	
	/**
	 * Utility method to save the products hashmap
	 * @param products the hashmap to be saved
	 */
	private synchronized void saveProducts(HashMap<Long, Product> products) {
		ensureDirectory();
		dao.freeze(products, CATALOG_PATH);
	}
	
	
	//*******************************************************************
	//*******************************************************************
	//****************Event Handlers Follow******************************
	//*******************************************************************
	//*******************************************************************
	
	/**
	 * Notify listeners of changes made to various elements of the controller
	 * @param evt the Action event that occurred.
	 */
	private void notifyListeners(ActionEvent evt) {
		if (listeners == null) {
			return;
		}
		// Notify all PropertyChangeListeners
		for (ActionListener al : listeners) {
			al.actionPerformed(evt);
	    }		
	}
	
	/**
	 * Fire an action event
	 * @param object the source of the action event
	 * @param command the actionCommand that has occurred.
	 */
	protected void fireEvent(Object object, String command) {
		notifyListeners(new ActionEvent(
							object, 
							ActionEvent.ACTION_PERFORMED,	
							command));
  	}

	/**
	 * Add an action listener to this class
	 * @param al the action listener to add
	 */
	public void addActionListener(ActionListener al) {
		if (listeners == null) {
			listeners = new ArrayList<ActionListener>();
		}
		listeners.add(al);
	}
	
	/**
	 * Remove an action listener from this class
	 * @param al the action listener to remove
	 */
	public void removeActionListener(ActionListener al) {
		if (listeners == null) {
			return;
		}
		listeners.remove(al);
	}
	
	/**
	 * Utility method to ensure the directory exists.  This will avoid
	 * unnecessary IOExceptions if the end user moves the jar from its intended
	 * directory
	 */
	private void ensureDirectory() {
		File dir = new File(DB_DIR);
		if (!dir.exists()) {
			// Create the directory
			dir.mkdir();
		}
	}
}

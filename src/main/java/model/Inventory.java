package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import exceptions.EmptySlotException;

import util.StringUtils;


/**
 * A class that implements an inventory system for a vending machine. Inventory
 * is composed of Slots which contain Products. These slots are stored in a
 * HashMap.
 * 
 * @author ggb9189
 * @author dxs3203
 *
 */
public class Inventory implements Serializable{

	private static final long serialVersionUID = -6638750976180960081L;
	private HashMap<Integer, Slot> slots;
	public static final int MIN_ROWS = 1;
	public static final int MIN_COLS = 1;
	public static final int MAX_ROWS = 15;
	public static final int MAX_COLS = 10;
	private int rows;
	private int cols;
	private int slotSize;
	
	/**
	 * Default Constructor - Initiates rows and cols to MIN values
	 */
	protected Inventory() {
		this(MIN_ROWS, MIN_COLS, Slot.MAX_CAPACITY);
	}
	
	/**
	 * Somewhat Default Constructor - rows
	 * @param rowCount  the number of rows
	 * @param colCount  the number of columns
	 */
	protected Inventory(int rowCount, int colCount) {
		this(rowCount, colCount, Slot.MAX_CAPACITY);
	}
	
	/**
	 * Three args constructor - Create an inventory with a specified row and 
	 * column count and a maximum capacity
	 * @param rowCount	the number of rows
	 * @param colCount	the number of columns
	 * @param max_capacity the maximum depth of the slots
	 */
	protected Inventory(int rowCount, int colCount, int slotSize) 
			throws IllegalArgumentException {
		if (rowCount < MIN_ROWS || rowCount > MAX_ROWS) {
			throw new IllegalArgumentException(
					"Value must be between " + MIN_ROWS + " and " + MAX_ROWS + 
					": " + rowCount);
		}
		if (colCount < MIN_COLS || colCount > MAX_COLS) {
			throw new IllegalArgumentException(
					"Value must be between " + MIN_COLS + " and " + MAX_COLS + 
					": " + rowCount);
		}
		this.rows = rowCount;
		this.cols = colCount;
		this.slotSize = slotSize;
		slots = new HashMap<Integer, Slot>();
		
		// Initiate empty locations
		for (int i = 1; i <= rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				slots.put(Integer.parseInt(i + "" + j), new Slot(Integer.parseInt(i + "" + j), 0, slotSize));
			}
		}
	}
	
	public Inventory(Inventory i) {
		this.rows = i.rows;
		this.cols = i.cols;
		this.slotSize = i.slotSize;
		this.slots = new HashMap<Integer,Slot>(i.slots);
	}

	
	/**
	 * Get the number of products in the inventory (all locations)
	 * @return the number of products in inventory
	 */
	public int getNumProducts() {
		int retVal = 0;
		for (Slot s : slots.values()) {
			retVal += s.getNumProducts();
		}
		return retVal;
	}
	
	public int getSlotSize() {
		return this.slotSize;
	}
	
	/**
	 * Get a list of locations for this inventory.  Use the location to
	 * access products.
	 * @return a list of locations for this inventory
	 */
	public List<Integer> listLocations() {
		List<Integer> retVal = new ArrayList<Integer>();
		for (Integer loc : slots.keySet()) {
			retVal.add(loc);
		}
		return retVal;
	}
	
	/**
	 * Get the number of rows in this inventory
	 * @return the number of rows in this inventory
	 */
	public int getNumRows() {
		return rows;
	}
	
	/**
	 * Get the number of columns in this inventory
	 * @return the number of columns for this inventory
	 */
	public int getNumCols() {
		return cols;
	}
	
	/**
	 * Everybody needs a toString()
	 */
	@Override
	public String toString(){
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
		StringBuilder sb = new StringBuilder();
		
		// Find the longest product name
		int longestName = 10; // Initialized with the length of "No Product"
		for (Slot s : slots.values()) {
			try {
				int length = s.getProductAt(0).getName().length();
				if (length > longestName) {
					longestName = length;
				}
			} catch (EmptySlotException e) { }
		}
		
		// Print a header
		sb.append("   Location   - " + 
				StringUtils.cPad("Product", longestName) + " - Qty - Cost\n");
		if (slots == null || slots.size() == 0) {
			sb.append("Contents EMPTY");
		}
		
		// Print the details for each location and the front product
		SortedSet<Integer> keys = new TreeSet<Integer>(slots.keySet());
		for (Integer loc : keys) {
			Slot s = slots.get(loc);
			sb.append("Location: " + StringUtils.rPad(loc + "", 3) + " - ");
			try{
				sb.append(StringUtils.rPad(s.getProductAt(0).getName(), 
						longestName) + " - " +	
						StringUtils.rPad(s.getNumProducts() + "", 3));
			}
			catch(EmptySlotException e){
				sb.append(StringUtils.rPad("No Product", longestName) + 
						" - 0  ");
			}
			sb.append(" - $" + df.format(s.getCost()/100.0f) + "\n");
		}
		return sb.toString();
	}
	
	public Slot getSlotAt(int row, int col) {
		return this.getSlotAt(mapToLocation(row, col));
	}
	
	public Slot getSlotAt(int location) {
		return slots.get(location);
	}
	
	/**
	 * Generate a location based on row and column using our own custom process
	 * @param row	the row of the location
	 * @param col	the column of the location
	 */
	private int mapToLocation(int row, int col) {
		if ((col + "").length() == 1) {
			return Integer.parseInt(row + "0" + col);
		}
		return Integer.parseInt(row + "" + col);
	}
}

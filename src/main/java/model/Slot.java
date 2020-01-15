package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import exceptions.EmptySlotException;
import exceptions.SlotOverflowException;

/**
 * Manages the adding, removing and viewing of products at a location.
 * Also manages the cost of that location.
 */
public class Slot implements Serializable  {
	
	private static final long serialVersionUID = 8168093480426868490L;
	private transient List<ActionListener> listeners;
	public static final int MAX_CAPACITY = 15;
	private int capacity;
	private ArrayList<Product> products;
	private int cost;
	private int location;


	/**
	 * Single arg constructor - Require a cost to be set for the location
	 * @param cost		the cost for this location
	 * @param capacity	the capacity for this slot
	 * @throws IllegalArgumentException when capacity is greater than allowed
	 * @throws Negative ArraySizeException when capacity is zero or negative
	 */
	protected Slot(int loc, int cost, int capacity) throws IllegalArgumentException, 
			NegativeArraySizeException {
		this.location = loc;
		this.cost = cost;
		if (capacity > MAX_CAPACITY) {
			throw new IllegalArgumentException("Capacity must be <= " + MAX_CAPACITY + ": " + capacity);
		} else if (capacity <= 0) {
			throw new NegativeArraySizeException("Capacity must be > 0: " + capacity);
		}
		this.capacity = capacity;
	}
	
	/**
	 * Add a product, the default is to add the product to 
	 * the first free space in the location which will always
	 * be at the last open position.  This is because removeProduct()
	 * always pushes the products forward.
	 * @param p the product to add
	 * @throws SlotOverflowException 
	 */
	protected void addProduct(Product p) throws SlotOverflowException {
		if (products == null) {
			products = new ArrayList<Product>(capacity);
		} else if (isFull()) {
			throw new SlotOverflowException("Slot is full");
		}
		products.add(p);
		fireEvent(this, "addProduct");
	}
	
	/**
	 * Remove a product at a specific location (depth).  All
	 * products behind this product will default to push forward.
	 * For example, if there are 10 products and you remove from
	 * echelon 3, echelon 3 will be filled with 4, 4 with 5 and
	 * the result will be that echelon 10 will be empty or filled
	 * with the product in slot 11 if there is a product there.
	 * @param echelon the depth from which to pull the product
	 */
	protected void removeProductAt(int echelon) {
		products.remove(echelon);
		fireEvent(this, "removeProduct");
	}

	/**
	 * Get a product at a specific depth location.
	 * @param echelon the depth from which to view
	 * @return the product at the specified depth
	 * @throws EmptySlotException
	 */
	public Product getProductAt(int echelon) throws EmptySlotException{
		if (products == null || products.size() == 0) {
			throw new EmptySlotException("Slot is empty");
		} else if (products.size() <= echelon) {
			throw new EmptySlotException("Slot is empty at echelon: " + echelon);
		}

		return products.get(echelon);
	}
	
	/**
	 * Set the cost for products in this location
	 * @param cost the cost for the location
	 */
	protected void setCost(int cost) {
		this.cost = cost;
		fireEvent(this, "setCost");
	}
	
	/**
	 * Get the cost for this location
	 * @return the cost for this location
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Get the number of products in this location
	 * @return the number of products in this location
	 */
	public int getNumProducts() {
		if (products == null) {
			return 0;
		}
		return products.size();
	}
	
	/**
	 * Get the maximum number of products this location can hold
	 * @return the capacity of this location
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Everyone needs a toString method
	 * @see	Object
	 */
	@Override
	public String toString(){
		String s = "List of products in slot.\n";
		if (products == null) {
			s += "Contents EMPTY";
		}
		
		for(int i = 0; i < products.size(); i++){
			s = s + "Product in slot " + (i + 1) + "\n";
			s = s + products.get(i).getName() + "\n";
			s = s + products.get(i).getType() + "\n";
			s = s + products.get(i).getUPC() + "\n\n";
		}
		return s;
	}	
	
	//*******************************************************************
	//*******************************************************************
	//****************Event Handlers Follow******************************
	//*******************************************************************
	//*******************************************************************
	
	private void notifyListeners(ActionEvent evt) {
		if (listeners == null) {
			return;
		}
		// Notify all PropertyChangeListeners
		for (ActionListener al : listeners) {
			al.actionPerformed(evt);
	    }		
	}
	
	protected void fireEvent(Object object, String command) {
		notifyListeners(new ActionEvent(
							object, 
							ActionEvent.ACTION_PERFORMED,	
							command));
  	}

	public void addActionListener(ActionListener al) {
		if (listeners == null) {
			listeners = new ArrayList<ActionListener>();
		}
		listeners.add(al);
	}
	
	public void removeActionListener(ActionListener al) {
		if (listeners == null) {
			return;
		}
		listeners.remove(al);
	}
	
	public int getLocation() {
		return location;
	}
	
	private boolean isFull() {
		return products.size() >= capacity;
	}
}
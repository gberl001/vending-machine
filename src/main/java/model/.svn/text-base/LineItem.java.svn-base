/**
 * LineItem.java
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


/**
 * @author jimiford
 *
 */
public class LineItem implements java.io.Serializable, Comparable<LineItem> {

	private static final long serialVersionUID = 1123581321L;
	private int cost;
	private String note;
	private long upc;
	private int qty;
	private int location;
	private int start;
	private boolean isComplete = false;
	private String prodName;

	/**
	 * 
	 * @param name
	 * @param upc
	 */
	protected LineItem(String name, long upc) {
		// Default to location 0 and qty 0
		this(name, upc, 0, 0);
	}
	
	/**
	 * 
	 * @param name  name of the product
	 * @param upc  UPC of product
	 * @param loc  destination slot for product
	 * @param qty  the number of products to add/remove
	 */
	public LineItem(String name, long upc, int loc, int qty) {
		this(name, upc, loc, qty, 0);
	}

	public LineItem(String name, long upc, int loc, int qty, int start) {
		this.prodName = name;
		this.upc = upc;
		this.location = loc;
		this.qty = qty;
		this.start = start;
	}
	
	/**
	 * 
	 * @return
	 * int
	 */
	public int getQty() {
		return this.qty;
	}
	
	/**
	 * 
	 * @return
	 * long
	 */
	public long getUPC() {
		return this.upc;
	}
	
	/**
	 * @return
	 * String
	 */
	public String getProductName() {
		return this.prodName;
	}
	
	/**
	 * 
	 * @param quantity
	 * void
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	/**
	 * 
	 * @param note
	 * void
	 */
	public void setNotes(String note) {
		this.note = note;
	}
	
	public String getNotes() {
		return note;
	}

	public void setLocation(int loc) {
		this.location = loc;
	}
	
	/**
	 * @return
	 * String
	 */
	public int getLocation() {
		return this.location;
	}

	public int getStartEchelon() {
		return this.start;
	}
	
	public void setIsComplete(boolean b) {
		isComplete = b;
	}
	
	public boolean isComplete() {
		return isComplete;
	}
	
	/**
	 * Test if two LineItem's are equal
	 * @return
	 * boolean
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof LineItem)) {
			return false;
		}
		
		// TODO Need to check more than just these two fields
		return this.upc == ((LineItem)o).getUPC() &&
			this.prodName.equals(((LineItem)o).getProductName());
	}
	
	
	/**
	 * 
	 * @param price
	 * void
	 */
	public void setPrice(int price) {
		this.cost = price;
	}
	
	/**
	 * @return
	 * String
	 */
	public int getPrice() {
		return this.cost;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(LineItem li) {
		// Compare based on location
		return this.location - li.location;
	}
	
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result
		.append("Product:\t" + prodName + '\n')
		.append("Qty:\t" + qty + '\n')
		.append("Cost:\t" + cost + '\n');
		return result.toString();
		
	}
}

package model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * A class that represents a product. It has fields that store product 
 * information as well as a toString which provides a nicely formatted 
 * string. 
 * 
 * @author dxs3203
 *
 */
public class Product implements Transferable, Serializable, Comparable<Product> {

	private static final long serialVersionUID = -1422181722287047553L;

	//Each product will have a TYPE field will will indicate the 
	//category it should be placed in.
	public enum TYPE{ CHIPS, CANDY, CANDY_BAR, GUM, SODA, MISC}
	private String imagePath = "";
	
    public static DataFlavor PRODUCT_FLAVOR = new DataFlavor(Product.class, "productFlavor");  
    private DataFlavor flavors[] = { PRODUCT_FLAVOR }; 
	private String name;//The name of the product
	private Long upc;//The upc of the product
	private TYPE type;//The type of the product
	private Date expDate;
	private boolean isRecalled;
	private boolean isActive;
	
	//private SimpleDateFormat expiration;
	/**
	 * The constructor for this class.
	 * 
	 * @param name the product name
	 * @param type the product type
	 * @param upc the product upc
	 */
	public Product(String name, TYPE type, Long upc){
		this.name = name;
		this.type = type;
		this.upc = upc;
		this.isActive = true;
	}

	/**
	 * Copy Constructor
	 * @param p the product to copy
	 */
	public Product(Product p) {
		if(p != null && p != this) {
			this.name = new String(p.name);
			this.type = p.type;
			this.upc = new Long(p.upc);
		}
		// Copy non constructor fields below (when we add some more)
		// ...
	}

	/*	public void setExpiration(SimpleDateFormat date){
		this.expiration = SimpleDateFormat(date);
	}
*/
	/**
	 * returns the name of the product
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	
	
/*	public Date getExpiration() {
		return expiration;
	}
*/
	/**
	 * returns the product's upc
	 * 
	 * @return upc
	 */
	public Long getUPC() {
		return upc;
	}

	/**
	 * THIS IS ONLY TO TEST THAT THE DEEP COPY WORKS
	 * @param t
	 * void
	 */
	public void setType(TYPE t) {
		this.type = t;
	}
	
	/**
	 * returns the type of the product
	 * 
	 * @return type
	 */
	public TYPE getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Product p) {
		return this.name.compareTo(p.name);
	}
	
	@Override
	public boolean equals(Object o){
		// If the object is null or isn't a Product, return false
	    if (o == null || !(o instanceof Product)) {
	    	return false;
	    }
	    // If this Product references the object they are the same
	    if (o == this) {
	    	return true;
	    }
	    // If the Product is not equal to the object, return false
	    return 	((Product)o).name.equals(this.name) &&
	    		((Product)o).upc.equals(this.upc) &&
	    		((Product)o).type.equals(this.type);
	}
	
	/**
	 * toString
	 * 
	 * @return a string representation of the product
	 */
	@Override
	public String toString() {
		return upc + "\t" + name + "\t" + type.toString();
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws 
			UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(flavor)) {    
			return this; 
		} else {   
			try {
				throw new UnsupportedFlavorException(flavor);
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			}
		} 
		return null;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavors[0].equals(flavor);
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public void setExpirationDate(Date date) {
		this.expDate = date;
	}
	
	public Date getExpirationDate() {
		return expDate;
	}
	
	public boolean isExpired() {
		// If expiration date is not set, default to false
		if (expDate == null) {
			return false;
		}
		return expDate.before(VendingMachine.getSimulatedDate());
	}
	
	public void setRecalled(boolean b) {
		this.isRecalled = b;
	}
	
	public boolean isRecalled() {
		return isRecalled;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}

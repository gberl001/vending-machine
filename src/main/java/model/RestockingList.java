/**
 * RestockingList.java
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
public class RestockingList extends ProductList
		implements java.io.Serializable {

	private static final long serialVersionUID = 1123581321L;

	private String note;
	
	/**
	 * 
	 */
	public RestockingList() {
		this.note = "";
	}
	
	
	/**
	 * 
	 * @return
	 * String
	 */
	public String getNote() {
		return this.note;
	}
	
	/**
	 * 
	 * @param note
	 * void
	 */
	public void setNote(String note) {
		super.fireEvent(this, "note", this.note, this.note = note);
	}

}

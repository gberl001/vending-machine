package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductList implements Serializable {

	private static final long serialVersionUID = 1123581321L;
	private List<LineItem> list;
	private transient List<PropertyChangeListener> listeners;
	
	protected ProductList() {
		list = new ArrayList<LineItem>();
	}
	
	public void addProduct(Product p) {
		if (list == null) {
			list = new ArrayList<LineItem>();
		}
		// Create a new line item and fire event
		int oldSize = list.size();
		list.add(new LineItem(p.getName(), p.getUPC()));
		fireEvent(this, "listSize", oldSize, list.size());
	}
	
	public void addLine(LineItem line) {
		if (list == null) {
			list = new ArrayList<LineItem>();
		}
		// Create a new line item and fire event
		int oldSize = list.size();
		list.add(line);
		fireEvent(this, "listSize", oldSize, list.size());
	}
	
	public List<LineItem> getLineItems() {
		return list;
	}
	
	public void removeLineItem(LineItem line) {
		if (list == null) {
			return;
		}
		int oldSize = list.size();
		list.remove(line);
		fireEvent(this, "listSize", oldSize, list.size());
	}
	
	@Override
	public String toString() {
		int size = 0;
		if (list != null) {
			size = list.size();
		}
		return "Product List of size " + size;
	}
	
	
	//*******************************************************************
	//*******************************************************************
	//****************Event Handlers Follow******************************
	//*******************************************************************
	//*******************************************************************
	
	private void notifyListeners(PropertyChangeEvent evt) {
		if (listeners == null) {
			return;
		}
		// Notify all PropertyChangeListeners
		for (PropertyChangeListener pl : listeners) {
			pl.propertyChange(evt);
	    }		
	}
	
	protected void fireEvent(Object object, String property, 
			int oldValue, int newValue) {
		// Don't send the event if nothing really changed
		if (oldValue == newValue) {
			return;
		}
		notifyListeners(new PropertyChangeEvent(
							this, 
							property,	
							oldValue, 
							newValue));
	}
	
	protected void fireEvent(Object object, String property, String oldValue, 
			String newValue) {
		// Don't send the event if nothing really changed
		if (oldValue != null && newValue != null) {
			if (oldValue.equals(newValue)) {
				return;
			}
		}
		notifyListeners(new PropertyChangeEvent(
							this, 
							property,	
							oldValue, 
							newValue));
  	}

	public void addChangeListener(PropertyChangeListener pl) {
		if (listeners == null) {
			listeners = new ArrayList<PropertyChangeListener>();
		}
		listeners.add(pl);
	}
	
	public void removeChangeListener(PropertyChangeListener pl) {
		if (listeners == null) {
			return;
		}
		listeners.remove(pl);
	}
	
	public void printList() {
		System.out.println(this);
	}
	
}

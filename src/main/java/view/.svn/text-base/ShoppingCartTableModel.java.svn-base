package view;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.LineItem;


public class ShoppingCartTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4358790306984167048L;
	List<LineItem> list;
	String[] columnNames = new String[]{"Name", "Qty", "Price"};
	public ShoppingCartTableModel(List<LineItem> list2){
		this.list = list2;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		LineItem lineRow = list.get(row);
		switch(col){
		case(0): 
			return lineRow.getProductName();
		case(1):
			return lineRow.getQty();
		case(2):
			return "$" + new DecimalFormat("0.00").format(lineRow.getPrice()/100.0);
		default:
			return "Error";
		}
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

}

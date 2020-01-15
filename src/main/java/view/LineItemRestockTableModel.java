package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.LineItem;


@SuppressWarnings("serial")
public class LineItemRestockTableModel extends AbstractTableModel {

	private List<LineItem> tableData;
	private String[] headers = new String[] 
			{"Product", "Slot", "Action", "Quantity"};
	
	public LineItemRestockTableModel(List<LineItem> tableData) {
		this.tableData = tableData;
	}
	
	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		if (tableData == null) {
			return 0;
		}
		return tableData.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		LineItem rowData = tableData.get(row);
		switch (col) {
		case 0: return rowData.getProductName();
		case 1: return rowData.getLocation();
		case 2: return (rowData.getQty() > 0)?"Add":"Remove";
		case 3: return Math.abs(rowData.getQty());
		default: return "FIXME";
		}
			
	}
	
	public LineItem getLineAt(int row){
		LineItem li = tableData.get(row);
		return li;
	}
	@Override
	public Class<?> getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		return false;
	}
	@Override
	public String getColumnName(int col) {
		return headers[col];
	}
}

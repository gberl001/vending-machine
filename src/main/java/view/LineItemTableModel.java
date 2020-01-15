package view;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.LineItem;


@SuppressWarnings("serial")
public class LineItemTableModel extends AbstractTableModel {

	private List<LineItem> tableData;
	private String[] headers = new String[] 
			{"C", "Product", "UPC", "Action", "Qty", "Slot", "Slot Price", "Notes" };
	
	public LineItemTableModel(List<LineItem> tableData) {
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
		case 0: return rowData.isComplete();
		case 1: return rowData.getProductName();
		case 2: return rowData.getUPC();
		case 3: 
			if (rowData.getQty() > 0) {
				return "Add";
			} else {
				return "Remove";
			}
		case 4: return rowData.getQty();
		case 5: return rowData.getLocation();
		case 6: return new DecimalFormat("0.00").format(rowData.getPrice()/100.0);
		case 7: return rowData.getNotes();
		default: return "FIXME";
		}
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		Object value =  getValueAt(0, col);
		return (value==null?Object.class:value.getClass());
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		switch(col) {
		case 4:
		case 5:
		case 6:
		case 7:
			return true;
		}
		return false;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		switch (col) {
		case 4:
			tableData.get(row).setQty((Integer) value);
			break;
		case 5:
			tableData.get(row).setLocation((Integer) value);
			break;
		case 6:
			tableData.get(row).setPrice((int) (Float.parseFloat(value+"")*100));
			break;
		case 7:
			tableData.get(row).setNotes((String) value);
			break;
		default:
			// Throw exception maybe??  (non editable column)
		}

		// Tell the super to notify listeners
		// We have to call RowsUpdated because one column's value is dependent
		// on another, if we update one we want to make sure all are updated.
		this.fireTableRowsUpdated(row, row);
	}
	
	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

	public void removeRow(int row) {
		tableData.remove(row);
		this.fireTableRowsDeleted(row, row);
	}
}

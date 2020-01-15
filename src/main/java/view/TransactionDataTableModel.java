package view;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class TransactionDataTableModel extends AbstractTableModel {

	private List<String[]> tableData;
	private String[] headers = new String[] {"Vending ID",
			"Transaction Date","Product Name","Product Type",
			"Product UPC","Qty","Vending Location","Cost"};
	
	public TransactionDataTableModel(List<String[]> tableData) {
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
		if (tableData.size() == 0) {
			return "";
		}
		switch (col) {
			case 7:  return new DecimalFormat("0.00").format(
					Integer.parseInt(tableData.get(row)[col].replaceAll("\"", "")) / 100.0); 
		}
		return tableData.get(row)[col].replaceAll("\"", "");
	}
	
	public String[] getElementAt(int row) {
		if (tableData == null) {
			return null;
		}
		return tableData.get(row);
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
	
	@Override
	public String getColumnName(int col) {
		return headers[col];
	}
}

package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.VendingMachine;


@SuppressWarnings("serial")
public class VendingMachineDetailsTableModel extends AbstractTableModel {

	private List<VendingMachine> tableData;
	private String[] headers = new String[] {"Address", "City", "State", "Zip", "Location", "ID"};
	
	public VendingMachineDetailsTableModel(List<VendingMachine> tableData) {
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
		VendingMachine rowData = tableData.get(row);
		switch (col) {
		case 0: return rowData.getID().getAddress();
		case 1: return rowData.getID().getCity();
		case 2: return rowData.getID().getState();
		case 3: return rowData.getID().getZip();
		case 4: return rowData.getID().getLocation();
		case 5: return rowData.getID().getId();
		default: return "FIXME";
		}
	}
	
	public VendingMachine getElementAt(int row) {
		return tableData.get(row);
	}
	
	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

}

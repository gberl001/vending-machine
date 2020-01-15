package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.VendingMachine;


@SuppressWarnings("serial")
public class VendingMachineTableModel extends AbstractTableModel {

	private List<VendingMachine> tableData;
	private String[] headers = new String[] {"Location", "ID"};
	
	public VendingMachineTableModel(List<VendingMachine> tableData) {
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
		case 0: return rowData.getID().getAddress() + ", " + rowData.getID().getState();
		case 1: return rowData.getID().getId();
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

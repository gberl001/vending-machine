package view;

import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import util.ImageUtil;

import model.Product;


@SuppressWarnings("serial")
public class ProductTableModel extends AbstractTableModel {

	private List<Product> tableData;
	private String[] headers = new String[] {"Img", "Product"};
	
	public ProductTableModel(List<Product> tableData) {
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
		switch (col) {
		case 0: try {
					return new ImageIcon(ImageUtil.LoadSquareImage(
							tableData.get(row).getImagePath(), 50)
									);
				} catch (IOException ignore) { }
		return null;
		case 1: return tableData.get(row).getName();
		default: return "FIXME";
		}
	}
	
	public Product getElementAt(int row) {
		if (tableData == null) {
			return null;
		}
		return tableData.get(row);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    if(columnIndex == 0){
	        return ImageIcon.class;
	    }
	    return String.class;
	}
	
	@Override
	public String getColumnName(int col) {
		return headers[col];
	}
}

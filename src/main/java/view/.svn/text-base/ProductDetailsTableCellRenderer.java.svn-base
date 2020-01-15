package view;

import javax.swing.*;
import javax.swing.table.*;

import model.Product;

import java.awt.*;

@SuppressWarnings("serial")
public class ProductDetailsTableCellRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent (JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int col) {
		
		Component cell = super.getTableCellRendererComponent(table, value, 
				isSelected, hasFocus, row, col);
		
		if (!isSelected) {
			Product p = ((ProductDetailsTableModel)table.getModel()).getElementAt(table.convertRowIndexToModel(row));
			if (p.isRecalled()) {
				cell.setBackground(Color.RED);
			} else if (!p.isActive()) {
				cell.setBackground(Color.YELLOW);
			} else {
				if (row % 2 == 0) {
					cell.setBackground(Color.WHITE);	
				} else {
					cell.setBackground(new Color(242,242,242));
				}
			}
		} 
		
		return cell;
	}
}

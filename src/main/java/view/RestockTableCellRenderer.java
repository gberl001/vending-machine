package view;

import javax.swing.*;
import javax.swing.table.*;

import model.LineItem;

import java.awt.*;

@SuppressWarnings("serial")
public class RestockTableCellRenderer extends DefaultTableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent (JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int col) {
		
		Component cell = super.getTableCellRendererComponent(table, value, 
				isSelected, hasFocus, row, col);
		
		if (!isSelected) {
			LineItem li = ((LineItemRestockTableModel)table.getModel()).getLineAt(row);
			if (li.isComplete()) {
				cell.setBackground(Color.GRAY);
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

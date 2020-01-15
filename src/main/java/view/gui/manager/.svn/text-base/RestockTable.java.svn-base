package view.gui.manager;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class RestockTable extends JTable {

	public RestockTable() {
		
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component c = super.prepareRenderer(renderer, row, col);
        if (c instanceof JComponent && col == 7 && getValueAt(row, col) != null) {
            JComponent jc = (JComponent) c;
            String str = getValueAt(row, col).toString();
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            int start = 0;
            // Since it would normally be a huge single line, let's break it up
            for (int i = 0; i <= Math.floor(str.length() / 40); i++) {
            	int end = start + 40;
            	// If we reached the end...
            	if (end >= str.length()) {
            		end = str.length();
            	}
            	sb.append(str.substring(start, end) + "<br>");
            	start = end;
            }
            sb.append("</html>");
        	jc.setToolTipText(sb.toString());
        }
        return c;
    }
	
}

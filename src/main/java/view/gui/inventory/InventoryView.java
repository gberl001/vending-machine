package view.gui.inventory;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class InventoryView extends JPanel {

	private int cols;
	private int rows;
	
	public InventoryView() {
		this.setBackground(Color.LIGHT_GRAY.darker().darker());
		
	}
	
	public InventoryView(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
        initComponents();
        this.setBackground(Color.LIGHT_GRAY.darker().darker());
	}
	
	@Override
	public void setSize(int rows, int cols) {
		this.cols = cols;
		this.rows = rows;
		initComponents();
	}


	private void initComponents() {
		
		this.setLayout(new GridLayout(rows, cols));

	}
    
	@SuppressWarnings("rawtypes")
	public void addSlotView(SlotView sv) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        sv.setFontHints((Map)(tk.getDesktopProperty("awt.font.desktophints")));
        
		this.add(sv);
	}
	
}

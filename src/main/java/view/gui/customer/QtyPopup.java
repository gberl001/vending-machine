package view.gui.customer;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import view.TitleLabel;

@SuppressWarnings("serial")
public class QtyPopup extends JDialog {
	
	protected JButton btnUp;
	protected JButton btnDown;
	protected JTextField tfQty;
	protected JButton btnConfirm;
	private GridBagConstraints c;
	
	public QtyPopup(JFrame parent) {
		super(parent, true);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		this.getRootPane().setOpaque(false);
		this.getContentPane().setBackground(new Color(0, true));
		this.setBackground(new Color(0, true));
		
		initComponents();
		fillComponents();
		
		this.setSize(300, 125);
		int x;
		int y;
		if (parent == null) {
			x = 50;
			y = 50;
		} else {
			x = parent.getX() + parent.getWidth()/2 - this.getWidth()/2;
			y = parent.getY() + parent.getHeight()/2 - this.getHeight()/2;
		}
		
		this.setLocation(x, y);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private void initComponents() {
		btnUp = new JButton("More");
		btnDown = new JButton("Less");
		tfQty = new JTextField();
		btnConfirm = new JButton("Confirm");
	}
	
	private void fillComponents() {
		c = new GridBagConstraints();
		JPanel gbPanel = new TransparentPanel(new GridBagLayout());
		int row;
		c.insets = new Insets(2,2,2,2);
		
		// First row
		row = 0;
		setGridBagConstraints(c, 0, row, GridBagConstraints.BOTH, 0.0, 0.0, 6, 1);
		gbPanel.add(new TitleLabel("Snickers", SwingConstants.CENTER), c);
		
		// Second row
		row = 1;
		setGridBagConstraints(c, 0, row, GridBagConstraints.BOTH, 0.0, 0.0, 2, 2);
		gbPanel.add(btnUp, c);
		setGridBagConstraints(c, 2, row, GridBagConstraints.BOTH, 0.0, 0.0, 2, 2);
		gbPanel.add(btnDown, c);
		setGridBagConstraints(c, 4, row, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		gbPanel.add(new JLabel("Quantity:"), c);
		setGridBagConstraints(c, 5, row, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		gbPanel.add(tfQty, c);
		
		// Third row
		row = 2;
		setGridBagConstraints(c, 5, row, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		gbPanel.add(btnConfirm, c);
		
		this.add(gbPanel, BorderLayout.CENTER);
	}
	
	
    private GridBagConstraints setGridBagConstraints(GridBagConstraints c,
    		int x, int y, int fill, double weightx, double weighty, int 
    		width, int height) {
        c.weightx = weightx;
        c.weighty = weighty;
        c.fill = fill;
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        // Reset the insets each time, we use it a few times so this
        //  will undo any times we do it.
        //c.insets = new Insets(0, 0, 0, 0);
        return c;
    }
    
    class TransparentPanel extends JPanel {
    	
    	TransparentPanel(LayoutManager lm) {
    		super(lm);
    	}
    	
    	@Override
    	public void paintComponent(Graphics g) {
    		Graphics2D g2d = (Graphics2D) g.create();
			// Get the old composite
    		Composite origComp = g2d.getComposite();
    		
			// Set the composite to 75% transparency
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			
			// Draw rectangle with rounded corners using background color
	        g2d.setColor(getBackground());
			g2d.fillRoundRect(0,0,getWidth(),getHeight(),50,50);
			
			// Clean up
			g2d.setComposite(origComp);
			g2d.dispose();
    	}
    }
    
    public static void main(String[] args) {
    	JDialog dlg = new QtyPopup(null);
    	dlg.setVisible(true);
    }
	
}

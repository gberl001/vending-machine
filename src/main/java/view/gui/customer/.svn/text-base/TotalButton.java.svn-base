package view.gui.customer;
import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JButton;

/**
 * A custom button class for displaying the total for an order
 * 
 * @author Geoff Berl
 *
 */

@SuppressWarnings("serial")
public class TotalButton extends JButton {
	private String amount;
	
	/**
	 * A constructor for this class
	 * @param label
	 * @param amount
	 */
	public TotalButton(String label, int amount) {
		super(label);
		this.setTotal(amount);  
	}
	
	/**
	 * setTotal
	 * @param amount
	 */
	public void setTotal(double amount) {		
		this.amount = "$" + new DecimalFormat("0.00").format(amount/100);	
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int x = (int) (this.getHeight()*0.47);
		int y = (int) (this.getHeight()*0.75);
		g.drawString(amount, x, y);
		g.setColor(Color.GREEN);
	}
	
}

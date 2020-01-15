package view.gui.inventory;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import exceptions.EmptySlotException;

import model.Product;
import model.Slot;

import util.ImageUtil;
import view.gui.restocker.RestockerView;


@SuppressWarnings("serial")
public class SlotView extends JPanel implements ActionListener {

	private Image image;
	private String textValue = "<<Image>>";
	@SuppressWarnings("rawtypes")
	private Map desktopHints = null;
	private JPanel indicator;
	private Slot model;
	private AnimationPane glassPane;
	private JFrame owner;
	
	public SlotView(Slot model, AnimationPane glassPane, JFrame owner) {
		super();
		this.owner = owner;
		this.glassPane = glassPane;
		this.setMinimumSize(new Dimension(50,50));
		
		this.setOpaque(false);
		
		// Create a Lowered border
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
		
		// Create and add the indicator
		indicator = new Indicator(0, "");
		this.setLayout(new BorderLayout());
		this.add(indicator, BorderLayout.SOUTH);
		this.model = model;
		this.model.addActionListener(this);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw the image to fill the slot minus the indicator
		int imgHeight = this.getHeight() - this.indicator.getHeight();
		
		
		Graphics2D g2d = (Graphics2D) g.create();
		if (image == null) {
			// Enable antialiasing and print text if we couldn't find a picture
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
	        if (desktopHints != null) {
	            g2d.addRenderingHints(desktopHints);
	        }
			g2d.drawString(textValue, 2, this.getHeight()/2);
		} else {
			g2d.drawImage(image, 0, 0, this.getWidth(), imgHeight, this);
		}
	}

	public void setFontHints(@SuppressWarnings("rawtypes") Map desktopHints) {
		this.desktopHints = desktopHints;
	}
    
	protected int getSlotLocation() {
		return Integer.parseInt(((Indicator)indicator).lblLocation.getText());
	}
	
    class Indicator extends JPanel {
    	
    	private JLabel lblCost;
    	private JLabel lblLocation;
    	
    	public Indicator(int cost, String loc) {
    		super(); 
    		this.setCost(cost);
    		this.setLocation(loc);
    		this.initComponents();
    	}
    	
    	private void initComponents() {
    		this.setLayout(new GridLayout(1, 2));
    		this.add(lblLocation);
    		this.add(lblCost);
    		this.setBackground(Color.BLACK);
    		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY));
    	}
    	
    	private void setCost(int cost) {
    		if (lblCost == null) {
    			lblCost = new JLabel();
    			lblCost.setHorizontalTextPosition(SwingConstants.CENTER);
    			lblCost.setHorizontalAlignment(SwingConstants.CENTER);
    			lblCost.setForeground(Color.WHITE);
    		}
    		lblCost.setText("$" + new DecimalFormat("#0.00").format(cost/100.0f));
    	}
    	
    	private void setLocation(String loc) {
    		if (lblLocation == null) {
    			lblLocation = new JLabel();
    			lblLocation.setHorizontalTextPosition(SwingConstants.CENTER);
    			lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
    			lblLocation.setForeground(Color.WHITE);
    		}
    		lblLocation.setText(loc);
    	}
    	
    }
    
    @Override
	public String toString() {
    	return "SlotView for location " + model.getLocation();
    }

    public void updateAll() {
    	((Indicator)indicator).setCost(model.getCost());
    	((Indicator)indicator).setLocation(String.valueOf(model.getLocation()));
    	Product p = null;
    	try {
			p = model.getProductAt(0);
		} catch (EmptySlotException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
    	this.image = null;
    	boolean empty = false;
    	try {
			this.image = ImageUtil.LoadSquareImage(String.valueOf(p.getUPC()), 72);
		} catch (IOException e) {
			System.err.println("Couldn't load image: " + e.getMessage());
		} catch (NullPointerException ignore) {
			// No product at that location
			empty = true;
		}
    	if (empty) {
    		try {
				this.image = ImageUtil.LoadSquareImage("EmptySlot", 72);
			} catch (IOException e) {
				System.err.println("Couldn't load EmptySlot: " + e.getMessage());
			}
    	}
    	SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				repaint();
			}
    		
    	});
    	
    }

	@Override
	public void actionPerformed(ActionEvent evt) {
		String action = evt.getActionCommand();
		if ("removeProduct".equals(action) || "addProduct".equals(action)) {
			// Animate the old image
			if ("removeProduct".equals(action) && !(owner instanceof RestockerView)) {
				glassPane.animateDrop(ImageUtil.deepCopy(image), getX(), getY());
			}
			
			updateAll();
			
			
		} else if ("setCost".equals(action)) {
			((Indicator)indicator).setCost(model.getCost());
		}
	}

}

package view.gui.customer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import util.ImageUtil;


@SuppressWarnings("serial")
public class FuturisticTouchPanel extends JFrame {

	public FuturisticTouchPanel(String title) {
		super(title);
		
		initComponents();
	}
	
	
	private void initComponents() {
		ImagePanel p = new ImagePanel(new GridLayout(4,3,10,10));
		p.setBorder(new EmptyBorder(10,10,10,10));
		
		for (int i = 0; i < 12; i++) {
			p.add(new GlassButton(i+""));
		}

		this.add(p);

	}
	
	public class GlassButton extends JButton {

		private Shape shape;
		private static final int ARC_WIDTH = 40;
		
		public GlassButton(String text) {
			super(text);
			this.setOpaque(false);
			// Set the default background color to transparent white
			this.setBackground(new Color(30,144,255,50));
			this.setFont(this.getFont().deriveFont(25.0f));
		}

		public GlassButton() {
			this("");
		}


		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g.create();
			
			// Clear the old image (we need to do this because we are using transparency)
			g2d.setColor(new Color(0,0,0,0));
			g2d.drawRect(0, 0, getWidth(), getHeight());
			
			// Always use antialiasing because otherwise it's UGLY
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);

	        if (getModel().isArmed()) {
	        	g2d.setColor(new Color(30,144,255,100));
	        } else {
	        	g2d.setColor(getBackground());
	        }

			// Draw rectangle with rounded corners on top of button
			g2d.fillRoundRect(0,0,getWidth(),getHeight(),ARC_WIDTH,ARC_WIDTH);
			
			/** Add the label */
			// Finding size of text so can position in center.
			FontRenderContext frc = 
					new FontRenderContext(null, false, false);
			Rectangle2D r = getFont().getStringBounds(getText(), frc);

			float xMargin = (float)(getWidth()-r.getWidth())/2;
			float yMargin = (float)(getHeight()-getFont().getSize())/2;

			// Draw the text in the center
			g2d.setColor(Color.WHITE);
			g2d.drawString(getText(), xMargin, 
					getFont().getSize() + yMargin);
			g2d.dispose();
		}
		
		
	    // Paint the border of the button using a basic stroke.
	    @Override
	    protected void paintBorder(Graphics g) {
	    	Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        g2d.setStroke(new BasicStroke(3f));
	        if (getModel().isArmed()) {
	        	g2d.setColor(new Color(30,144,255,200));
	        } else {
	        	g2d.setColor(new Color(30,144,255,100));
	        }
			g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1,ARC_WIDTH,ARC_WIDTH);
	    }
		
	    @Override
	    public boolean contains(int x, int y) {
	        // If the button has changed size, make a new shape object.
	        if (shape == null || !shape.getBounds().equals(getBounds())) {
	        	shape = new RoundRectangle2D.Float(
	        			0,0,getWidth(),getHeight(),ARC_WIDTH,ARC_WIDTH);
	        }
	        return shape.contains(x, y);
	    }
	    
	}
	
    class ImagePanel extends JPanel {
    	
    	private BufferedImage bkgd;
    	
    	public ImagePanel(LayoutManager lm) {
    		super(lm);
    		this.setBackground(Color.BLACK);
    		try {
				bkgd = (BufferedImage) ImageUtil.LoadSquareImage("AIBackground", 400);
			} catch (IOException ignore) { }
    	}
    	
    	@Override
    	public void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(bkgd, 0, 0, getWidth(), getHeight(), null);
    	}
    }
    
    
	private static void createAndShowGUI() {
		JFrame frame = new FuturisticTouchPanel("GlassBtnTest");
		frame.setSize(400,400);
		frame.setVisible(true);
	}
	
	public static void main(String [] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
}

package view.gui.customer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.LinearGradientPaint;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class RoundButton extends JButton {

	private Shape shape;
	private static final int ARC_WIDTH = 40;
	
	public RoundButton(String text) {
		super(text);
		// Set the default background color
		this.setBackground(new Color(200, 200, 200));
		//this.setBackground(Color.BLACK);
		this.setFont(this.getFont().deriveFont(25.0f));
	}

	public RoundButton() {
		this("");
	}


	@Override
	public void paintComponent(Graphics g) {

		// Set background same as parent.
		//setBackground(getParent().getBackground());
		// setBorder(Styles.BORDER_NONE);

		// I don't need this -- calls to above methods will 
		// invoke repaint as needed.
		//        super.paint(g);

		// Take advantage of Graphics2D to position string
		Graphics2D g2d = (Graphics2D)g;
		
		// Always use antialiasing because otherwise it's UGLY
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

		// Make it grey #DDDDDD, and make it round with 
		// 1px black border.
		// Use an HTML color guide to find a desired color.
		// Last color is alpha, with max 0xFF to make 
		// completely opaque.
        // JIMI - You don't need the fourth parameter if you aren't using transparency
		g2d.setColor(getBackground());

		// Draw rectangle with rounded corners on top of button
		g2d.fillRoundRect(0,0,getWidth(),getHeight(),ARC_WIDTH,ARC_WIDTH);

		/** Draw the upper portion of the 3D button */
		// Compute the position (it will be 76% of the size of the total button)
		int x;
		int y;
		int width;
		int height;
		if (getModel().isArmed()) {
			// Make it a little smaller to simulate pressing down
			x = (int)(getWidth()*0.14);
			y = (int)(getHeight()*0.14);
			width = (int)(getWidth()*0.71);
			height = (int)(getHeight()*0.71);
		} else {
			x = (int)(getWidth()*0.12);
			y = (int)(getHeight()*0.12);
			width = (int)(getWidth()*0.76);
			height = (int)(getHeight()*0.76);	
		}

		
		// Draw the fill for the upper portion of the button
		g2d.setColor(new Color(225, 225, 225));
		g2d.fillRoundRect(x, y, width, height, ARC_WIDTH, ARC_WIDTH);
		
		// Apply the shine (a vertical bilinear gradient)
		g2d = applyGloss(g2d, x, y, width, height);
		
		// Draw the outline (this is a very light shading brighter just to give
		// it that extra distinction between the side and top of the button)
		g2d.setStroke(new BasicStroke(1f));
		g2d.setColor(new Color(235, 235, 235));
		g2d.drawRoundRect(x, y, width, height,ARC_WIDTH,ARC_WIDTH);
		
		/** Add the label */
		// Finding size of text so can position in center.
		FontRenderContext frc = 
				new FontRenderContext(null, false, false);
		Rectangle2D r = getFont().getStringBounds(getText(), frc);

		float xMargin = (float)(getWidth()-r.getWidth())/2;
		float yMargin = (float)(getHeight()-getFont().getSize())/2;

		if (getModel().isArmed()) {
			// Move this down so it looks like the button was pushed
			yMargin -= 1;
		}
		
		// Draw the text in the center
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString(getText(), xMargin, 
				getFont().getSize() + yMargin);
	}
	
	
    // Paint the border of the button using a basic stroke.
    @Override
    protected void paintBorder(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isArmed()) {
        	g2d.setStroke(new BasicStroke(1f));
        	g2d.setColor(new Color(255,255,255,100));        	
        } else {
        	g2d.setStroke(new BasicStroke(1f));
        	g2d.setColor(Color.LIGHT_GRAY.darker());
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
	
    private Graphics2D applyGloss(Graphics2D g, int x, int y, int width, int height) {
    	// Apply a clipping region (so we only shimmer up the top of the button)
    	g.setClip(new RoundRectangle2D.Float(x, y, width, height, ARC_WIDTH, ARC_WIDTH)); 
    	
    	// Start in middle on left and end in middle on right
        Point2D start = new Point2D.Float(x, height/2);
        Point2D stop = new Point2D.Float(x+width, height/2);
        
        // Color (transparent to white to transparent)setting the middle to most white
        // (this is the Bilinear gradient)
        float[] dist = {0f, .5f, 1f};
        Color[] colors = {new Color(255,255,255,0), Color.WHITE, new Color(255,255,255,0)};
        
        // Create and apply the gradient
        LinearGradientPaint paint = 
                new LinearGradientPaint(start, stop, dist, colors);
        g.setPaint(paint);
        // We know there is a border around this so paint inside that border 
        // (hence the starting at 1 and ending at -3)
        g.fillRoundRect(1, 1, getWidth()-3, getHeight()-3, ARC_WIDTH, ARC_WIDTH);
        g.setClip(null); // Remove the clipping region
        return g;
    }
    
	
	public static void main(String [] args) {
		JFrame frame = new JFrame("test");
		RoundButton butt = new RoundButton("1");
		butt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Round button clicked");
			}
			
		});

		frame.add(butt);
		//frame.pack();
		frame.setSize(400,400);
		frame.setVisible(true);
	}
}
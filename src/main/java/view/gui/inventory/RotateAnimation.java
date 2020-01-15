package view.gui.inventory;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

/*
 * A class used to control the animation of a series of images
 */
public class RotateAnimation {
	
	private long totalTime;			// The amount of time it has been running
    private long duration;          // Length of time to show the frame
    private Image img;
    private Point position;
    private final static float GRAVITY = 0.3f;

    /**
     * One args Constructor.  Creates an animation that does 1 rotation/duration
     * @param frames        The list of images/frames to animate
     * @param totalDuration The length of time of this animation
     */
    public RotateAnimation(Image img, int x, int y, long duration) {
    	this.img = img;
        this.duration = duration;
        this.position = new Point(x, y);
    }
    
    /**
     * Get the current translation of the image to display.
     * @return the image to display
     */
    public synchronized BufferedImage getImage() {
    	BufferedImage retVal = new BufferedImage(img.getWidth(null), 
    			img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2d = retVal.createGraphics();
    	
    	// Now compute the new angle
    	float factor = (float) (totalTime % duration)/duration;
    	float angle = 360*factor;
    	//System.out.println("Animation Angle is " + angle + " after " + (totalTime % duration) + "*" + factor);
    	// Compute a translation
        AffineTransform rotation = new AffineTransform();
        rotation.rotate(Math.toRadians(angle), img.getWidth(null)/2, 
        		img.getHeight(null)/2);

        // Draw the original image onto the new bi
        g2d.drawRenderedImage((RenderedImage) img, rotation);
        
        // Clean up
        g2d.dispose();
        
    	return retVal;
    }
    
    public void start() {
    	totalTime = 0;
    }
    
    public int getX() {
    	return position.x;
    }

    public int getY() {
    	return position.y;
    }
    
    public Point getPoint() {
    	return position;
    }

	public void update(long elapsedTime) {
		totalTime += elapsedTime;
		// Update Y (they only fall down, no x change)
		this.position.y += elapsedTime*GRAVITY;
	}
	
	public boolean isStarted() {
		return totalTime != 0;
	}

}
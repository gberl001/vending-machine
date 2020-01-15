package view.gui.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import util.ImageUtil;


@SuppressWarnings("serial")
public class AnimationPane extends JComponent implements MouseListener {

	private List<RotateAnimation> animations = new ArrayList<RotateAnimation>();
	private List<Image> images = new ArrayList<Image>();
	private long currTime;
	private Rectangle clippingRegion;
	private boolean viewingImages = false;
	private String title = "";
	private int numSlots;
	private Thread animationThread = new AnimationThread();
	private Queue<RotateAnimation> requestQueue = new LinkedBlockingQueue<RotateAnimation>();
	
    public AnimationPane() {
    	this.currTime = System.currentTimeMillis();
    }
    
    public void animateDrop(Image img, int x, int y) {
    	// Create an animation and add it to the list
    	RotateAnimation anim = new RotateAnimation(img, x, y, 3000);
    	
    	// Add the animation to the request stack
    	requestQueue.add(anim);
    	
    	// If the animation thread isn't running, start a new one
    	if (!animationThread.isAlive()) {
    		animationThread = new AnimationThread();
    		animationThread.start();
    	}
 
    	//animations.add(anim);
    	//anim.start();
    	//this.animate();
    }
    
    /**
     * Draw cascading images starting at x and y
     * @param x	the x location of the cascade drawing
     * @param y the y location of the cascade drawing
     * @param images	the images to cascade
     */
    public synchronized void paintImages(List<Image> images, String title, 
    		int numSlots) {
    	this.images = images;
    	this.title = title;
    	this.numSlots = numSlots;
    	viewingImages = true;
    	repaint();
    }
    
    public void clearImages() {
    	this.images = new ArrayList<Image>();
    	viewingImages = false;
    	repaint();
    }
    
    public void clearAnimations() {
    	this.animations = new ArrayList<RotateAnimation>();
    	repaint();
    }
    
    public void clearAll() {
    	clearImages();
    	clearAnimations();
    }
    
    class AnimationThread extends Thread {
    	
    	
    	@Override
		public void run() {
			// Check for an animation on the Queue
			if (!requestQueue.isEmpty()) {
				RotateAnimation a = requestQueue.remove();
				animations.add(a);
				a.start();
			}
    		
			long duration = 0;
			long currentTime = System.currentTimeMillis();
	    	while(animations.size() > 0) {
	    		
	            long timeElapsed =
	                    System.currentTimeMillis() - currentTime;
	            currentTime += timeElapsed;
	    		duration += timeElapsed;
	    		if (duration >= 1000) {
	    			// After 1 second, check for an animation on the Queue
	    			if (!requestQueue.isEmpty()) {
	    				System.out.println("added an animation");
	    				RotateAnimation a = requestQueue.remove();
	    				animations.add(a);
	    				a.start();
	    			}
	    			duration = 0;
	    		}
	            
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						repaint();
					}
				});
				
				// Clean up the list
				for (int i = 0; i < animations.size(); i++) {
		        	// If this image is off of the screen, remove it from the list
		        	// Currently only looks at Y value lower bounds
		        	if (animations.get(i).getY() > clippingRegion.y + clippingRegion.height) {
		        		animations.remove(i);
		        	}
				}
		    	try {
					Thread.sleep(10);
				} catch (InterruptedException e) { }
	    	}
    	}
    }
    /*
    public synchronized void animate() {
    	new Thread(new Runnable() {

			@Override
			public void run() {
		    	while(animations.size() > 0) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							repaint();
						}
					});
					// Clean up the list
					for (int i = 0; i < animations.size(); i++) {
			        	// If this image is off of the screen, remove it from the list
			        	// Currently only looks at Y value lower bounds
			        	if (animations.get(i).getY() > clippingRegion.y + clippingRegion.height) {
			        		animations.remove(i);
			        	}
					}
			    	try {
						Thread.sleep(10);
					} catch (InterruptedException e) { }
		    	}
			}
    		
    	}).start();

    }*/
	
    @Override
    protected void paintComponent(Graphics g) {
        // enables anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setClip(clippingRegion);
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //        RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        // Properly compute time elapsed
        long timeElapsed =
                System.currentTimeMillis() - currTime;
        currTime += timeElapsed;
        // Draw any animations we have
        for (int i = 0; i < animations.size(); i++) {
        	RotateAnimation anim = animations.get(i);
        	if (!anim.isStarted()) {
        		anim.start();
        		anim.update(1);
        	} else {
        		anim.update(timeElapsed);
        		g2.drawImage(anim.getImage(), anim.getX(), anim.getY(), null);
        	}
        }
        
        // If they are viewing images, translucification of the background occurs
        if (clippingRegion != null && viewingImages) {
	        g2.setColor(new Color(255,255,255,150));
	        g2.fillRect(clippingRegion.x, clippingRegion.y, clippingRegion.width, clippingRegion.height);
        
	        g2.setFont(new Font("default", Font.BOLD, 20));
	        g2.setColor(Color.WHITE);
	        int offset = 30;
	        int i;
	        int lastX = this.getWidth()/2-36;
	        int lastY = clippingRegion.y + 40;
	        
	        // Print the blanks
	        BufferedImage blank = null;
	        try {
				blank = (BufferedImage) ImageUtil.LoadSquareImage("EmptySlot", 72);
			} catch (IOException e) {
				System.err.println("couldn't load blank");
			}
	        for (i = numSlots; i > images.size(); i--) {
	        	g2.drawImage(blank, lastX, lastY, null);
	        	g2.drawString("" + (i-1), lastX-5+36, lastY+30);
	        	lastY += offset; // decrement the next image placement
	        }
	        
	        // Print the images
	        while (i > 0) {
	        	System.out.println("Getting image");
	        	Image img = images.get(--i);
	        	g2.drawImage(img, lastX, lastY, null);
	        	g2.drawString("" + i, lastX-5+36, lastY+30);
	        	lastY += offset; // increment the next image placement
	        }
	        
	     // Print the location
	        g2.setColor(Color.DARK_GRAY);
	        g2.drawString(title, clippingRegion.x+10, clippingRegion.y+40);

        }
        // Clean up
        g2.dispose();
    }


	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }

	public void setClippingRegion(Rectangle region) {
		this.clippingRegion = region;
	}

}

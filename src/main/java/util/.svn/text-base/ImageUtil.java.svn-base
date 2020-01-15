package util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static final int DEFAULT_SIZE = 200;
	private static final String IMG_DIR = "database/images";
	private static final String IMAGE_PATH = IMG_DIR + "/";
	
	
	/**
	 * Fancy way of making a square image whilst keeping the aspect ratio.
	 * @param img	the image to be made square
	 * @param dim	the desired size of the image
	 * @return	a square image representation of the passed image using transparency
	 * @throws IOException 
	 */
	public static Image LoadSquareImage(String name, int dim) throws IOException {
		ensureDirectory();
		Image img = null;
		if (name.split("\\\\").length > 1) {
			// Read from disk
			img = ImageIO.read(new File(name));
		} else {
			// Read from database
			img = ImageIO.read(new File(IMAGE_PATH + name + ".png"));
		}
		
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		
		// Find the largest factor
		int maxFactor = height > width ? height : width;
		
		// Create a blank image of the desired square size
		BufferedImage newImg = new BufferedImage(maxFactor, maxFactor, 
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D newG = newImg.createGraphics();
		
		// Compute the translation
        double translateX = (maxFactor - width)/2;
        double translateY = (maxFactor - height)/2;
        
        // Translate the image
        AffineTransform imgTransform = 
        		AffineTransform.getTranslateInstance(
        				translateX, translateY);
        // Draw on the new image's graphics with the translation
        newG.drawRenderedImage((RenderedImage) img, imgTransform);
        
        // Clean up
        newG.dispose();

		return ImageUtil.getFasterScaledInstance(
				newImg, 
				dim, 
				dim, 
				RenderingHints.VALUE_INTERPOLATION_BILINEAR, 
				true);
	}
	
	/**
	 * Loads an image in it's regular form
	 * @param img	the image to be loaded
	 * @return	the image with it's original attributes
	 * @throws IOException 
	 */
	public static Image LoadImage(String name) throws IOException {
		ensureDirectory();
		Image img = null;
		if (name.split("\\\\").length > 1) {
			// Read from disk
			img = ImageIO.read(new File(name));
		} else {
			// Read from database
			img = ImageIO.read(new File(IMAGE_PATH + name + ".png"));
		}
		return img;
	}
	
	/**
	 * Fancy way of making a square image whilst keeping the aspect ratio.
	 * @param img	the image to be made square
	 * @param dim	the desired size of the image
	 * @return	a square image representation of the passed image using transparency
	 * @throws IOException 
	 */
	public static void StoreImage(String source, String name) throws IOException {
		ensureDirectory();
		Image img = ImageIO.read(new File(source));
		
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		
		// Find the largest factor
		int maxFactor = height > width ? height : width;
		
		// Create a blank image of the desired square size
		BufferedImage newImg = new BufferedImage(maxFactor, maxFactor, 
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D newG = newImg.createGraphics();
		
		// Compute the translation
        double translateX = (maxFactor - width)/2;
        double translateY = (maxFactor - height)/2;
        
        // Translate the image
        AffineTransform imgTransform = 
        		AffineTransform.getTranslateInstance(
        				translateX, translateY);
        // Draw on the new image's graphics with the translation
        newG.drawRenderedImage((RenderedImage) img, imgTransform);
        
        // Clean up
        newG.dispose();

        BufferedImage outImage = ImageUtil.getFasterScaledInstance(
				newImg, 
				DEFAULT_SIZE, 
				DEFAULT_SIZE, 
				RenderingHints.VALUE_INTERPOLATION_BILINEAR, 
				true);
        // Remove the old file if it exists
        File f = new File(IMAGE_PATH + name + ".png");
        if (f.exists()) {
        	f.delete();
        }
        ImageIO.write(outImage, "png", f); 
	}
	
    /**
     * Convenience method that returns a scaled instance of the
     * provided BufferedImage.
     * 
     * 
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    RenderingHints.KEY_INTERPOLATION (e.g.
     *    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,
     *    RenderingHints.VALUE_INTERPOLATION_BILINEAR,
     *    RenderingHints.VALUE_INTERPOLATION_BICUBIC)
     * @param progressiveBilinear if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in down-scaling cases, where
     *    targetWidth or targetHeight is
     *    smaller than the original dimensions)
     * @return a scaled version of the original BufferedImage
     */
    private static BufferedImage getFasterScaledInstance(BufferedImage img,
            int targetWidth, int targetHeight, Object hint,
            boolean progressiveBilinear)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;
        int w, h;
        int prevW = ret.getWidth();
        int prevH = ret.getHeight();
        boolean isTranslucent = img.getTransparency() !=  Transparency.OPAQUE; 
        // Find out if we are up or down scaling
        double scaleFactor = (targetHeight*targetWidth)/(img.getHeight()*img.getWidth());

        if (progressiveBilinear) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }
        
        do {
            if (progressiveBilinear && w > targetWidth && scaleFactor < 1) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            } else if (progressiveBilinear && w < targetWidth && scaleFactor > 1) {
                w *= 2;
                if (w > targetWidth) {
                    w = targetWidth;
                }
            }

            if (progressiveBilinear && h > targetHeight && scaleFactor < 1) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            } else if (progressiveBilinear && h < targetHeight && scaleFactor > 1) {
                h *= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            if (scratchImage == null || isTranslucent) {
                // Use a single scratch buffer for all iterations
                // and then copy to the final, correctly-sized image
                // before returning
                scratchImage = new BufferedImage(w, h, type);
                g2 = scratchImage.createGraphics();
            }
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);
            prevW = w;
            prevH = h;

            ret = scratchImage;
        } while (w != targetWidth || h != targetHeight);
        
        if (g2 != null) {
            g2.dispose();
        }

        // If we used a scratch buffer that is larger than our target size,
        // create an image of the right size and copy the results into it
        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }
        
        return ret;
    }
    
	public static BufferedImage deepCopy(Image img) {
		 ColorModel cm = ((BufferedImage) img).getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = ((BufferedImage) img).copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	private static void ensureDirectory() {
		File dir = new File(IMG_DIR);
		if (!dir.exists()) {
			// Create the directory
			dir.mkdir();
			// Add Default images
			try {
				ImageIO.write(
						ImageIO.read(ImageUtil.class.getClass().getResource(
								"/resources/EmptySlot.png"))
						, "png"
						, new File(IMG_DIR + "/EmptySlot.png"));
			} catch (IOException ignore) { }
		}
	}
	
}

package view.gui.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

	public AboutDialog(JFrame parent) {
		super(parent, true);
		this.setLayout(new BorderLayout());
		this.setTitle("About");
		this.setResizable(false);
		
		JPanel centerPanel = new JPanel(new GridLayout(2,1));
		
		JPanel labelPanel = new JPanel(new BorderLayout());
		JEditorPane ep = new JEditorPane();
		ep.setContentType("text/html");
		ep.setEditable(false);
		labelPanel.add(ep, BorderLayout.CENTER);
		ep.setText("<p>All Rights Reserved. This application is published by "
				+ "<b>Team&nbsp;E&nbsp;-&nbsp;Subject&nbsp;To&nbsp;Change</b>. "
				+ "Terms and conditions may apply.</p>"
				+ "<p>Team Members:"
				+ "<ul type=\"square\"><li>Geoff Berl</li><li>Ryan Castner</li>"
				+ "<li>Jimi Ford</li><li>Dave Schoeffler</li></ul></p>");
				
		labelPanel.setBorder(new EmptyBorder(10,5,10,5));
		centerPanel.add(new ImagePanel());
		centerPanel.add(labelPanel);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(new JLabel("  Version: beta"), BorderLayout.SOUTH);
		
		this.setSize(480, 420);
		int x = parent.getX() + parent.getWidth()/2 - this.getWidth()/2;
		int y = parent.getY() + parent.getHeight()/2 - this.getHeight()/2;
		
		this.setLocation(x, y);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}
	
    class ImagePanel extends JPanel {
    	
    	private BufferedImage bkgd;
    	
    	public ImagePanel() {
    		super();
    		this.setBackground(Color.BLACK);
    		try {
				bkgd = (BufferedImage) ImageIO.read(getClass().getResource("/resources/logo.png"));
			} catch (IOException ignore) { }
    		if (bkgd != null) {
    			this.setMinimumSize(new Dimension(bkgd.getWidth(), bkgd.getHeight()));
    		}
    	}
    	
    	@Override
    	public void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(bkgd, 0, 0, getWidth(), getHeight(), null);
    	}
    }
	
}

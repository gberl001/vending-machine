package view.gui.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import util.ImageUtil;

import control.Controller;
import exceptions.EmptySlotException;
import exceptions.SlotOverflowException;

import model.VendingMachine;

import model.Product;
import model.Slot;


@SuppressWarnings("serial")
public class VendingMachineView extends JDialog implements MouseListener, 
		KeyListener, ActionListener {
	
	private InventoryView inventoryPanel;
	private Map<Integer, SlotView> slotViews;
	private VendingMachine model;
	private JPanel mainPanel;
	private int viewingLoc = 0;
	@SuppressWarnings("unused")
	private final Controller controller;
	private JFrame owner;
	
	// KeyListener stuff
	private int[] cheat = 
			new int[] {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};
	private int cursor = 0;
	
	public VendingMachineView(JFrame owner, VendingMachine model, Controller controller) {
		super(owner, false);
		this.owner = owner;
		this.controller = controller;
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(10,10,0,10));
		//mainPanel.add(inventoryPanel = new InventoryView(), BorderLayout.CENTER);
		mainPanel.add(createPushPanel(), BorderLayout.SOUTH);
		mainPanel.setBackground(Color.RED.darker());
		
		
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.setGlassPane(new AnimationPane());
		this.getGlassPane().setVisible(true);
		
		setModel(model);
		
		this.addKeyListener(this);

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setSize(425, 750);
        this.setResizable(true);
        this.setVisible(true);
		
	}
	
	private JPanel createPushPanel() {
		JPanel retVal = new JPanel(new BorderLayout());
		
		retVal.setBorder(new EmptyBorder(25,50,25,50));
		retVal.setOpaque(false);
		
		// Create the PUSH Door using a JLabel
		JLabel lblPush = new JLabel("P  U  S  H", SwingConstants.CENTER);
		lblPush.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, 
				Color.LIGHT_GRAY, Color.DARK_GRAY));
		lblPush.setOpaque(true);
		lblPush.setBackground(Color.DARK_GRAY);
		lblPush.setPreferredSize(new Dimension(200, 50));
		lblPush.setFont(lblPush.getFont().deriveFont(40.0f));
		
		retVal.add(lblPush, BorderLayout.CENTER);
		
		return retVal;
	}
	
	public void setModel(VendingMachine model) {
		//if (this.model != null) {
		//	this.model.removeChangeListener(this);
		//}
		this.model = model;
		if (this.model == null) {
			return;
		}
		updateView();
		//this.model.addChangeListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Adjust the clipping region for the glass pane
		Dimension d = inventoryPanel.getSize();
		Point p = inventoryPanel.getLocation();
		((AnimationPane)this.getGlassPane()).setClippingRegion(
				new Rectangle(p.x, p.y, d.width, d.height));
	}
	
	protected void updateView() {
		System.out.println("Called updateView()");
		slotViews = new HashMap<Integer, SlotView>();
		inventoryPanel = new InventoryView(model.getNumRows(), model.getNumCols());
		List<Integer> locations = model.listLocations();
		Collections.sort(locations);
		for (int location : locations) {
			SlotView sv = new SlotView(model.getInventory().getSlotAt(location), 
					(AnimationPane) this.getGlassPane(), owner);
			model.getInventory().getSlotAt(location).addActionListener(this);
			// Finally add this to the InventoryView
			inventoryPanel.addSlotView(sv);
			slotViews.put(location, sv);
			sv.updateAll();
		}
		mainPanel.add(inventoryPanel, BorderLayout.CENTER);
		System.out.println("Parent is " + this.getParent());
		inventoryPanel.addMouseListener(this);
	}
	
    private static void createAndShowGUI() {
		Controller controller = new Controller();
		VendingMachine vm = controller.findVendingMachine("0000000001");
		
		/*
    	Product p1 = controller.findProduct(1234567890L);
		Product p2 = controller.findProduct(2345678901L);
		Product p3 = controller.findProduct(3456789012L);
		Product p4 = controller.findProduct(9987654321L);
		Product p5 = controller.findProduct(9876543210L);
		Product p6 = controller.findProduct(8765432109L);
		Product p7 = controller.findProduct(7654321098L);
		Product p8 = controller.findProduct(6543210987L);
		
		try {
			vm.addProductAt(p1, 10);
			vm.addProductAt(p1, 10);
			vm.addProductAt(p1, 10);
			vm.setCostAt(10, 100);
			vm.addProductAt(p2, 11);
			vm.setCostAt(11, 100);
			vm.addProductAt(p3, 12);
			vm.addProductAt(p3, 12);
			vm.addProductAt(p2, 12);
			vm.addProductAt(p2, 12);
			vm.addProductAt(p2, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.addProductAt(p1, 12);
			vm.setCostAt(12, 100);
			vm.addProductAt(p4, 13);
			vm.setCostAt(13, 75);
			vm.addProductAt(p5, 14);
			vm.setCostAt(14, 75);
			vm.addProductAt(p6, 20);
			vm.setCostAt(20, 75);
			vm.addProductAt(p7, 21);
			vm.setCostAt(21, 150);
			vm.addProductAt(p8, 22);
		
		} catch (SlotOverflowException e) {
			System.err.println("Slot overflow " + e.getMessage());
		}*/
		
		new VendingMachineView(null, vm, new Controller());
    	
		
		//frame.testAdd();
    }
    
    public void testAdd() {
    	final VendingMachine vm = this.model; 
		// Add a three new products four seconds later
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException ignore) { }
				final Product p = new Product(
						"Mountain Dew Code Red", 
						Product.TYPE.SODA, 
						9987654321L);
				try {
					vm.addProductAt(p, 31);
					vm.addProductAt(p, 31);
					vm.addProductAt(p, 31);
				} catch (SlotOverflowException e) {
					System.err.println("Slot overflow " + e.getMessage());
				}
			}
			
		}).start();
    }
    
    public void purgeInventory() {
    	final VendingMachine vm = this.model; 
		// Remove all products six seconds later in one second intervals
		new Thread(new Runnable() {

			@Override
			public void run() {
				vm.setSaveMe(false);
				int c = 0;
				for (int i = 1; i <= vm.getNumRows(); i++) {
					for (int j = 0; j < vm.getNumCols(); j++) {
						// Loop through numProduct times and dispense
						int loc = Integer.parseInt(i + "" + j);
						int numProduct = vm.getNumProductsAt(loc);
						for (int k = 0; k < numProduct; k++) {
							System.out.println("Called removeProduct");
							vm.removeProductAt(loc);
							long sleep;
							if (c++ == 0) {
								sleep = 1000;
							} else {
								sleep = 1000;
							}
							try {
								Thread.sleep(sleep);
							} catch (InterruptedException ignore) { }
						}
					}
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) { }
				updateView();
			}
			
		}).start();
    }
    
    public static void main(String[] args) {
        // Set the Nimbus look and feel because it's new and cool looking
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Will be set to default LAF
        }
    	
        Runnable doCreateAndShowGUI = new Runnable() {
            @Override
			public void run() {
                createAndShowGUI();
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
		
	}

    public void showSlotProducts(SlotView sv, boolean update) {
		List<Image> prodImages = new ArrayList<Image>();
		int loc = sv.getSlotLocation();
		
		// Check if we are already looking
		if (viewingLoc == loc && update == false) {
			// If we click on the same location a second time, clear the screen
			((AnimationPane)this.getGlassPane()).clearImages();
			viewingLoc = 0;
		} else {
			// Always clear the screen and open the drawer
			((AnimationPane)this.getGlassPane()).clearImages();
			viewingLoc = loc;
			// Gather a list of products from the back
			int numProducts = model.getNumProductsAt(loc);
			// For each product, starting in the back, get an image
			for (int i = 0; i < numProducts; i++) {
				// Get the product
				Product p = null;
				try {
					p = model.getInventory().getSlotAt(loc).getProductAt(i);
				} catch (EmptySlotException e1) {
					System.err.println("Empty Slot");
				}
				if (p == null) {
					continue;
				}
				// Add a scaled version of the image to the glasspane list
				try {			
					prodImages.add(ImageUtil.LoadSquareImage(
							String.valueOf(p.getUPC()), 72));
				} catch (IOException e) {
					System.err.println("Error loading image " + e.getMessage());
				}
			}
			System.out.println("Calling glasspane method");
			((AnimationPane)this.getGlassPane()).paintImages(prodImages, 
					"Location: " + loc, model.getCapacityAt(loc));
		}
    }
    
	@Override
	public void actionPerformed(ActionEvent evt) {
		Object newObj = evt.getSource();
		if (newObj instanceof Slot) {
			String action = evt.getActionCommand();
			Slot slot = (Slot) newObj;
			if ("addProduct".equals(action) || "removeProduct".equals(action)) {
				SlotView sv = slotViews.get(slot.getLocation());
				
				// If Restocker is viewing, update his view
				if (viewingLoc == sv.getSlotLocation()) {
					showSlotProducts(sv, true);
				}
			}
		}  else if (newObj instanceof VendingMachine) {
			// Update the Title
			this.setTitle("Vending ID: " + ((VendingMachine)newObj).getID());
		}
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		Object src = inventoryPanel.getComponentAt(evt.getPoint());
		if (src instanceof SlotView) {
			SlotView sv = (SlotView) src;
			
			// Check if we are already looking
			if (viewingLoc != 0) {			
				// If we were already looking, clear the screen
				((AnimationPane)this.getGlassPane()).clearImages();
				viewingLoc = 0;
			} else {
				// Show the products
				showSlotProducts(sv, false);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent evt) { }
	@Override
	public void mouseExited(MouseEvent evt) { }
	@Override
	public void mousePressed(MouseEvent evt) { }
	@Override
	public void mouseReleased(MouseEvent evt) { }
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == cheat[cursor]) {
			cursor++;
		} else {
			cursor = 0;
		}
		if (cursor >= cheat.length) {
			purgeInventory();
			cursor = 0;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }

}

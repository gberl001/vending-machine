package view.gui.manager;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import control.Controller;


@SuppressWarnings("serial")
public class ManagerView extends JFrame implements ActionListener {

	private Controller controller;
	private JDialog helpDialog;
	
	
	public ManagerView(Controller controller, String title) {
		super(title);
		
		this.controller = controller;
		
		initComponents();
	}
	
	private void initComponents() {
		this.setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();		
		
		// Replace this with the custom analytics Panel
		JPanel analyticsPanel = new AnalyticsPanel(controller);
		tabs.add(analyticsPanel, "Analytics");
		
		// Add the Restock Instructions Panel
		JPanel restockPanel = new RestockPanel(this, controller);
		tabs.add(restockPanel, "Restock Instructions");
		
		// Add the Manage Machines tab
		JPanel machinesPanel = new MachinesPanel(controller);
		tabs.add(machinesPanel, "Manage Machines");
		
		// Replace this with the custom products management Panel
		JPanel productsPanel = new ProductPanel(controller);
		tabs.add(productsPanel, "Manage Products");
		
		// Add the tabbed pane to the JFrame
		this.getContentPane().add(tabs, BorderLayout.CENTER);
		
		// La Menu bar Shtufffff
		JMenuBar jmb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('f');
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('h');
		JMenuItem jmiExit = new JMenuItem("Exit");
		jmiExit.setMnemonic('x');
		jmiExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JMenuItem jmiHelp = new JMenuItem("Help");
		jmiHelp.setMnemonic('h');
		jmiHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Desktop.isDesktopSupported()) {
					File f = null;
					try {
						f = new File(getClass().getResource("/resources/README.txt").toURI());
					} catch (URISyntaxException ignore) { }
					if (f == null) {
						return;
					}
					final File file = f;
				    if (file.exists()) {
				    	new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Desktop.getDesktop().edit(file);
								} catch (IOException e) {
									JOptionPane.showMessageDialog(null, 
											"Couldn't load README.txt", "Error", 
											JOptionPane.ERROR_MESSAGE, null);
								}
							}
				    	}).start();
				    } else {
				    	JOptionPane.showMessageDialog(null, 
								"Couldn't load README.txt, File not found", "Error", 
								JOptionPane.ERROR_MESSAGE, null);
				    }
				} else {
					JOptionPane.showMessageDialog(null, 
							"This feature is not supported.\n"
							+ "Please download the latest Java version", "Error", 
							JOptionPane.ERROR_MESSAGE, null);
				}
			}
		});
		JMenuItem jmiAbout = new JMenuItem("About");
		jmiAbout.setMnemonic('a');
		jmiAbout.addActionListener(this);
		fileMenu.add(jmiExit);
		helpMenu.add(jmiHelp);
		helpMenu.addSeparator();
		helpMenu.add(jmiAbout);
		jmb.add(fileMenu);
		jmb.add(helpMenu);
		this.setJMenuBar(jmb);
	}
	
	private static void createAndShowGUI() {
		
		Controller controller = new Controller();
        
        // Crap for creating new user, products and machines  (ONLY USE ONCE)
        /*try {
			controller.addProduct(new model.Product("Nacho Cheese Doritos", TYPE.CHIPS, 1234567890L));
	        controller.addProduct(new model.Product("Cool Ranch Doritos", TYPE.CHIPS, 2345678901L));
	        controller.addProduct(new model.Product("Blazin' Buffalo Doritos", TYPE.CHIPS, 3456789012L));
	        controller.addProduct(new model.Product("Mountain Dew Code Red", TYPE.SODA, 9987654321L));
	        controller.addProduct(new model.Product("Diet Mountain Dew", TYPE.SODA, 9876543210L));
	        controller.addProduct(new model.Product("Diet Pepsi", TYPE.SODA, 8765432109L));
	        controller.addProduct(new model.Product("Reese's Cups", TYPE.CANDY, 7654321098L));
	        controller.addProduct(new model.Product("Garden Salsa Sunchips", TYPE.CHIPS, 6543210987L));
        } catch (OverwriteException e1) {
        	System.err.println("Can't overwrite Products");
		}
        try {
			controller.addVendingMachine(new VendingMachine(new VendingID("0000000001", State.NY, "Rochester", 14623, "1 Lomb Memorial Drive", "GCCIS Bldg"), controller));
	        controller.addVendingMachine(new VendingMachine(new VendingID("0000000002", State.NY, "Rochester", 14623, "1 Lomb Memorial Drive", "GCCIS Bldg"), controller));
	        controller.addVendingMachine(new VendingMachine(new VendingID("0000000003", State.NY, "Rochester", 14623, "1 Lomb Memorial Drive", "GCCIS Bldg"), controller));
	        controller.addVendingMachine(new VendingMachine(new VendingID("0000000004", State.NY, "Rochester", 14623, "1 Lomb Memorial Drive", "GCCIS Bldg"), controller));
	        controller.addVendingMachine(new VendingMachine(new VendingID("0000000005", State.NY, "Rochester", 14623, "1 Lomb Memorial Drive", "GCCIS Bldg"), controller));
	        controller.addVendingMachine(new VendingMachine(new VendingID("0000000006", State.NY, "Rochester", 14623, "1 Lomb Memorial Drive", "GCCIS Bldg"), controller));
	        controller.addVendingMachine(new VendingMachine(new VendingID("0000000007", State.NY, "Rochester", 14623, "1 Lomb Memorial Drive", "GCCIS Bldg"), controller));
	        controller.addVendingMachine(new VendingMachine(new VendingID("0000000008", State.NY, "Rochester", 14623, "1 Lomb Memorial Drive", "GCCIS Bldg"), controller));
		} catch (InvalidFormatException e) {
			System.err.println("Error creating VendingMachines");
		} catch (OverwriteException e) {
			System.err.println("Can't overwrite VendingMachines");
		}*/
		JFrame f = new ManagerView(controller, "Vending Manager");
		
		// Setup JFrame deets.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack(); // Pack before setting location (this determines size)
		
		// Get the current screen's size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// Compute and set the location so the frame is centered
		int x = screen.width/2-f.getSize().width/2;
		int y = screen.height/2-f.getSize().height/2;
		f.setLocation(x, y);
		
		f.setVisible(true);
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
        
		if (!System.getProperty("java.version").startsWith("1.7")) {
			JOptionPane.showMessageDialog(null, "Please download Java version 7", "Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}
    	
        Runnable doCreateAndShowGUI = new Runnable() {
            @Override
			public void run() {
                createAndShowGUI();
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src instanceof JMenuItem) {
			JMenuItem mi = (JMenuItem) src;
			if (mi.getText().equals("About")) {
				if (helpDialog == null) {
					helpDialog = new AboutDialog(this);	
				}
				helpDialog.setVisible(true);
			}
		}
		
	}
}

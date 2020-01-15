package view.gui.manager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.activation.DataHandler;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import model.LineItem;
import model.Product;
import model.RestockingList;
import model.VendingMachine;

import control.Controller;


import view.AutoDismissMessage;
import view.LineItemTableModel;
import view.ProductTableModel;
import view.TitleLabel;
import view.VendingMachineTableModel;
import view.gui.inventory.VendingMachineView;

@SuppressWarnings("serial")
public class RestockPanel extends JPanel implements ActionListener {

	private VendingMachineView vmView;
	private VendingMachine model;
	private Controller controller;
	private RestockDetailsPanel restockPanel;
	private VendingMachinePanel vmPanel;
	private JFrame parent;
	
	public RestockPanel(JFrame parent, Controller controller) {
		super(new BorderLayout());
		this.parent = parent;
		//this.setBorder(new EmptyBorder(0,35,0,0));
		this.controller = controller;
		
		vmPanel = new VendingMachinePanel();  			// Create the panel
		vmPanel.updateAll();								// Force initial update
		
		restockPanel = new RestockDetailsPanel();	// Create the panel
		restockPanel.updateAll();					// Force initial update
		
		this.add(vmPanel, BorderLayout.WEST);			// Add the panel
		this.add(restockPanel, BorderLayout.CENTER);	// Add the panel
		
		controller.addActionListener(this);
	}
	
	private void setModel(VendingMachine model) {
		// Only update the RS List if the old one doesn't equal the new one
		/*if (model != null) {
			if (model.getRestockList() != restockPanel.rsList && restockPanel != null) {
				restockPanel.setRestockList(model.getRestockList());
			}
		} else {
			restockPanel.setRestockList(model.getRestockList());
		}*/
		this.model = model;
		restockPanel.setRestockList(model.getRestockList());
		if (model.isLocked()) {
			AutoDismissMessage.showMessageDialog(null, 
					"This vending machine is currently being" +
					" restocked.\nChanges will not be allowed " +
					"until restocking is complete.", 2000);
		}
		/*
		// Start a timer to notify check for state changes
		final String id = model.getID().getId();
		final boolean currentIsLocked = model.isLocked();
		new Timer(2000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				// If the oldIsLocked doesn't match the new, reload and display message
				if (!controller.findVendingMachine(id).isLocked() == currentIsLocked) {
					((Timer)evt.getSource()).stop();
					setModel(controller.findVendingMachine(id));
					if (currentIsLocked) {	// UNLOCKED
						AutoDismissMessage.showMessageDialog(null, 
								"This vending machine is now unlocked", 1000);
					} else {			// LOCKED
						AutoDismissMessage.showMessageDialog(null, 
								"This vending machine is currently being" +
								" restocked.\nChanges will not be allowed " +
								"until restocking is complete.", 2000);
					}
				}
			}
		}).start();*/
		
		
	}
	
    protected GridBagConstraints setGridBagConstraints(GridBagConstraints c,
    		int x, int y, int fill, double weightx, double weighty, int 
    		width, int height) {
        c.weightx = weightx;
        c.weighty = weighty;
        c.fill = fill;
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        // Reset the insets each time, we use it a few times so this
        //  will undo any times we do it.
        c.insets = new Insets(0, 0, 0, 0);
        return c;
    }
	
	class RestockDetailsPanel extends JPanel implements PropertyChangeListener {

		private RestockingList rsList;
		private JTable tblProduct;
		private JTable tblRestockList;
		private JTextArea taNote;
		private JButton btnConfirm;
		private JButton btnClear;
		private JButton btnReset;
		private GridBagConstraints c;
		
		public RestockDetailsPanel() {
			super(new GridBagLayout());
			
			this.setBorder(BorderFactory.createRaisedBevelBorder());
			initComponents();
			layoutComponents();
			updateProductTable();
		}
		
		private void initComponents() {
			// Product list table
			tblProduct = new JTable();
			tblProduct.setDragEnabled(true);
			tblProduct.setTransferHandler(new TransferHandler() {
				private int[] rows    = null;
				private Object[] transferedObjects = null;
				private final DataFlavor localObjectFlavor = 
						Product.PRODUCT_FLAVOR;
				//private int addIndex  = -1; //Location where items were added
				//private int addCount  = 0;  //Number of items added.
				
				@Override
				protected Transferable createTransferable(JComponent c) {
					//System.out.println("Creating transferable");
					JTable table = (JTable) c;
					ProductTableModel model = 
							(ProductTableModel)table.getModel();
					Product transferableObj = 
							model.getElementAt(table.getSelectedRow());
					ArrayList< Product > list = new ArrayList< Product >();
					for(int i: rows = table.getSelectedRows()) {
						list.add(model.getElementAt(i));
					}
					transferedObjects = list.toArray();
					return new DataHandler(transferableObj,
							localObjectFlavor.getMimeType());
				}
				
				@Override public int getSourceActions(JComponent c) {
					return TransferHandler.COPY_OR_MOVE;
				}
				
				@Override protected void exportDone(JComponent c, 
						Transferable t, int act) {
					cleanup(c, act == MOVE);
				}
				
				private void cleanup(JComponent src, boolean remove) {

				}
				  
			});
		    tblProduct.addMouseListener(new MouseAdapter() {
		    	
		    	@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						JTable target = (JTable)e.getSource();
						ProductTableModel tableModel = 
								(ProductTableModel) target.getModel();
						int row = target.getSelectedRow();
						Product p = tableModel.getElementAt(row);
						if (model.getRestockList() == null) {
							return;
						}
						model.getRestockList().addProduct(p);
					}
				}
		    	
		    	@Override
		    	public void mousePressed(MouseEvent e) {
		    		super.mousePressed(e);
		    		if (e.getButton() != MouseEvent.BUTTON1) {
		    			return;
		    		}
		    		tblProduct.getTransferHandler().exportAsDrag(
		    				tblProduct, e, TransferHandler.COPY);
		    	}
		    });
			
			// Restocking list Line Items table
			tblRestockList = new RestockTable();
			//tblRestockList.setPreferredSize(new Dimension(600, 100));
			tblRestockList.setFillsViewportHeight(true);
			tblRestockList.setDragEnabled(true);
			tblRestockList.setDropMode(DropMode.INSERT_ROWS);
			tblRestockList.setTransferHandler(new TransferHandler() {
		        @Override
				public boolean importData(TransferSupport support) {
		            // return if we can't handle the object
		            if (!canImport(support)) {
		                return false;
		            }

		            // Get the dropped data
		            Product product;
		            try {
		            	product = (Product)
		            			support.getTransferable().getTransferData(
		            					Product.PRODUCT_FLAVOR
		            					);
					} catch (UnsupportedFlavorException e) {
						return false;
					} catch (IOException e) {
						return false;
					}

		            if (rsList == null) {
		            	JOptionPane.showMessageDialog(null, 
		            			"Please select a vending machine" +
		            			"\nDouble click the list", "Error", 
		            			JOptionPane.ERROR_MESSAGE, null);
		            }
		            rsList.addProduct(product);

		            return true;
		        }
		        
		        @Override
				public boolean canImport(TransferSupport support) {
		            // Only support drops
		            if (!support.isDrop()) {
		                return false;
		            }

		            // Only support product objects
		            if (!support.isDataFlavorSupported(Product.PRODUCT_FLAVOR)) {
		            	return false;
		            }
		            return true;
		        }
			});
			tblRestockList.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseReleased(MouseEvent e) {
		        	if (e.getButton() != MouseEvent.BUTTON3) {
		        		return;
		        	}
		            int r = tblRestockList.rowAtPoint(e.getPoint());
		            if (r >= 0 && r < tblRestockList.getRowCount()) {
		            	tblRestockList.setRowSelectionInterval(r, r);
		            } else {
		            	tblRestockList.clearSelection();
		            }

		            int row = tblRestockList.getSelectedRow();
		            if (row < 0) {
		                return;
		            }
	                JPopupMenu popup = createPopup(row);
	                popup.show(e.getComponent(), e.getX(), e.getY());
		        }
			});
			
			// Restocking list notes 
			taNote = new JTextArea();
			taNote.setPreferredSize(new Dimension(600, 100));
			taNote.setEditable(false);
			
			// Confirm Button
			btnConfirm = new JButton("Confirm Changes");
			btnConfirm.setEnabled(false);
			btnConfirm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					rsList.setNote(taNote.getText());
					if (model.isLocked()) {
						JOptionPane.showMessageDialog(null, 
								"The machine is currently being restocked, " +
								"please try again later", "Error", 
								JOptionPane.ERROR_MESSAGE, null);
					} else {
						model.setRestockingList(rsList);
						AutoDismissMessage.showMessageDialog(null, 
								"Changes saved successfully", 1000);
					}
				}
			});
			btnClear = new JButton("New List");
			btnClear.setEnabled(false);
			btnClear.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					if (model.isLocked()) {
						JOptionPane.showMessageDialog(null, 
								"The machine is currently being restocked, " +
								"please try again later", "Error", 
								JOptionPane.ERROR_MESSAGE, null);
					} else {
						setRestockList(new RestockingList());
					}
				}
			});
			btnReset = new JButton("Reset List");
			btnReset.setEnabled(false);
			btnReset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					if (model.isLocked()) {
						JOptionPane.showMessageDialog(null, 
								"The machine is currently being restocked, " +
								"please try again later", "Error", 
								JOptionPane.ERROR_MESSAGE, null);
					} else {
						for (LineItem li : rsList.getLineItems()) {
							li.setIsComplete(false);
						}
					}
				}
			});
			
		}
		
		private void layoutComponents() {
			c = new GridBagConstraints();
			int row;
			
			// First row
			row = 0;
			setGridBagConstraints(c, 0, row, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
			this.add(new TitleLabel("Drag n drop products:"), c);
			setGridBagConstraints(c, 2, row, GridBagConstraints.BOTH, 1.0, 0.0, 4, 1);
			this.add(new TitleLabel("Edit list qtys and locations:"), c);
			
			// Second row
			row = 1;
			setGridBagConstraints(c, 0, row, GridBagConstraints.BOTH, 0.0, 1.0, 2, 15);
			JScrollPane sp = new JScrollPane(tblProduct);
			sp.setPreferredSize(new Dimension(150, 300));
			this.add(sp, c);
			setGridBagConstraints(c, 2, row, GridBagConstraints.BOTH, 0.0, 1.0, 4, 9);
			sp = new JScrollPane(tblRestockList);
			this.add(sp, c);
			
			// Eleventh row
			row = 10;
			setGridBagConstraints(c, 2, row, GridBagConstraints.BOTH, 1.0, 0.0, 2, 1);
			this.add(new TitleLabel("Leave notes:"), c);
			
			// Twelfth row
			row = 11;
			setGridBagConstraints(c, 2, row, GridBagConstraints.BOTH, 1.0, 0.2, 4, 4);
			this.add(new JScrollPane(taNote), c);
			
			// Sixteenth row
			row = 15;
			setGridBagConstraints(c, 5, row, GridBagConstraints.BOTH, 1.0, 0.0, 1, 1);
			this.add(btnConfirm, c);
			setGridBagConstraints(c, 4, row, GridBagConstraints.BOTH, 1.0, 0.0, 1, 1);
			this.add(btnReset, c);
			setGridBagConstraints(c, 3, row, GridBagConstraints.BOTH, 1.0, 0.0, 1, 1);
			this.add(btnClear, c);
			
		}
		
		
		private void setRestockList(RestockingList list) {
			this.rsList = list;
			this.rsList.addChangeListener(this);
			updateAll();
		}
		
		protected void updateAll() {
			updateProductTable();
			updateList();
			updateNote();
			if (rsList != null) {
				taNote.setEditable(true);
				btnConfirm.setEnabled(true);
				btnClear.setEnabled(true);
				btnReset.setEnabled(true);
			}
		}
		
		private void updateProductTable() {
			java.util.List<Product> products = 
					new ArrayList<Product>(controller.listProducts());
			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).isRecalled() || !products.get(i).isActive()) {
					products.remove(i);
				}
			}
			// Update the Product List
			tblProduct.setModel(
					new ProductTableModel(products));
			
			// Adjust row heights and column widths
			tblProduct.getColumnModel().getColumn(0).setMaxWidth(50);
			try {
				for (int row=0; row<tblProduct.getRowCount(); row++) {
					int rowHeight = tblProduct.getRowHeight();
					for (int col=0; col<tblProduct.getColumnCount(); col++) {
						Component comp = 
								tblProduct.prepareRenderer(
										tblProduct.getCellRenderer(
												row, col), row, col);
						rowHeight = Math.max(rowHeight, 
								comp.getPreferredSize().height);
					}
					tblProduct.setRowHeight(row, rowHeight);
				}
			} catch(ClassCastException ignore) { }
		}
		
		private void updateList() {
			if (rsList == null) {
				return;
			}
			tblRestockList.setModel(
					new LineItemTableModel(rsList.getLineItems()));
			tblRestockList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel cm = tblRestockList.getColumnModel();
			TableColumn tc = cm.getColumn(0);
			tc.setPreferredWidth(25);
			tc.setMinWidth(25);
			tc.setMaxWidth(25);
			tc.setResizable(false);
			tc = cm.getColumn(1);
			tc.setMinWidth(150);
			tc = cm.getColumn(2);
			tc.setPreferredWidth(80);
			tc.setMinWidth(80);
			tc.setMaxWidth(80);
			tc.setResizable(false);
			tc = cm.getColumn(3);
			tc.setPreferredWidth(60);
			tc.setMinWidth(60);
			tc.setMaxWidth(60);
			tc.setResizable(false);
			tc = cm.getColumn(4);
			tc.setPreferredWidth(30);
			tc.setMinWidth(30);
			tc.setMaxWidth(30);
			tc.setResizable(false);
			tc = cm.getColumn(5);
			tc.setPreferredWidth(35);
			tc.setMinWidth(35);
			tc.setMaxWidth(35);
			tc.setResizable(false);
			tc = cm.getColumn(6);
			tc.setPreferredWidth(65);
			tc.setMinWidth(65);
			tc.setMaxWidth(65);
			tc.setResizable(false);
			tc = cm.getColumn(7);
			tc.setMinWidth(200);
		}
		
		private void updateNote() {
			if (rsList == null) {
				return;
			}
			taNote.setText(rsList.getNote());
		}
		
		private JPopupMenu createPopup(final int row) {
			JPopupMenu pMenu = new JPopupMenu();
			JMenuItem jmiRemove = new JMenuItem("Remove");
			jmiRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					removeLine(row);
				}
			});
			pMenu.add(jmiRemove);
			return pMenu;
		}
		
		private void removeLine(int row) {
			((LineItemTableModel)tblRestockList.getModel()).removeRow(row);
		}
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			String prop = evt.getPropertyName();
			if ("listSize".equals(prop)) {
				updateList();
			} else if ("note".equals(prop)) {
				updateNote();
			}
		}
		
	}
	
	class VendingMachinePanel extends JPanel {

		private JTable tblVM;
		
		public VendingMachinePanel() {
			super(new GridLayout(1,2));
			
			initComponents();
			
		}
		
		private void initComponents() {
			/*
			 * This panel will contain two other panels
			 * that have a JTable and a JLabel
			 */
			JPanel leftWestPanel = new JPanel(new BorderLayout());
			
			JScrollPane sp;
			// Now add the components, starting with the left eastern panel
			leftWestPanel.add(new TitleLabel("Select a machine"),
					BorderLayout.NORTH);
			sp = new JScrollPane(tblVM = new JTable());
			tblVM.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						JTable target = (JTable)e.getSource();
						int row = target.getSelectedRow();
						String id = ((VendingMachineTableModel)
								tblVM.getModel()).getElementAt(row).getID().getId();
						setModel(controller.findVendingMachine(id));

						vmView = new VendingMachineView(parent, model, controller);
						vmView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					}
				}
			});
			
			sp.setPreferredSize(new Dimension(200, 300));
			leftWestPanel.setBorder(new EmptyBorder(0,10,75,15));
			leftWestPanel.add(sp, BorderLayout.CENTER);
			
			this.add(leftWestPanel);
		}
		
		protected void updateAll() {
			updateVendingMachineTable();
		}
		
		private void updateVendingMachineTable() {
			tblVM.setModel(
					new VendingMachineTableModel(controller.listVendingMachines()));
		}

	}
	

	@Override
	public void actionPerformed(ActionEvent evt) {
		String action = evt.getActionCommand();
		
		// If a vending machine was added, removed or updated
		if ("addVendingMachine".equals(action) || 
				"removeVendingMachine".equals(action) ||
				"updateVendingMachine".equals(action)) {
			vmPanel.updateVendingMachineTable();
			/*if (model != null) {
				this.model = controller.findVendingMachine(model.getID().getId());
			}*/
		}
		// If a vending machine was added, removed or updated
		if ("addProduct".equals(action) || 
				"removeProduct".equals(action) ||
				"updateProduct".equals(action)) {
			restockPanel.updateProductTable();
		}
	}
	
}

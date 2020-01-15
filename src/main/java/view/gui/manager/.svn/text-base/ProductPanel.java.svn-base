package view.gui.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Product;

import sun.swing.DefaultLookup;
import util.ImageUtil;
import view.FieldLabel;
import view.ProductDetailsTableModel;
import view.ProductDetailsTableCellRenderer;
import view.TitleLabel;
import control.Controller;
import exceptions.OverwriteException;

@SuppressWarnings("serial")
public class ProductPanel extends JPanel implements ActionListener {

	private Controller controller;
	private boolean isUpdating = false;
	private static final String DROP_IMAGE_PROMPT = "Drop image here";
	private long oldUPC;
	
	// Properties
	private final static Dimension TF_SIZE = new Dimension(100, 35);
	
	// Components
	private JTable tblProducts;
	private JTextField tfAddProdName;
	private JTextField tfAddProdUPC;
	private JLabel lblPicture;
	@SuppressWarnings("rawtypes")
	private JComboBox<Comparable> cbAddProdType;
	private JButton btnAddProd;
	private JButton btnCancelProd;
	private JTextField tfRemoveProdUPC;
	private JButton btnRemoveProd;
	private JTextField tfRecallProdUPC;
	private JButton btnRecallProd;
	private JToggleButton btnShowAll;
	private JToggleButton btnShowActive;
	private ButtonGroup btnGroup;
	private GridBagConstraints c;
	private TableRowSorter<ProductDetailsTableModel> sorter;
	
	public ProductPanel(Controller controller) {
		super();
		
		this.controller = controller;
		
		initComponents();
		this.updateAll();
		
		controller.addActionListener(this);
	}
	
	private void initComponents() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10,25,25,25));
		JPanel westPanel = new JPanel(new BorderLayout());
		tblProducts = new JTable();
		tblProducts.setAutoCreateRowSorter(true);
		tblProducts.getTableHeader().setReorderingAllowed(false);
		tblProducts.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseReleased(MouseEvent e) {
	            int r = tblProducts.rowAtPoint(e.getPoint());
	            if (r >= 0 && r < tblProducts.getRowCount()) {
	            	tblProducts.setRowSelectionInterval(r, r);
	            } else {
	            	tblProducts.clearSelection();
	            }

	            int modelRow = tblProducts.convertRowIndexToModel(r);
	            if (r < 0)
	                return;
	            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
	                JPopupMenu popup = createPopup(((ProductDetailsTableModel)tblProducts.getModel()).getElementAt(modelRow));
	                popup.show(e.getComponent(), e.getX(), e.getY());
	            }
	        }
	        
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	if (e.getClickCount() == 2) {
	        		int row = tblProducts.convertRowIndexToModel(tblProducts.getSelectedRow());
	        		ProductDetailsTableModel model = 
	        				(ProductDetailsTableModel) tblProducts.getModel();
	        		editProduct(model.getElementAt(row));
	        	}
	        }
		});
		westPanel.add(new TitleLabel("Products:"), BorderLayout.NORTH);
		westPanel.add(new JScrollPane(tblProducts), BorderLayout.CENTER);
		JPanel southPanel = new JPanel(new GridLayout(1,2));
		btnShowAll = new JToggleButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateAll();
			}
			
		});
		btnShowActive = new JToggleButton("Show Active");
		btnShowActive.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateAll();
			}
			
		});
		btnGroup = new ButtonGroup();
		btnGroup.add(btnShowActive);
		btnGroup.add(btnShowAll);
		// Initially set showActive to selected
		btnGroup.setSelected(btnShowActive.getModel(), true);
		southPanel.add(btnShowAll);
		southPanel.add(btnShowActive);
		westPanel.add(southPanel, BorderLayout.SOUTH);
		
		westPanel.setBorder(new EmptyBorder(0,0,0,25));
		this.add(westPanel, BorderLayout.CENTER);
		
		// This will be GridBagLayout later
		c = new GridBagConstraints();
		JPanel eastPanel = new JPanel(new BorderLayout());
		eastPanel.setBorder(new EmptyBorder(50,0,0,0));
		JPanel eastSouthPanel = new JPanel(new GridLayout(2,1));

		eastPanel.add(createAddProductPanel(), BorderLayout.CENTER);
		
        eastSouthPanel.add(createRemoveProductPanel(), BorderLayout.NORTH);
        eastSouthPanel.add(createRecallProductPanel(), BorderLayout.SOUTH);
        eastPanel.add(eastSouthPanel, BorderLayout.SOUTH);
        //eastPanel.setBorder(new EmptyBorder(100,100,100,100));
        this.add(eastPanel, BorderLayout.EAST);
	}
	
	@SuppressWarnings("rawtypes")
	private JPanel createAddProductPanel() {
		JPanel addPanel = new JPanel(new GridBagLayout());
		addPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // First Grid Row
        setGridBagConstraints(0, 0, GridBagConstraints.BOTH, 0.5, 0.0, 2, 1);
        addPanel.add(new TitleLabel("Add/Update a Product"), c);
        
        // Second Grid Row
        setGridBagConstraints(0, 1, GridBagConstraints.BOTH, 0.5, 0.0, GridBagConstraints.REMAINDER, 1);
        c.insets.left = 4;
        addPanel.add(new FieldLabel("Product Name:"), c);
        c.insets.left = 0;
        c.gridx = 1;
        tfAddProdName = new JTextField();
        tfAddProdName.setPreferredSize(TF_SIZE);
        tfAddProdName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) { }

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				tfAddProdName.setBackground(Color.WHITE);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				tfAddProdName.setBackground(Color.WHITE);
			}
        });
        addPanel.add(tfAddProdName, c);
        
        // Third Grid Row
        setGridBagConstraints(0, 2, GridBagConstraints.BOTH, 0.5, 0.0, 1, 1);
        c.insets.left = 4;
        addPanel.add(new FieldLabel("Product UPC:"), c);
        c.insets.left = 0;
        c.gridx = 1;
        tfAddProdUPC = new JTextField();
        tfAddProdUPC.setPreferredSize(TF_SIZE);
        tfAddProdUPC.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) { }

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				tfAddProdUPC.setBackground(Color.WHITE);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				tfAddProdUPC.setBackground(Color.WHITE);
			}
        });
        addPanel.add(tfAddProdUPC, c);
        //setGridBagConstraints(2, 2, GridBagConstraints.BOTH, 1.0, 0.0, 1, 1);
        //addPanel.add(btnAddProd = new JButton("Add/Update"), c);
        //btnAddProd.addActionListener(new ActionListener() {
		//	@Override
		//	public void actionPerformed(ActionEvent arg0) {
		///		addProduct();
		//	}
        //});
        
        
        // Fourth Grid Row
        setGridBagConstraints(0, 3, GridBagConstraints.BOTH, 0.5, 0.0, 1, 1);
        c.insets.left = 4;
        addPanel.add(new FieldLabel("Product Type:"), c);
        c.insets.left = 0;
        c.gridx = 1;
        c.gridwidth = 2;
        Product.TYPE[] selection = Product.TYPE.values();
        cbAddProdType = new JComboBox<Comparable>();
        cbAddProdType.addItem("Select Type");
        for (int i = 0; i < selection.length; i++) {
        	cbAddProdType.addItem(selection[i]);
        }
        cbAddProdType.setPreferredSize(TF_SIZE);
        cbAddProdType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				if ("comboBoxChanged".equals(evt.getActionCommand())) {
					cbAddProdType.setBackground(DefaultLookup.getColor(cbAddProdType, ui, "List.dropCellBackground"));
				}
			}
        	
        });
        addPanel.add(cbAddProdType, c);
        //setGridBagConstraints(2, 3, GridBagConstraints.BOTH, 1.0, 0.0, 1, 1);
        //addPanel.add(btnCancelProd = new JButton("Cancel"), c);
        //btnCancelProd.addActionListener(new ActionListener() {
		//	@Override
		//	public void actionPerformed(ActionEvent arg0) {
		//		clearFields();
		//	}
        //});
        
        
        
        // Fifth Grid Row
        setGridBagConstraints(0, 4, GridBagConstraints.CENTER, 0.5, 0.0, 4, 4);
        c.insets.left = 4;
        lblPicture = new JLabel(DROP_IMAGE_PROMPT, SwingConstants.CENTER);
        lblPicture.setPreferredSize(new Dimension(200,200));
        lblPicture.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        //lblPicture.setTransferHandler(new TransferHandler("icon"));
        
        lblPicture.setTransferHandler(new TransferHandler() {

			@SuppressWarnings("unchecked")
			@Override
            public boolean importData( JComponent comp, Transferable aTransferable ) {
            	
            	try {
            		// Only pull the first item in the list
            		//@SuppressWarnings("unchecked")
					Object transferItem = aTransferable.getTransferData(DataFlavor.javaFileListFlavor);
					List<Object> paths = null;
					if (transferItem instanceof List<?>) {
						 paths = (List<Object>) transferItem;
					}
					if (paths == null) {
						return false;
					}
					String path = paths.get(0).toString();
            		lblPicture.setIcon(
            				new ImageIcon(
            						ImageUtil.LoadSquareImage(
            								path, 
            								lblPicture.getWidth())));
            		lblPicture.setText(path);
            	} catch ( UnsupportedFlavorException e ) {
              	  System.err.println("Unsupported Flavor");
              	  return false;
                } catch ( IOException e ) {
              	  System.err.println("IO Exception: " + e.getMessage());
              	  return false;
                }

              return true;
            }

            @Override
            public boolean canImport( JComponent comp, DataFlavor[] transferFlavors ) {
              return true;
            }
		});
        addPanel.add(lblPicture, c);
        
        // Ninth Grid Row
        setGridBagConstraints(0, 8, GridBagConstraints.BOTH, 0.5, 0.0, 2, 1);
        addPanel.add(btnAddProd = new JButton("Add/Update"), c);
        btnAddProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addProduct();
			}
        });
        setGridBagConstraints(2, 8, GridBagConstraints.BOTH, 0.5, 0.0, 1, 1);
        addPanel.add(btnCancelProd = new JButton("Cancel"), c);
        btnCancelProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearFields();
			}
        });
        
        return addPanel;
	}
	
	private JPanel createRemoveProductPanel() {
		JPanel removePanel = new JPanel(new GridBagLayout());
		removePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // First Grid Row
        setGridBagConstraints(0, 0, GridBagConstraints.BOTH, 0.5, 0.0, 2, 1);
        removePanel.add(new TitleLabel("Discontinue a Product"), c);
        
        // Second Grid Row
        setGridBagConstraints(0, 1, GridBagConstraints.BOTH, 0.5, 0.0, 1, 1);
        c.insets.left = 4;
        removePanel.add(new FieldLabel("Product UPC:"), c);
        c.insets.left = 0;
        c.gridx = 1;
        tfRemoveProdUPC = new JTextField();
        tfRemoveProdUPC.setPreferredSize(TF_SIZE);
        tfRemoveProdUPC.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) { }

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				tfRemoveProdUPC.setBackground(Color.WHITE);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				tfRemoveProdUPC.setBackground(Color.WHITE);
			}
        });
        removePanel.add(tfRemoveProdUPC, c);
        setGridBagConstraints(2, 1, GridBagConstraints.BOTH, 0.5, 0.0, 1, 2);
        removePanel.add(btnRemoveProd = new JButton("Discontinue"), c);
        btnRemoveProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Verify the UPC is valid
				if (tfRemoveProdUPC.getText().length() != 10) {
					tfRemoveProdUPC.setBackground(Color.RED);
					return;
				}
				long upc = 0;
				try {
					upc = Long.parseLong(tfRemoveProdUPC.getText());
				} catch(NumberFormatException e) {
					tfRemoveProdUPC.setBackground(Color.RED);
					return;
				}
				removeProduct(upc);				
			}
        });        
        
        return removePanel;
	}
	
	private JPanel createRecallProductPanel() {
		JPanel recallPanel = new JPanel(new GridBagLayout());
		recallPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // First Grid Row
        setGridBagConstraints(0, 0, GridBagConstraints.BOTH, 0.5, 0.0, 2, 1);
        recallPanel.add(new TitleLabel("Recall a Product"), c);
        
        // Second Grid Row
        setGridBagConstraints(0, 1, GridBagConstraints.BOTH, 0.5, 0.0, 1, 1);
        c.insets.left = 4;
        recallPanel.add(new FieldLabel("Product UPC:"), c);
        c.insets.left = 0;
        c.gridx = 1;
        tfRecallProdUPC = new JTextField();
        tfRecallProdUPC.setPreferredSize(TF_SIZE);
        tfRecallProdUPC.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) { }

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				tfRecallProdUPC.setBackground(Color.WHITE);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				tfRecallProdUPC.setBackground(Color.WHITE);
			}
        });
        recallPanel.add(tfRecallProdUPC, c);
        setGridBagConstraints(2, 1, GridBagConstraints.BOTH, 0.5, 0.0, 1, 2);
        recallPanel.add(btnRecallProd = new JButton("Recall"), c);
        btnRecallProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Verify the UPC is valid
				if (tfRecallProdUPC.getText().length() != 10) {
					tfRecallProdUPC.setBackground(Color.RED);
					return;
				}
				long upc = 0;
				try {
					upc = Long.parseLong(tfRecallProdUPC.getText());
				} catch(NumberFormatException e) {
					tfRecallProdUPC.setBackground(Color.RED);
					return;
				}
				recallProduct(upc);				
			}
        });
        
        return recallPanel;
	}
	
    private void setGridBagConstraints(int x, int y, int fill
            , double weightx, double weighty, int width, int height) {
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
        
    }

	private void setFilter() {
    	RowFilter<Object,Object> hideInactiveFilter = new RowFilter<Object,Object>() {
    		@Override
			public boolean include(Entry<? extends Object, ? extends Object> entry) {
    			ProductDetailsTableModel tblModel = ((ProductDetailsTableModel)tblProducts.getModel());
   				Product p = tblModel.getElementAt((Integer) entry.getIdentifier());
   				if (p.isActive() && !p.isRecalled()) {
   					return true;
   				}
		   		return false;
    		}
    	};

    	try {
    		sorter.setRowFilter(hideInactiveFilter);
        } catch (java.util.regex.PatternSyntaxException e) { }
	}    
    
	public void updateAll() {
		java.util.List<Product> products = 
				new ArrayList<Product>(controller.listProducts());
		
		TableColumn tCol;
		// Update the Product List
		tblProducts.setModel(
				new ProductDetailsTableModel(products));
		sorter = new TableRowSorter<ProductDetailsTableModel>(
				(ProductDetailsTableModel) tblProducts.getModel());
		
		// If we aren't showing all, remove the unwanted products from the list
		if (btnShowAll.getModel() != btnGroup.getSelection()) {
			setFilter();
		}
		
		tblProducts.setRowSorter(sorter);
		ProductDetailsTableCellRenderer cr = new ProductDetailsTableCellRenderer();
		tCol = tblProducts.getColumnModel().getColumn(0);
		//tCol.setWidth(50);
		tCol = tblProducts.getColumnModel().getColumn(1);
		//tCol.setWidth(200);
		tCol.setCellRenderer(cr);
		tCol = tblProducts.getColumnModel().getColumn(2);
		tCol.setCellRenderer(cr);
		//tCol.setWidth(200);
		tCol = tblProducts.getColumnModel().getColumn(3);
		tCol.setCellRenderer(cr);
		//tCol.setWidth(200);
		
		// Adjust row heights
		try {
		    for (int row=0; row<tblProducts.getRowCount(); row++) {
		        int rowHeight = tblProducts.getRowHeight();
		 
		        for (int column=0; column<tblProducts.getColumnCount(); column++) {
		            Component comp = tblProducts.prepareRenderer(tblProducts.getCellRenderer(row, column), row, column);
		            rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
		        }
		 
		        tblProducts.setRowHeight(row, rowHeight);
		    }
		} catch(ClassCastException ignore) { }
	}
	
	private void clearFields() {
		tfAddProdName.setText("");
		tfAddProdUPC.setText("");
		cbAddProdType.setSelectedIndex(0);
		tfRemoveProdUPC.setText("");
		tfRecallProdUPC.setText("");
		lblPicture.setText(DROP_IMAGE_PROMPT);
		lblPicture.setIcon(null);
		isUpdating = false;
	}
	
	private void editProduct(Product p) {
		// Store the old UPC for the update function
		oldUPC = p.getUPC();
		
		// Fill in the fields with the product values
		tfAddProdName.setText(p.getName());
		tfAddProdUPC.setText(String.valueOf(p.getUPC()));
		cbAddProdType.setSelectedItem(p.getType());
			
		// Set the product image
		try {
			lblPicture.setIcon(new ImageIcon(ImageUtil.LoadSquareImage(p.getImagePath(), lblPicture.getWidth())));
			lblPicture.setText(p.getImagePath());
		} catch (IOException e) {
			lblPicture.setIcon(null);
			System.err.println("Error loading image: " + e.getMessage());
		}
		
		// Let the GUI know we are updating an existing
		isUpdating = true;
	}
	
	private void addProduct() {
		String error = "";
		// Verify a type was selected
		if (cbAddProdType.getSelectedIndex() == 0) {
			cbAddProdType.setBackground(Color.RED);
			error = "\nA Product Type must be selected";
		}
		// Verify the UPC is valid
		if (tfAddProdUPC.getText().length() != 10) {
			error += "\nUPC must be ten digits";
			tfAddProdUPC.setBackground(Color.RED);
		}
		long upc = 0;
		try {
			upc = Long.parseLong(tfAddProdUPC.getText());
		} catch(NumberFormatException e) {
			error += "\nUPC must be numeric";
			tfAddProdUPC.setBackground(Color.RED);
		}
		// Verify the product name is not blank
		String name = tfAddProdName.getText();
		if (name.length() == 0) {
			error += "\nMust enter a Product Name";
			tfAddProdName.setBackground(Color.RED);
		}
		
		// If there were errors, display them and return
		if (error.length() != 0) {
			JOptionPane.showMessageDialog(null, "Please fix the items in red\n" + error, "Error", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		
		// They can enter any product name and the type does not need to be
		// checked since the list came from the actual enum type.
		Product.TYPE type = Product.TYPE.valueOf(cbAddProdType.getSelectedItem().toString());
		
		Product addProduct = new Product(name, type, upc);
		// If the drop image prompt is not there, we have an image
		if (!lblPicture.getText().equals(DROP_IMAGE_PROMPT)) {
			// We have a new image, store it
			String imageName = String.valueOf(addProduct.getUPC());
			try {
				ImageUtil.StoreImage(lblPicture.getText(), imageName);
			} catch (IOException e) {
				System.err.println("Error saving image: " + e.getMessage());
			}
			addProduct.setImagePath(imageName);
		}
		if (isUpdating) {
			controller.updateProduct(oldUPC, addProduct);
			isUpdating = false;
		} else {
			try {
				controller.addProduct(addProduct);
			} catch (OverwriteException e) {
				JOptionPane.showMessageDialog(null, "You can't overwrite products, that upc already exists\nTo edit product information double click the product", "Error", JOptionPane.ERROR_MESSAGE, null);
				tfAddProdUPC.setBackground(Color.RED);
				return;
			}
		}
		this.clearFields();
	}
	
	private void removeProduct(long upc) {
		controller.deleteProduct(upc);
		this.clearFields();
	}
	
	private void recallProduct(long upc) {
		Product p = controller.findProduct(upc);
		if (p == null) {
			return;
		}
		p.setRecalled(true);
		controller.updateProduct(upc, p);
		this.clearFields();
	}
	
	private void unrecallProduct(long upc) {
		Product p = controller.findProduct(upc);
		if (p == null) {
			return;
		}
		p.setRecalled(false);
		controller.updateProduct(upc, p);
		this.clearFields();
	}
	
	private JPopupMenu createPopup(final Product p) {
		JPopupMenu pMenu = new JPopupMenu();
		String message = "";
		if (p.isRecalled()) {
			message = "REMOVE RECALL on " + p.getName();
		} else {
			message = "RECALL all " + p.getName();
		}
		JMenuItem jmiRecall = new JMenuItem(message);
		jmiRecall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (p.isRecalled()) {
					unrecallProduct(p.getUPC());
				} else {
					recallProduct(p.getUPC());
				}
			}
		});
		if (p.isActive()) {
			message = "DISCONTINUE " + p.getName();
		} else {
			message = "REACTIVATE " + p.getName();
		}
		JMenuItem jmiRemove = new JMenuItem(message);
		jmiRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (p.isActive()) {
					removeProduct(p.getUPC());
				} else {
					p.setActive(true);
					controller.updateProduct(p.getUPC(), p);
				}
			}
		});
		pMenu.add(jmiRecall);
		pMenu.add(jmiRemove);
		return pMenu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// If the product list was updated, update our view of it.
		String action = e.getActionCommand();
		if ("addProduct".equals(action) || "removeProduct".equals(action) || 
				"updateProduct".equals(action)) {
			this.updateAll();
		}
	}
	
	
}

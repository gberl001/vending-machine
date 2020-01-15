package view.gui.restocker;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import model.LineItem;
import model.Product;
import model.RestockingList;
import model.VendingMachine;
import model.VendingMachine.STATE;

import util.ImageUtil;

import view.LineItemRestockTableModel;
import view.RestockTableCellRenderer;
import view.VendingMachineTableModel;

import control.Controller;
import exceptions.InsufficientProductsException;
import exceptions.InvalidLocationException;
import exceptions.SlotOverflowException;

@SuppressWarnings("serial")
public class RestockerCardPanel extends JPanel{
	protected final static String MACHINE_CARD = "Machine List";
	protected final static String LOGIN_CARD = "Login";
	protected final static String LIST_CARD = "Item List";
	protected final static String DETAILS_CARD = "Item Details";
	protected JButton confirm;
	protected JButton gotoDetails;
	protected JButton overview;
	protected JButton completeBtn;
	protected JButton prevBtn;
	protected JButton nextBtn;
	protected JTable machineList;
	protected VendingMachine model;
	protected Controller controller;
	protected RestockerMachinePanel mp;
	protected RestockerListPanel lp;
	protected RestockerDetailsPanel dp;
	protected RestockingList rsList = new RestockingList();
	public RestockerCardPanel(Controller controller, VendingMachine model){
		super(new CardLayout());
		this.controller = controller;
		this.model = model;
		mp = new RestockerMachinePanel(); 
		mp.setName(MACHINE_CARD);
		this.add(mp, MACHINE_CARD);	
		lp = new RestockerListPanel();
		lp.setName(LIST_CARD);
		this.add(lp, LIST_CARD);
		dp = new RestockerDetailsPanel();
		dp.setName(DETAILS_CARD);
		this.add(dp, DETAILS_CARD);
	}
	
	public boolean isRestockListEmpty(){
		return lp.isRestockListEmpty();
	}
	public boolean isRestockComplete(){
		return dp.isRestockComplete();
	}
	public void setStateUnlocked(){
		model.setState(STATE.READY);
	}
	
	public void setStateLocked(){
		model.setState(STATE.RESTOCK);
	}

	public void setModel(VendingMachine model){
		this.model = model;
		lp.updateAll();
		if(gotoDetails.getText().equals("Begin")){
			dp.addExpiredItems();
		}
		dp.nextItem();
	}
	
	public void detailsNext(){
		dp.nextItem();
	}
	
	public void detailsPrev(){
		dp.previousItem();
	}
	
	public boolean detailsCompl(){
		return dp.completeItem();
	}
	
	public void listCompl(){
		lp.updateTable();
	}
	
	class RestockerMachinePanel extends JPanel{
		private JLabel l;
		
		public RestockerMachinePanel(){
			super(new BorderLayout());
			initComponents();
		}
		public void initComponents(){
			JPanel topPanel = new JPanel();
			l = new JLabel("Please Select Your Vending Machine");
			l.setFont(new Font("Dialog", Font.PLAIN, 26));
			topPanel.add(l, BorderLayout.CENTER);
			this.add(topPanel, BorderLayout.NORTH);
			
			JPanel centerPanel = new JPanel();
			JScrollPane sp = new JScrollPane(machineList = new JTable());
			sp.setBorder(BorderFactory.createEmptyBorder(3, 50, 20, 50));
			machineList.setModel(
				   new VendingMachineTableModel(controller.listVendingMachines()));
			machineList.getTableHeader().setReorderingAllowed(false);
			machineList.getTableHeader().setEnabled(false);
			centerPanel.add(sp);
			this.add(centerPanel, BorderLayout.CENTER);
		}	
	}
	
	class RestockerListPanel extends JPanel{
		private JTable restockList;
		private JTextArea managerNote;
		private JScrollPane sp;
		private JScrollPane tsp;
		public RestockerListPanel(){
			super(new BorderLayout());	
			initComponents();
		}

		private void initComponents(){
			JPanel topPanel = new JPanel();
			sp = new JScrollPane(restockList = new JTable());
			restockList.getTableHeader().setReorderingAllowed(false);
			restockList.getTableHeader().setEnabled(false);
			topPanel.add(sp);
			topPanel.setBorder(new EmptyBorder(10,20,10,20));
			this.add(topPanel, BorderLayout.NORTH);
			
			JPanel centerPanel = new JPanel();
			tsp = new JScrollPane(managerNote = new JTextArea());
			tsp.setPreferredSize(new Dimension(200,100));
			managerNote.setEditable(false);
			managerNote.setWrapStyleWord(true);
			managerNote.setLineWrap(true);
			centerPanel.add(tsp);
			this.add(centerPanel, BorderLayout.CENTER);
			
			JPanel botPanel = new JPanel();
			gotoDetails = new JButton("Begin");
			gotoDetails.setPreferredSize(new Dimension(80,30));
			botPanel.add(gotoDetails);
			this.add(botPanel, BorderLayout.SOUTH);
		}
		private void updateAll(){
			updateNote();
			updateTable();
			if(restockList.getRowCount() == 0){
				gotoDetails.setPreferredSize(new Dimension(180,30));
				gotoDetails.setText("Return to Machines List");
				gotoDetails.setEnabled(true);
				model.setState(STATE.READY);
			}
		}
		private void updateNote(){
			if(model.getRestockList() != null){
				managerNote.setText(model.getRestockList().getNote());
			}
		}
		private void updateTable(){
			if(model.getRestockList() != null){
				restockList.setModel(new LineItemRestockTableModel(model.getRestockList().getLineItems()));
				TableCellRenderer completedRender = new RestockTableCellRenderer();
				for(int j = 0; j < restockList.getColumnCount(); j++){
					restockList.getColumnModel().getColumn(j).setCellRenderer(completedRender);
				}
			}
		}
		protected boolean isRestockListEmpty(){
			List<LineItem> products = model.getRestockList().getLineItems();
			for(int i = 0; i < products.size(); i++){
				if(!products.get(i).isComplete()){
					return false;
				}
			}
			return true;
		}
	}
	
	class RestockerDetailsPanel extends JPanel{
		//this top panel will hold the overview button 
		//product image and check box
		JPanel topPanel;
		JLabel imageLb;
		Image itemImage;
		JLabel checkBoxLb;//tmp placeholder for checkbox
		
		//this center panel will hold 6 other panels each corresponding to 
		//the restocking list item
		JPanel middlePanel;
		//the upc panel and components
		JPanel upcPanel;
		JLabel upcLb;
		JLabel upcNumLb;
		
		//the slot panel and components
		JPanel slotPanel;
		JLabel slotLb;
		JLabel slotTf;
		
		//the cost panel and components
		JPanel costPanel;
		JLabel costLb;
		JLabel costTf;
		
		//the quantity panel and components
		JPanel qtyPanel;
		JLabel addRemoveLb;
		JTextField qtyTf;
		
		//the expiration panel and components
		JPanel expirationPanel;
		JLabel expirationLb;
		JDateChooser date;
		
		//the notes panel and component
		JPanel notesPanel;
		JTextArea note;
		
		//the complete instruction panel with buttons moving 
		//to the previous and next instruction
		JPanel completePanel;
		
		//the bottom panel will hold the progress bar
		JPanel bottomPanel;
		JProgressBar progressBar;
		
		//used to hold the list of lines item to be restocked
		List<LineItem> products;
		LineItem currentItem;
		int lineItemIndex;
		Product currentProduct;
		
		/**
		 * a constructor for this class
		 */
		public RestockerDetailsPanel(){
			super(new BorderLayout());
			initComponents();
			this.add(topPanel, BorderLayout.NORTH);
			this.add(middlePanel, BorderLayout.CENTER);
			this.add(bottomPanel, BorderLayout.SOUTH);
		}
		private void initComponents(){
			//top panel and components
			topPanel = new JPanel(new BorderLayout());
			JPanel topLeftPanel = new JPanel();
			JPanel topRightPanel = new JPanel();
			JPanel topCenterPanel = new JPanel();
			checkBoxLb = new JLabel("                       ");
			topRightPanel.add(checkBoxLb);
			overview = new JButton("Overview");
			topLeftPanel.add(overview);
			imageLb = new JLabel("");
			topCenterPanel.add(imageLb);
			topPanel.add(topLeftPanel, BorderLayout.WEST);
			topPanel.add(topCenterPanel, BorderLayout.CENTER);
			topPanel.add(topRightPanel, BorderLayout.EAST);
			
			//the 6 panels for the middle panel
			//upc panel and components
			upcPanel = new JPanel();
			//JPanel mid = new JPanel();
			upcLb = new JLabel("UPC:");
			upcLb.setFont(new Font("Dialog", Font.PLAIN, 24));
			upcNumLb = new JLabel("");
			upcNumLb.setFont(new Font("Dialog", Font.PLAIN, 24));
			upcPanel.add(upcLb, BorderLayout.WEST);
			upcPanel.add(upcNumLb, BorderLayout.EAST);
			
			//slot panel and components
			slotPanel = new JPanel();
			slotLb = new JLabel("Slot:");
			slotLb.setFont(new Font("Dialog", Font.PLAIN, 24));
			slotTf = new JLabel();
			slotTf.setFont(new Font("Dialog", Font.PLAIN, 24));
			slotTf.setPreferredSize(new Dimension(150,30));
			slotPanel.add(slotLb, BorderLayout.WEST);
			slotPanel.add(slotTf, BorderLayout.EAST);
			
			//cost panel and components
			costPanel = new JPanel(); 
			costLb = new JLabel("Cost:");
			costLb.setFont(new Font("Dialog", Font.PLAIN, 24));
			costTf = new JLabel();
			costTf.setFont(new Font("Dialog", Font.PLAIN, 24));
			costTf.setPreferredSize(new Dimension(150,30));
			costPanel.add(costLb);//, BorderLayout.WEST);
			costPanel.add(costTf);//, BorderLayout.EAST);
			
			//quantity panel and components
			qtyPanel = new JPanel();
			qtyTf = new JTextField();
			qtyTf.setFont(new Font("Dialog", Font.PLAIN, 24));
			qtyTf.setPreferredSize(new Dimension(150,30));
			addRemoveLb = new JLabel("");
			addRemoveLb.setFont(new Font("Dialog", Font.PLAIN, 24));
			qtyPanel.add(addRemoveLb, BorderLayout.WEST);
			qtyPanel.add(qtyTf, BorderLayout.EAST);
			
			//expiration panel and components
			expirationPanel = new JPanel();
			expirationLb = new JLabel("Expiration:", SwingConstants.RIGHT);
			expirationLb.setFont(new Font("Dialog", Font.PLAIN, 24));
			date = new JDateChooser();
			((JTextFieldDateEditor)date.getDateEditor()).setEditable(false);
			date.setPreferredSize(new Dimension(150,30));
			expirationPanel.add(expirationLb, BorderLayout.WEST);
			expirationPanel.add(date, BorderLayout.EAST);
			
			//notes panel and component
			notesPanel = new JPanel(new BorderLayout());
			note = new JTextArea();
			note.setSize(new Dimension(150,30));
			notesPanel.add(note, BorderLayout.CENTER);
			
			//complete instruction panel and components
			completePanel = new JPanel(new BorderLayout());
			completeBtn = new JButton("Completed");
			completeBtn.setFont(new Font("Dialog", Font.PLAIN, 24));
			prevBtn = new JButton("<");
			nextBtn = new JButton(">");
			completePanel.add(new JPanel().add(completeBtn), BorderLayout.CENTER);
			completePanel.add(prevBtn, BorderLayout.WEST);
			completePanel.add(nextBtn, BorderLayout.EAST);
			
			//the middle panel that holds the previously declared panels
			middlePanel = new JPanel(new GridLayout(7,1));
			middlePanel.add(upcPanel);
			middlePanel.add(slotPanel);
			middlePanel.add(costPanel);
			middlePanel.add(qtyPanel);
			middlePanel.add(expirationPanel);
			middlePanel.add(notesPanel);
			middlePanel.add(completePanel);
			
			//the bottom panel that holds the progress bar
			bottomPanel = new JPanel(new BorderLayout());
			progressBar = new JProgressBar();
			bottomPanel.add(progressBar, BorderLayout.CENTER);
			
			//add top, middle and bottom panels to the main panel
			this.add(topPanel, BorderLayout.NORTH);
			this.add(middlePanel, BorderLayout.CENTER);
			this.add(bottomPanel, BorderLayout.SOUTH);
			
			//initialize products list and line item index
			products = new ArrayList<LineItem>();
			currentItem = null;
			lineItemIndex = -1;
		}
		private boolean completeItem(){
			//Handle Note
			String lineItemNote = note.getText();
			note.setText("");
			if(lineItemNote != null){
				products.get(lineItemIndex).setNotes(lineItemNote);
			}
			
			//Handle Quantity Errors
			String itemQuantity = qtyTf.getText();
			if(itemQuantity.length() == 0){
				JOptionPane.showMessageDialog(null, "No Quantity Entered", "Error!", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			int quantity = Integer.parseInt(itemQuantity);
			if(quantity < 0 || quantity > model.getInventory().getSlotSize()){
				JOptionPane.showMessageDialog(null, "Invalid quantity.", "Error!", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			//Handle adding
			if(addRemoveLb.getText().equals("Add:")){
				//Handle date
				try{
					java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("MMM-d-yyyy");
					fmt.format(date.getDate());
				}catch(NullPointerException e){
					JOptionPane.showMessageDialog(null, "No Expiration Date Entered", "Error!", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				products.get(lineItemIndex).setQty(quantity);
				currentProduct = controller.findProduct(products.get(lineItemIndex).getUPC());
				if(currentProduct.isRecalled()){
					JOptionPane.showMessageDialog(null, "Product has been Recalled. Do not restock.\nAn automated note has been sent to the manager.", "Error!", JOptionPane.ERROR_MESSAGE);
					products.get(lineItemIndex).setNotes("Product was recalled and so was not stocked.");
				}else{
				try {
					currentProduct.setExpirationDate(date.getDate());
					model.addProductAt(currentProduct, products.get(lineItemIndex).getLocation(), products.get(lineItemIndex).getQty());	
					model.setCostAt(products.get(lineItemIndex).getLocation(), products.get(lineItemIndex).getPrice());
				}catch(InvalidLocationException e){
					String msg = e.getMessage();
					JOptionPane.showMessageDialog(null, msg + "\nA message has been sent to the Manager notifying them of this error.", "Error!", JOptionPane.ERROR_MESSAGE);
					products.get(lineItemIndex).setNotes("Invalid slot location was given, item was not restocked.");
				} catch (SlotOverflowException e) {
					String msg = e.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error!", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				}
			}else{	//Handle removing
				currentProduct = controller.findProduct(products.get(lineItemIndex).getUPC());
				try{
					model.removeProductAt(products.get(lineItemIndex).getLocation(), quantity, products.get(lineItemIndex).getUPC());
				}catch(InvalidLocationException e){
					String msg = e.getMessage();
					JOptionPane.showMessageDialog(null, msg+ "\nA message has been sent to the Manager notifying them of this error.", "Error!", JOptionPane.ERROR_MESSAGE);
					products.get(lineItemIndex).setNotes("Invalid slot location was given, item was not restocked.");
				}catch(InsufficientProductsException e){
					String msg = e.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error!", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				int price = products.get(lineItemIndex).getPrice();
				if(!(price == 0)){
					model.setCostAt(products.get(lineItemIndex).getLocation(), products.get(lineItemIndex).getPrice());
				}
			}
			
			//Handle line item cleanup
			products.get(lineItemIndex).setIsComplete(true);
			rsList.addLine(products.get(lineItemIndex));
			if(isRestockComplete()){
				model.setState(STATE.READY);
				model.setRestockingList(rsList);
				gotoDetails.setText("View Details");
				completeBtn.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Restocking Complete.\nReturn to main menu.", "Done", JOptionPane.INFORMATION_MESSAGE);
			}
			return true;
		}
		private void previousItem(){
			if(update(lineItemIndex-1)){
				lineItemIndex--;
			}
		}
		private void nextItem(){
			if(update(lineItemIndex+1)){
				lineItemIndex++;
			}
		}
		
		private boolean isRestockComplete(){
			for(int i = 0; i < products.size(); i++){
				if(!products.get(i).isComplete()){
					return false;
				}
			}
			addExpiredItems();
			for(int i = 0; i < products.size(); i++){
				if(!products.get(i).isComplete()){
					return false;
				}
			}
			return true;
		}
		
		private void addExpiredItems(){
			for(int i = 0; i < model.getNumRows(); i++){
				for(int j = 0; j < model.getNumCols(); j++){
					String a = String.valueOf(i+1);
					String b = String.valueOf(j);
					String c = a+b;
					int slotLocation = Integer.parseInt(c);
					for(int k = 0; k < model.getNumProductsAt(slotLocation); k++){
						if(model.getProductAt(slotLocation, k).isExpired()){
							Product product = model.getProductAt(slotLocation, k);
							LineItem item = new LineItem(product.getName(), product.getUPC(), slotLocation, -1, k);
							item.setNotes("Product Expired");
							products.add(item);
						}
					}
				}
			}
		}
		private boolean update(int i){
			products = model.getRestockList().getLineItems();
			if(products != null && i < products.size() && i >= 0){
				if(products.get(i).isComplete()){
					completeBtn.setEnabled(false);
				}else{
					completeBtn.setEnabled(true);
				}
				try {
					itemImage = ImageUtil.LoadSquareImage(String.valueOf(products.get(i).getUPC()), 100);
					imageLb.setIcon(new ImageIcon(itemImage));
				} catch (IOException e) {
					System.err.println("Image can't be loaded");
				} catch (Exception e){
					System.err.println(e.getMessage());
				}
				currentItem = products.get(i);
				upcNumLb.setText("" + currentItem.getUPC());
				slotTf.setText("" + currentItem.getLocation());
				NumberFormat fmt = NumberFormat.getCurrencyInstance();
				costTf.setText(fmt.format(((float)currentItem.getPrice())/100));
				if(currentItem.getQty() > 0){
					addRemoveLb.setText("Add:");
				}
				else {
					addRemoveLb.setText("Remove:");
				}
				qtyTf.setText("" + Math.abs(currentItem.getQty()));				
				return true;
			}else{
				return false;
			}
		}
		
		protected void resetLineItemIndex(){
			lineItemIndex = 0;
			update(lineItemIndex);
		}
		
	}
}
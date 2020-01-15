package view.gui.customer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import view.ShoppingCartTableModel;
import control.Controller;
import exceptions.EmptySlotException;
import exceptions.ExpiredProductException;
import exceptions.InvalidLocationException;
import exceptions.RecalledProductException;
import model.LineItem;
import model.VendingMachine;

/**
 * A panel for the Customer View
 * 
 * @author dxs3203
 *
 */
@SuppressWarnings("serial")
public class CustomerPanel extends JPanel {
	
	private VendingMachine model;
	
	//this is a text field used to hold the values entered by keypad
	private JTextField numberPadEntryText;
		
	/**
	 * A constructor for this class
	 */
	public CustomerPanel(Controller controller, VendingMachine model) {
		//set border layout
		super(new BorderLayout());
		
		this.model = model;
		
		//create and add the top, middle and bottom panels
		TopPanel tp = new TopPanel();
		this.add(tp, BorderLayout.NORTH);
		
		MiddlePanel mp = new MiddlePanel();
		this.add(mp, BorderLayout.CENTER);
		
		BottomPanel bp = new BottomPanel();
		this.add(bp, BorderLayout.SOUTH);
	}

	/**
	 * The TopPanel Class contain two components:
	 *    1) The number pad entry field
	 *    2) The shopping cart label
	 *    
	 * @author dxs3203
	 */
	public class TopPanel extends JPanel {
		
		/**
		 * A constructor for this class
		 */
		public TopPanel(){
			super(new BorderLayout());
			initComponents();
		}
		
		/**
		 * initialize components
		 */
		private void initComponents(){
			//set panel's layout and border
			JPanel numberPadAndCartLabelPan = new JPanel(new BorderLayout());
			numberPadAndCartLabelPan.setBorder(
					BorderFactory.createEmptyBorder(6, 10, 0, 0));
			
			//shopping cart label.
			JLabel cartLabel = new JLabel("  Shopping Cart      ");
			cartLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
			
			//initialize entry field
			numberPadEntryText = new JTextField();
			numberPadEntryText.setHorizontalAlignment(SwingConstants.RIGHT);
			numberPadEntryText.setEditable(false);
			numberPadEntryText.setFont(new Font("Dialog", Font.PLAIN, 40));
			
			//add both components to the pane
			numberPadAndCartLabelPan.add(
					numberPadEntryText, BorderLayout.CENTER);
			numberPadAndCartLabelPan.add(cartLabel, BorderLayout.EAST);
			
			//add panel to top panel
			this.add(numberPadAndCartLabelPan, BorderLayout.NORTH);
		}
		
	}
	
	/**
	 * the middle panel contains components for the number pad
	 * shopping cart table and checkout.
	 * 
	 * @author dxs3203
	 *
	 */
	class MiddlePanel extends JPanel implements PropertyChangeListener, 
												ActionListener{
		private JTable t;//table for the shopping cart
		private JButton[][] array;//number pad buttons
		private TotalButton checkout;//checkout button
		
		/**
		 * A constructor for this class
		 */
		public MiddlePanel(){
			super(new BorderLayout());
			initComponents();
		}
		/**
		 * initialize components
		 */
		private void initComponents(){
			//create and set layout of the number pad panel
			JPanel numberPadPan = new JPanel(new GridLayout(4,3,25,25));
			numberPadPan.setBorder(
					BorderFactory.createEmptyBorder(0, 10, 30, 11));
			
			//number pad buttons			
			array = new RoundButton[4][3];
			
			//create all buttons using custom buttons
			array[0][0] = new RoundButton("1");
		    array[0][1] = new RoundButton("2");
			array[0][2] = new RoundButton("3");
			array[1][0] = new RoundButton("4");
			array[1][1] = new RoundButton("5");
			array[1][2] = new RoundButton("6");
			array[2][0] = new RoundButton("7");
			array[2][1] = new RoundButton("8");
			array[2][2] = new RoundButton("9");
			array[3][0] = new RoundButton("Clear");
			array[3][1] = new RoundButton("0");
			array[3][2] = new RoundButton("Enter");
			
			//add each button to panel, set font and add action listener
		    for(int i = 0; i < 3; i++){
		        for(int j = 0; j < 3; j++){    
		           numberPadPan.add(array[i][j]);
		           array[i][j].setFont(new Font("Dialog", Font.PLAIN, 30));
		           array[i][j].addActionListener(this);
		        }
		    }
		    numberPadPan.add(array[3][0]);
		    numberPadPan.add(array[3][1]);
		    numberPadPan.add(array[3][2]);
		    array[3][0].setFont(new Font("Dialog", Font.PLAIN,24));
		    array[3][0].addActionListener(this);
		    array[3][1].setFont(new Font("Dialog", Font.PLAIN,30));
		    array[3][1].addActionListener(this);
		    array[3][2].setFont(new Font("Dialog", Font.PLAIN,24));
		    array[3][2].addActionListener(this);
		    
		    //add number pad panel to the center of middle panel
			this.add(numberPadPan, BorderLayout.CENTER);
			
			//the dimensions for the scroll pane
			Dimension d = new Dimension(200,300);
			
			//create and initialize the shopping cart table
			t = new JTable();
			List<LineItem> l = new ArrayList<LineItem>();
			t.setModel(new ShoppingCartTableModel(l));
			t.setFillsViewportHeight( true );
			t.getTableHeader().setReorderingAllowed(false);
			t.getTableHeader().setResizingAllowed(false);
			t.getColumnModel().getColumn(1).setPreferredWidth(20);
			t.getColumnModel().getColumn(2).setPreferredWidth(30);
			
			
			//the panel that contains a JTable for the shopping cart
			JPanel shoppingCartPan = new JPanel(new BorderLayout());
			JScrollPane sp = new JScrollPane(t);
			sp.setPreferredSize(d);
			sp.setBorder(BorderFactory.createEmptyBorder(3, 3, 0, 5));
			shoppingCartPan.add(sp, BorderLayout.NORTH);
			
			
			//the shopping cart buttons panel
			JPanel shoppingCartButtonPan = new JPanel(new BorderLayout());
			
			//create and initialize clear order button
			JButton clearOrder = new JButton("Clear Order");
			clearOrder.setForeground(Color.RED);
			clearOrder.setFont(new Font("Dialog", Font.PLAIN, 14));
			clearOrder.addActionListener(this);
				
			//create and initialize checkout button
			checkout = new TotalButton("Checkout", 000);
			checkout.addActionListener(this);
			checkout.setFont(new Font("Dialog", Font.PLAIN, 24));
			
			//panel for clear order button and cart total
			JPanel panTotal = new JPanel(new BorderLayout());
			panTotal.add(clearOrder,BorderLayout.CENTER);
					
			//add components to shopping cart panel
			shoppingCartButtonPan.add(checkout,BorderLayout.CENTER);
			shoppingCartButtonPan.add(panTotal,BorderLayout.SOUTH);
			shoppingCartPan.add(shoppingCartButtonPan, BorderLayout.CENTER);
			this.add(shoppingCartPan, BorderLayout.EAST);
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			//the string sent by the event
			String event = e.getActionCommand();
			
			//if the textfield contains one of these warning strings, clear it.
			if(numberPadEntryText.getText().equals("Invalid Location") ||
					numberPadEntryText.getText().equals("Slot Empty") ||
					numberPadEntryText.getText().equals("Product Recalled") ||
					numberPadEntryText.getText().equals("Product Expired") ||
					numberPadEntryText.getText().equals("No more products")){
				numberPadEntryText.setText("");
			}
			
			/* Appends the entered number to the end of the text contained 
			 * in the text field if it less than length 3 since valid locations
			 * will be at most 3 characters long.
			 */
			if(event.equals("1")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}		
			} 
			else if(event.equals("2")){	
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			} 
			else if(event.equals("3")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			} 
			else if(event.equals("4")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			}
			else if(event.equals("5")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			}
			else if(event.equals("6")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			} 
			else if(event.equals("7")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			} 
			else if(event.equals("8")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			} 
			else if(event.equals("9")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			} 
			else if(event.equals("0")){			
				if(numberPadEntryText.getText().length() < 3) {
					numberPadEntryText.setText(
							numberPadEntryText.getText() + event);
				}
			} 
			//clears the text field
			else if(event.equals("Clear")){	
				
				numberPadEntryText.setText("");
			} 
			//When enter is pressed the text field string is validated 
			//against a number of conditions
			else if(event.equals("Enter")){				
				try {
					model.addToOrder(Integer.parseInt(
							numberPadEntryText.getText()));
					numberPadEntryText.setText("");
					checkout.setTotal(computeTotal());
					updateTable();
				} catch(IllegalStateException ie){
					numberPadEntryText.setText(ie.getMessage());
				} catch (InvalidLocationException ie){
					numberPadEntryText.setText("Invalid Location");
				} catch (NumberFormatException e1) { 
				} catch (RecalledProductException e1) {
					numberPadEntryText.setText("Product Recalled");
				} catch (ExpiredProductException e1) {
					numberPadEntryText.setText("Product Expired");
				} catch (EmptySlotException e1) {
					numberPadEntryText.setText("No more products");
				}		
			} 
			//checkout calls the completeTransaction method, updates the table
			//and resets the total
			else if(event.equals("Checkout")){
				try {
					model.completeTransaction();
					updateTable();
					checkout.setTotal(0);
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				catch(IllegalStateException e1){
					numberPadEntryText.setText(e1.getMessage());
				}
				
			} 
			//clear oder removes all items from the current transaction,
			//updates the table and resets the total
			else if(event.equals("Clear Order")){
				try {
					model.cancelTransaction();
					updateTable();
					checkout.setTotal(0);
				}
				catch(IllegalStateException e1){
					numberPadEntryText.setText(e1.getMessage());
				}
			}
			
		}
		
		/**
		 * computeTotal
		 * 
		 * @return the sum of all line items in the current transaction
		 */
		private double computeTotal() {
			int total = 0;
			try {
				if (model.getTransaction() == null) {
					return 0;
				}
				if (model.getTransaction().getLineItems() == null) {
					return 0;
				}
				List<LineItem> list = new ArrayList<LineItem>(
						model.getTransaction().getLineItems());
				
				for(int i = 0; i < list.size(); i++){
					total += list.get(i).getPrice();
				}
			}
			catch(IllegalStateException e1){
				numberPadEntryText.setText(e1.getMessage());
			}
			return (total);
		}
		
		
		/**
		 * updates the shopping cart by sending it the list of 
		 * line items in the current transaction.
		 */
		private void updateTable() {
			try {
				if(model.getTransaction() != null){
					t.setModel(new ShoppingCartTableModel(
							model.getTransaction().getLineItems()));	
				} 
				else {
					List<LineItem> l = new ArrayList<LineItem>();
					t.setModel(new ShoppingCartTableModel(l));
				}
				t.getColumnModel().getColumn(1).setPreferredWidth(20);
				t.getColumnModel().getColumn(2).setPreferredWidth(30);
			}
			catch(IllegalStateException e1){
				numberPadEntryText.setText(e1.getMessage());
			}
		}
		@Override
		public void propertyChange(PropertyChangeEvent e) {	}
		
		
	}
	/**
	 * setModel
	 * @param model
	 */
	public void setModel(VendingMachine model){
		this.model = model;
	}
	/**
	 * the bottom panel contains the option to change the date
	 * 
	 * @author dxs3203
	 *
	 */
	class BottomPanel extends JPanel implements PropertyChangeListener{
		//a component that provides an easy way to choose a date
		private JDateChooser cal;
		
		/**
		 * A constructor for this class
		 */
		public BottomPanel(){
			super(new BorderLayout());
			initComponents();
	
		}
		
		/**
		 * initialize components
		 */
		private void initComponents(){
			cal = new JDateChooser();
			cal.setPreferredSize(new Dimension(150, 30));
			cal.setDate(VendingMachine.getSimulatedDate());
			((JTextFieldDateEditor)cal.getDateEditor()).setEditable(false);
			cal.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getNewValue() instanceof Date) {
						model.setSimulatedDate(cal.getDate());
					}
				}
			});
			
			
			this.add(cal, BorderLayout.WEST);
		}
		
		@Override
		public void propertyChange(PropertyChangeEvent e) {
			if(e.getPropertyName().equals("date")){
				model.setSimulatedDate(cal.getDate());
			}
		}
	}
	
	/**
	 * A method used to append a string onto the text contained 
	 * within the text field
	 * @param s
	 */
	public void appendToEntryText(String s){
		numberPadEntryText = new JTextField( numberPadEntryText.getText() + s);
		repaint();
	}
}
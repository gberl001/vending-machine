/**
 * AddMachinesPanel.java
 *
 * 	Version:
 *	$Id:$
 *
 *	Revisions:
 *	$Log:$
 */

/**
 *
 *	@author	jimiford
 */
package view.gui.manager;

//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import util.StringUtils;
import view.FieldLabel;

import model.VendingID;
import model.VendingID.State;
import model.VendingMachine;

import control.Controller;
import exceptions.InvalidFormatException;
import exceptions.OverwriteException;


/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class AddMachinesPanel extends JPanel {

	private static String[] validRows = {"Rows","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
	private static String[] validCols = {"Columns","1","2","3","4","5","6","7","8","9","10"};
	private static String[] validCapacity  = {"Capacity","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
//	private static final String DEFAULT_NOTES = "Notes...";
	
	private Controller controller;
	private GridBagConstraints c;
	private JTextField vendingID;
	private JTextField city;
	private JTextField notes;
	private JComboBox<State> state;
	private JTextField address;
	private JTextField zip;
	private JTextField location;
	private JComboBox<String> rows;
	private JComboBox<String> cols;
	private JComboBox<String> capacity;
	private final Color DEFAULT = new Color(57,105,138);
	private JButton addMachine;
	
	
	
	
	
	public AddMachinesPanel(Controller controller) {
		super(new FlowLayout());
		this.controller = controller;
		initComponents();
		updateAll();
		this.setSize(100, 400);
	}
	

	private void updateAll() {
		updateVendingID();
	}

	private void updateVendingID() {
		vendingID.setText(StringUtils.leadingZeroes((controller.getMaxID() + 1), 
				Controller.NUM_DIGITS));
	}
	
//	private void initStates() {
//		
//	}
	
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
	
	private void initComponents() {
		this.setBorder(new CompoundBorder(new EmptyBorder(4,0,0,0),BorderFactory.createLineBorder(Color.gray)));
		//vendingID = new JTextField("" + (model.getMaxID() + 1));
		// TODO FIX THIS FOR JIMI (sorry Jimi)
		vendingID = new JTextField("");
		city = new JTextField("Rochester");
		state = new JComboBox<State>(State.values());
		address = new JTextField("1 Lomb Memorial Drive");
		zip = new JTextField("14623");
		location = new JTextField("Golisano");
		addMachine = new JButton("Add Machine");
		rows = new JComboBox<String>(validRows);
		rows.setToolTipText("Rows");
		cols = new JComboBox<String>(validCols);
		cols.setToolTipText("Columns");
		capacity = new JComboBox<String>(validCapacity);
		capacity.setToolTipText("Slot Capacity");
		ToolTipManager.sharedInstance().registerComponent(rows);
		ToolTipManager.sharedInstance().registerComponent(cols);
		ToolTipManager.sharedInstance().registerComponent(capacity);
//		notes = new JTextField(DEFAULT_NOTES);
//		notes.addFocusListener(new FocusListener() {
//
//			@Override
//			public void focusGained(FocusEvent e) {
//				if(notes.getText().equals(DEFAULT_NOTES)) {
//					notes.setText("");
//				}
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				if(notes.getText().equals("")) {
//					notes.setText(DEFAULT_NOTES);
//				}
//			}
//		});
		addMachine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				VendingID id = null;
				boolean error = false;
				int row = 0, col = 0, cap = 0;
				try {
					row = Integer.parseInt(rows.getItemAt(rows.getSelectedIndex()));
					rows.setBackground(DEFAULT);
				} catch (NumberFormatException n) {
					rows.setBackground(Color.red);
					error = true;
				} try {
					col = Integer.parseInt(cols.getItemAt(cols.getSelectedIndex()));
					cols.setBackground(DEFAULT);
				} catch (NumberFormatException n) {
					cols.setBackground(Color.red);
					error = true;
				} try {
					cap = Integer.parseInt(capacity.getItemAt(capacity.getSelectedIndex()));
					capacity.setBackground(DEFAULT);
				} catch (NumberFormatException n) {
					capacity.setBackground(Color.red);
					error = true;
				}
				if(error) {
					return;
				}
				try {
					id = new VendingID(
							StringUtils.lPad(vendingID.getText(),
									Controller.NUM_DIGITS) ,
							state.getItemAt(state.getSelectedIndex()),
							city.getText(),
							Integer.parseInt(zip.getText()),
							address.getText(), 
							location.getText()
							);
				} catch (NumberFormatException e1) {
					zip.setText("Enter 5 digits");
				}
				catch (InvalidFormatException e1) {
					zip.setText(e1.getMessage());
				}
				VendingMachine tmp = new VendingMachine(id, controller, row, col, cap);
				try {
					controller.addVendingMachine(tmp);
				} catch (OverwriteException e1) {
					JOptionPane.showMessageDialog(null,  "A vending machine with that ID already exists", "Error",JOptionPane.ERROR_MESSAGE, null);
				}
				updateAll();
			}
		});
		
		rows = new JComboBox<String>(validRows);
		cols = new JComboBox<String>(validCols);
		capacity = new JComboBox<String>(validCapacity);
		rows.setBackground(DEFAULT);
		cols.setBackground(DEFAULT);
		capacity.setBackground(DEFAULT);
		
		fillComponents();
	}
	
	private void fillComponents() {
		JPanel main = new JPanel(new GridBagLayout());
		JPanel right = new JPanel(new GridBagLayout());
		
		c = new GridBagConstraints();
		//fill vendingID info
		setGridBagConstraints(0, 0, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		c.insets = new Insets(5,0,0,0);
		main.add(new FieldLabel("Vending ID"), c);
		setGridBagConstraints(1, 0, GridBagConstraints.BOTH, 0.0, 0.0, 3, 1);
		main.add(vendingID, c);
		setGridBagConstraints(0, 1, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		main.add(new FieldLabel("Address"), c);
		setGridBagConstraints(1, 1, GridBagConstraints.BOTH, 0.0, 0.0, 3, 1);
		main.add(address, c);
		setGridBagConstraints(0, 2, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		main.add(new FieldLabel("Location"), c);
		setGridBagConstraints(1, 2, GridBagConstraints.BOTH, 0.0, 0.0, 3, 1);
		main.add(location, c);
		setGridBagConstraints(0, 3, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		main.add(new FieldLabel("City"), c);
		setGridBagConstraints(1, 3, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		main.add(city, c);
		setGridBagConstraints(2, 3, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		main.add(new FieldLabel("State"), c);
		setGridBagConstraints(3, 3, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		state.setSelectedIndex(32);
		main.add(state, c);
		setGridBagConstraints(0, 4, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		main.add(new FieldLabel("Zip"), c);
		setGridBagConstraints(1, 4, GridBagConstraints.BOTH, 0.0, 0.0, 1, 1);
		main.add(zip, c);
		setGridBagConstraints(0, 5, GridBagConstraints.BOTH, 0.0, 0.0, 4, 2);
//		main.add(notes, c);
		
		
		
		//fill labels for dimensions
//		setGridBagConstraints(0, 0, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
//		right.add(new FieldLabel("Rows:"), c);
//		setGridBagConstraints(0, 2, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
//		right.add(new FieldLabel("Columns:"), c);
//		setGridBagConstraints(0, 4, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
//		right.add(new FieldLabel("Capacity:"), c);
//		setGridBagConstraints(0, 6, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
//		right.add(new FieldLabel("Confirm"), c);
		
		//fill dimensions info
		setGridBagConstraints(0, 1, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
		rows.setSelectedIndex(0);
		right.add(rows, c);
		setGridBagConstraints(0, 3, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
		cols.setSelectedIndex(0);
		right.add(cols, c);
		setGridBagConstraints(0, 5, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
		capacity.setSelectedIndex(0);
		right.add(capacity, c);
		setGridBagConstraints(0, 7, GridBagConstraints.BOTH, 0.0, 0.0, 2, 1);
		right.add(addMachine, c);
		
//		this.add(rows, c);
//		
//		this.add(cols, c);
//		this.add(capacity, c);
//		
//		this.add(new JLabel(), c);
//		this.add(zip, c);
//		this.add(addMachine, c);
		this.add(main);
		this.add(right);
	}
	
}

package view.gui.manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import model.VendingMachine;

import view.VendingMachineDetailsTableModel;
import control.Controller;


@SuppressWarnings("serial")
public class MachinesPanel extends JPanel implements ActionListener {

	private Controller controller;
	private JTable tblVM;
	
	public MachinesPanel(Controller controller) {
		super(new GridLayout(1,2));
		this.controller = controller;
		
		initComponents();
		updateAll();
		
		controller.addActionListener(this);
	}

		
	private void initComponents() {
		/*
		 * This panel will contain two other panels
		 * that have a JTable and a JLabel
		 */
		JPanel leftWestPanel = new JPanel(new BorderLayout());
		JPanel rightWestPanel = new JPanel(new BorderLayout());
		AddMachinesPanel amp = new AddMachinesPanel(controller);
		
		JScrollPane sp;
		// Now add the components, starting with the left eastern panel
		leftWestPanel.add(
				new JLabel("Select a vending machine", SwingConstants.CENTER),
				BorderLayout.NORTH);
		sp = new JScrollPane(tblVM = new JTable());
		tblVM.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseReleased(MouseEvent e) {
	            int r = tblVM.rowAtPoint(e.getPoint());
	            if (r >= 0 && r < tblVM.getRowCount()) {
	            	tblVM.setRowSelectionInterval(r, r);
	            } else {
	            	tblVM.clearSelection();
	            }

	            int row = tblVM.getSelectedRow();
	            if (row < 0)
	                return;
	            if (e.isPopupTrigger()) {
	                JPopupMenu popup = createPopup((String) tblVM.getValueAt(row, 5));
	                popup.show(e.getComponent(), e.getX(), e.getY());
	            }
	        }
	        
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	if (e.getClickCount() == 2) {
	        		int row = tblVM.getSelectedRow();
	        		VendingMachineDetailsTableModel model = 
	        				(VendingMachineDetailsTableModel) tblVM.getModel();
	        		editMachine(model.getElementAt(row));
	        	}
	        }
		});
		leftWestPanel.add(sp, BorderLayout.CENTER);
		
		// Now the right eastern panel
		rightWestPanel.add(
				new JLabel("Enter Details", SwingConstants.CENTER), BorderLayout.NORTH);
		rightWestPanel.add(amp, BorderLayout.CENTER);

		
		this.add(leftWestPanel);
		this.add(rightWestPanel);
	}
		
	private void updateAll() {
		updateVendingMachineTable();
	}
	
	private void updateVendingMachineTable() {
		tblVM.setModel(
				new VendingMachineDetailsTableModel(controller.listVendingMachines()));
	}

	private void editMachine(VendingMachine vm) {
		System.err.println("editMachine not implemented");
	}
	
	private void removeMachine(String id) {
		controller.deleteVendingMachine(id);
	}
	
	private JPopupMenu createPopup(final String id) {
		JPopupMenu pMenu = new JPopupMenu();

		JMenuItem jmiRemove = new JMenuItem("REMOVE " + id);
		jmiRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeMachine(id);
			}
		});
		pMenu.add(jmiRemove);
		return pMenu;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		String action = evt.getActionCommand();
		if ("addVendingMachine".equals(action) || 
				"removeVendingMachine".equals(action) ||
				"updateVendingMachine".equals(action)) {
			updateAll();
		}
	}
}

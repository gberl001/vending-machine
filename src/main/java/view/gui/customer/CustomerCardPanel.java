package view.gui.customer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.VendingMachine;
import view.VendingMachineTableModel;
import control.Controller;
/**
 * A class used to hold the CustomerSelectMachinePanel and the CustomerPanel
 * 
 * @author dxs3203
 *
 */
public class CustomerCardPanel extends JPanel{

	private static final long serialVersionUID = 40073048173285367L;
	protected final static String MACHINE_CARD = "Machine Card";
	protected final static String CUSTOMER_PANEL_CARD = "Customer Panel";
	protected JTable machineList;
	private JPanel cp;
	
	/**
	 * A constructor for this class
	 * 
	 * @param controller
	 * @param model
	 */
	public CustomerCardPanel(Controller controller, VendingMachine model){
		super(new CardLayout());
		JPanel mp = new CustomerSelectMachinePanel(controller); 
		this.add(mp, CUSTOMER_PANEL_CARD);
		cp = new CustomerPanel(controller, model);
		this.add(cp, MACHINE_CARD);
		
	}
	
	/**
	 * set the vending machine in CustomerPanel to the given vending machine
	 * @param model
	 */
	public void setCustPanelModel(VendingMachine model){
		((CustomerPanel) cp).setModel(model);
	}
	
	/**
	 * A class that holds a table of all vending machines
	 * @author dxs3203
	 */
	class CustomerSelectMachinePanel extends JPanel{

		private static final long serialVersionUID = -6503873023873118214L;
		Controller controller;
		JLabel l;
		
		/**
		 * A constructor for this class
		 * @param controller
		 */
		public CustomerSelectMachinePanel(Controller controller){
			super(new BorderLayout());
			this.controller = controller;
			initComponents();
		}
		
		/**
		 * initialize components
		 */
		public void initComponents(){
			JPanel pan = new JPanel();
			l = new JLabel("Please Select Your Vending Machine");
			l.setFont(new Font("Dialog", Font.PLAIN, 26));
			pan.add(l,BorderLayout.CENTER);
			this.add(pan, BorderLayout.NORTH);
			
			JScrollPane sp = new JScrollPane(machineList = new JTable());
			machineList.setModel(new VendingMachineTableModel(
					controller.listVendingMachines()));
			sp.setBorder(BorderFactory.createEmptyBorder(3, 50, 20, 50));
			machineList.getTableHeader().setReorderingAllowed(false);
			this.add(sp, BorderLayout.CENTER);
		
		}	
	}
}

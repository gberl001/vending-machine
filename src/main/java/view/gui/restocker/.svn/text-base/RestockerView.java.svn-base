package view.gui.restocker;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import view.VendingMachineTableModel;
import view.gui.inventory.VendingMachineView;
import model.VendingMachine;
import control.Controller;

@SuppressWarnings("serial")
public class RestockerView extends JFrame implements ActionListener, MouseListener{
	Controller controller;
	VendingMachine model;
	RestockerCardPanel rcp;
	@SuppressWarnings("unused")
	private VendingMachineView vmView;
	
	public RestockerView(Controller controller){
		super("Restocker View");
		this.controller = controller;
		initComponents();
	}
	
	public void initComponents(){		
		rcp = new RestockerCardPanel(controller, model);
		rcp.gotoDetails.addActionListener(this);
		rcp.overview.addActionListener(this);
		rcp.machineList.addMouseListener(this);
		rcp.nextBtn.addActionListener(this);
		rcp.prevBtn.addActionListener(this);
		rcp.completeBtn.addActionListener(this);
		this.add(rcp);
		WindowListener exitListener = new WindowListener(){
			@Override
			public void windowClosing(WindowEvent e) {
				JPanel card = null;
				for(Component comp : rcp.getComponents()){
					if(comp.isVisible()){
						card = (JPanel) comp;
					}
				}
				System.out.println(card.getName());
				if(!card.getName().equals("Machine List")){
					rcp.setStateUnlocked();
				}
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		this.addWindowListener(exitListener);
	}
	public void unlockState(){
		rcp.setStateUnlocked();
	}
	private static void createAndShowGUI(){
		Controller vmMan = new Controller();
		JFrame frame = new RestockerView(vmMan);
		frame.setSize(550,600);
		// Get the current screen's size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width/2-frame.getSize().width/2);
		int y = (screen.height/2-frame.getSize().height/2);
		frame.setLocation(x, y);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String args[]){
		try{
			for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}catch(Exception e){
			
		}
		
		if (!System.getProperty("java.version").startsWith("1.7")) {
			JOptionPane.showMessageDialog(null, "Please download Java version 7", "Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}
		
		Runnable doCreateAndShowGUI = new Runnable(){
			@Override
			public void run(){
				createAndShowGUI();
			}
		};
		SwingUtilities.invokeLater(doCreateAndShowGUI);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rcp.gotoDetails){
			if(rcp.gotoDetails.getText().equals("View Details")){
				((CardLayout)(rcp.getLayout())).show(rcp, RestockerCardPanel.DETAILS_CARD);
			}else if(rcp.gotoDetails.getText().equals("Return to Machines List")){
				((CardLayout)(rcp.getLayout())).show(rcp, RestockerCardPanel.MACHINE_CARD);
			}else{
				((CardLayout)(rcp.getLayout())).show(rcp, RestockerCardPanel.DETAILS_CARD);
				rcp.setStateLocked();
			}
		}
		if(e.getSource() == rcp.overview){
			if(!rcp.isRestockComplete()){
				rcp.gotoDetails.setText("Resume");
			}else{
				rcp.dp.resetLineItemIndex();
				rcp.gotoDetails.setPreferredSize(new Dimension(180,30));
				rcp.gotoDetails.setText("Return to Machines List");
			}
			((CardLayout)(rcp.getLayout())).show(rcp, RestockerCardPanel.LIST_CARD);
		}
		if(e.getSource() == rcp.nextBtn){
			rcp.detailsNext();
		}
		if(e.getSource() == rcp.prevBtn){
			rcp.detailsPrev();
		}
		if(e.getSource() == rcp.completeBtn){
			if(rcp.detailsCompl() && !rcp.isRestockComplete()){
				rcp.detailsNext();
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int index = rcp.machineList.getSelectedRow();
		if(index != -1){			
			model = ((VendingMachineTableModel)rcp.machineList.getModel()).
					getElementAt(index);
			rcp.setModel(model);
			vmView = new VendingMachineView(this, model, controller);	
			rcp.setStateUnlocked();
			System.out.println(rcp.isRestockComplete());
			System.out.println(rcp.isRestockListEmpty());
			if(rcp.isRestockComplete() && rcp.isRestockListEmpty()){
				rcp.gotoDetails.setPreferredSize(new Dimension(100,30));
				rcp.gotoDetails.setText("View Details");
			}
			((CardLayout)(rcp.getLayout())).show(rcp, RestockerCardPanel.LIST_CARD);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }
}

package view.gui.manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import view.FieldLabel;
import view.TitleLabel;
import view.TransactionDataTableModel;
import view.VendingMachineTableModel;
import control.Controller;

@SuppressWarnings("serial")
public class AnalyticsPanel extends JPanel {

	private Controller controller;
	private VendingMachinePanel vmPanel;
	private PieChartPanel pieChartPanel;
	private RawDataPanel tblPanel;
	private List<String[]> rawData;
	private List<String[]> tempData;
	private String currentID = "";
	
	public AnalyticsPanel(Controller controller) {
		super(new BorderLayout());
		
		this.controller = controller;
		this.updateRawData();							// Get all raw data
		
		vmPanel = new VendingMachinePanel();  			// Create the panel
		controller.addActionListener(vmPanel);
		vmPanel.updateAll();// Force initial update
		
		this.add(vmPanel, BorderLayout.WEST);
		this.add(createChartTabPanel(), BorderLayout.CENTER);
		
		// Create a timer to update raw data every two seconds
		new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateRawData();
			}
		}).start();
	}
	
	private JTabbedPane createChartTabPanel() {
		JTabbedPane tabs = new JTabbedPane(SwingConstants.LEFT);
		
		// Table Data
		tblPanel = new RawDataPanel();
		tblPanel.update();
		tabs.add(tblPanel, "Raw Data");
		
		// Pie Chart
		pieChartPanel = new PieChartPanel(null);
		pieChartPanel.update();
		tabs.add(pieChartPanel, "Pie Chart");
		
		// Create another panel and add it to tabs here
		//JPanel newPanel = new JPanel();
		//newPanel.update();
		//tabs.add(newPanel, "Panel Name");
		
		return tabs;
	}
	
	/*
	 * Creates the temp list containing only data for our selected machine, if
	 * id is blank it clears the selected VM
	 */
	private void updateTempData() {
		if (currentID == "") {
			tempData = null;
		} else {
			tempData = new ArrayList<String[]>();
			for (String[] row : rawData) {
				if (row == null || row.length == 0) {
					continue;
				}
				if (row[0].replaceAll("\"", "").equals(currentID)) {
					tempData.add(row);
				}
			}
		}
		pieChartPanel.update();
	}

	
	private void updateRawData() {
		List<String[]> newRawData = null;
		try {
			newRawData = controller.readTransactionLog();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't load data", "Error", 
					JOptionPane.ERROR_MESSAGE, null);
		}

		if (rawData == null) {
			rawData = newRawData;
			if (tblPanel != null) {
				tblPanel.update();
			}
			if (pieChartPanel != null) {
				pieChartPanel.update();
			}
		}
		
		if (!(rawData.size() == newRawData.size())) {
			System.err.println("Changed");
			rawData = newRawData;
			if (tblPanel != null) {
				tblPanel.update();
			}
			if (pieChartPanel != null) {
				pieChartPanel.update();
			}
		}
	}
	
	
	class VendingMachinePanel extends JPanel implements ActionListener {

		private JTable tblVM;
		
		public VendingMachinePanel() {
			super(new BorderLayout());
			
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
						currentID = (((VendingMachineTableModel)
								tblVM.getModel()).getElementAt(row)).getID().getId();
						tblPanel.update();
						pieChartPanel.update();
					}
				}

			});
			
			sp.setPreferredSize(new Dimension(200, 300));
			leftWestPanel.setBorder(new EmptyBorder(0,10,75,15));
			leftWestPanel.add(sp, BorderLayout.CENTER);
			
			JButton btnShowAll = new JButton("Show ALL");
			btnShowAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					currentID = "";
					tblPanel.update();
					pieChartPanel.update();
				}
			});
			
			this.add(btnShowAll, BorderLayout.SOUTH);
			this.add(leftWestPanel, BorderLayout.CENTER);
		}
		
		protected void updateAll() {
			updateVendingMachineTable();
			updateRawData();
		}
		
		private void updateVendingMachineTable() {
			tblVM.setModel(
					new VendingMachineTableModel(controller.listVendingMachines()));
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			String action = evt.getActionCommand();
			
			// If a vending machine was added, removed or updated
			if ("addVendingMachine".equals(action) || 
					"removeVendingMachine".equals(action) ||
					"updateVendingMachine".equals(action)) {
				updateVendingMachineTable();
			}
		}

	}
	
	class RawDataPanel extends JPanel {

		private JTable tblRawData;
		private TableRowSorter<TransactionDataTableModel> sorter;
		private JTextField tfSearch;
		private FieldLabel lblCount;
		
		public RawDataPanel() {
			super(new BorderLayout());
			
			initComponents();
			fillComponents();
		}
		
		private void initComponents() {
			tfSearch = new JTextField();
			tfSearch.setPreferredSize(new Dimension(150, 30));
			tfSearch.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent arg0) {	}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					setFilter();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					setFilter();
				}
			});
			
			
			tblRawData = new JTable();
			tblRawData.setFillsViewportHeight(true);
			
			lblCount = new FieldLabel("Record Count: 0");
		}
		
		private void fillComponents() {
			JPanel filterPanel = new JPanel(new FlowLayout());
			filterPanel.add(new FieldLabel("Filter: "));
			filterPanel.add(tfSearch);
			
			this.add(filterPanel, BorderLayout.NORTH);
			this.add(new JScrollPane(tblRawData), BorderLayout.CENTER);	
			this.add(lblCount, BorderLayout.SOUTH);
		}
		
		private void setFilter() {
	    	if (tfSearch.getText().equals("*") || tfSearch.getText().equals("")) {
	    		sorter.setRowFilter(null);
	    		lblCount.setText("Record Count: " + tblRawData.getRowCount());
	    		return;
	    	}
	    	RowFilter<Object,Object> startsWithAFilter = new RowFilter<Object,Object>() {
	    		@Override
				public boolean include(Entry<? extends Object, ? extends Object> entry) {
		    		//If any column in row starts with filter
			   		if (!tfSearch.getText().startsWith("*")) {
			   			for (int i = entry.getValueCount() - 1; i >= 0; i--) {
			   				if (entry.getStringValue(i).toUpperCase().startsWith(tfSearch.getText().toUpperCase())) {
			   					return true;
			   				}
			   			}  
			   		}
	    			//If any column in row contains filter
			   		else if (tfSearch.getText().startsWith("*")) {
			   			for (int i = entry.getValueCount() - 1; i >= 0; i--) {
			   				if (entry.getStringValue(i).toUpperCase().indexOf(tfSearch.getText().substring(1).toUpperCase()) >= 0) {
			   					return true;
			   				}
			   			}
			   		}
		    		//The value wasn't found in the the filter field
			   		return false;
	    		}
	    	};

	    	try {
	    		sorter.setRowFilter(startsWithAFilter);
	        	lblCount.setText("Record Count: " + sorter.getViewRowCount());
	        } catch (java.util.regex.PatternSyntaxException e) {
	        	System.out.println("Error in setFilter");
	        }
		}
		
		private void update() {
			if (rawData == null) {
				return;
			}
			if ("".equals(currentID)) {
				tblRawData.setModel(new TransactionDataTableModel(rawData));	
			} else {
				updateTempData();
				tblRawData.setModel(new TransactionDataTableModel(tempData));
			}
			sorter = new TableRowSorter<TransactionDataTableModel>(
				(TransactionDataTableModel) tblRawData.getModel());
			tblRawData.setRowSorter(sorter);
			setFilter();
			lblCount.setText("Record Count: " + sorter.getModelRowCount());
		}
		

	}
	
	class PieChartPanel extends ChartPanel {
		
		public PieChartPanel(JFreeChart chart) {
			super(chart);
			
			initComponents();
			
		}
		
		private void initComponents() {

		}
		
		private void update() {
			// Update chart data based on selected ID, if none show all data
			this.setChart(createPieChart());
		}
		
		private JFreeChart createPieChart() {
			String title;
			if ("".equals(currentID)) {
				title = "All Vending Machines";
			} else {
				title = "Vending Machine " + currentID;
			}
			
			JFreeChart chart = ChartFactory.createPieChart3D(
					title,
					createPieDataset(),                // data
					true,                   // include legend
					true,
					false);
			
			PiePlot3D plot = (PiePlot3D) chart.getPlot();
			plot.setStartAngle(290);
			plot.setDirection(Rotation.CLOCKWISE);
			plot.setForegroundAlpha(0.75f);
			return chart;
			
		}
		
		private PieDataset createPieDataset() {
			List<String[]> data;
			// if we have rawData, use it, else use all data
			if (rawData == null) {
				return null;
			}
			if (currentID == "") {
				data = rawData;
			} else {
				data = tempData;
			}
			
			// Loop through the data and chart on product sales
			DefaultPieDataset result = new DefaultPieDataset();
			for (String[] row : data) {
				double cnt = 0;
				try {
					cnt = (Double) result.getValue(row[2].replaceAll("\"", ""));
				} catch (UnknownKeyException ignore) { }
				result.setValue(row[2].replaceAll("\"", ""), ++cnt);
			}

	        return result;
		}

	}
	
}

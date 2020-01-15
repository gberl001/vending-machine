/**
 * JimiAnalyticsPanel.java
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
package view.gui.manager.analytics;

import javax.swing.JPanel;

import control.Controller;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class JimiAnalyticsPanel extends JPanel {

	@SuppressWarnings("unused")
	private Controller control;
	
	
	public JimiAnalyticsPanel(Controller control) {
		super();
		this.control = control;
	}
}

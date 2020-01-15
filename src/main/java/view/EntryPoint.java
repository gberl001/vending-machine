/**
3 * This will be our entry point for our system.
 */
package view;

import javax.swing.JFrame;


/**
 * @author jimiford
 *
 */
public class EntryPoint extends JFrame {
	private static final long serialVersionUID = 1123581321L;
	
	public EntryPoint() {
		super();
		System.out.println(this.getClass().getResource(""));
		
	}
	
	public void createAndDisplayGUI() {
		
	}
	
	public static void main(String args[]) throws Exception {
		// TODO: Geoff to talk about SwingUtility
		
		if(!new java.io.File("database").exists()) {
			throw new Exception("Please run this jar file in the same directory that the" +
								"\"database\" directory is in");
		}
		
		
	}
}
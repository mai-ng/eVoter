/**
 * 
 */
package web.gui.secretary;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author louisnguyen
 * 
 */
public class EmptyPage extends JPanel {

	public EmptyPage(String contentMessage) {
		JLabel welcomeText = new JLabel(contentMessage);
		this.add(welcomeText);
	}
	

}

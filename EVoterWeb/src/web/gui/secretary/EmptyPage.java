/**
 * 
 */
package web.gui.secretary;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmptyPage extends JPanel {
	private static final long serialVersionUID = 1L;

	public EmptyPage(String contentMessage) {
		JLabel welcomeText = new JLabel(contentMessage);
		this.add(welcomeText);
	}
	

}

package web.gui.secretary.testShowGUI;

import javax.swing.JFrame;

import web.gui.secretary.LoginPanel;
import web.gui.secretary.MainPanel;

/**
 * Test show GUI {@link LoginPanel}
 * @author maint
 *
 */
public class TestShowGUI_Login {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test Log in page");
		frame.add(new LoginPanel(new MainPanel()));
		frame.setSize(700, 600);
		frame.setVisible(true);

	}

}

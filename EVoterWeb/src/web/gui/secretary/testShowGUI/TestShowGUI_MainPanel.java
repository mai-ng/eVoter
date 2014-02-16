package web.gui.secretary.testShowGUI;

import javax.swing.JFrame;

import web.gui.secretary.MainPanel;

/**
 * Test show GUI {@link MainPanel}
 * @author maint
 *
 */
public class TestShowGUI_MainPanel {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test main panel");
		frame.add( new MainPanel());
		frame.setSize(700, 600);
		frame.setVisible(true);

	}
}

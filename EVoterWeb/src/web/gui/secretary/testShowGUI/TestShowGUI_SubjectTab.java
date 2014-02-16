package web.gui.secretary.testShowGUI;

import javax.swing.JFrame;

import web.gui.secretary.SubjectTab;

/**
 * Test show GUI {@link SubjectTab}.
 * @author maint
 *
 */
public class TestShowGUI_SubjectTab {

	public static void main(String[] args) {
		TestShowGUI_Env.setUpLogIn();
		
		JFrame frame = new JFrame("Test-: display content of subject tab on menu bar");
		frame.add(new SubjectTab());
		frame.setSize(700, 600);
		frame.setVisible(true);

	}

}

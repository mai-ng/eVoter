package web.gui.secretary.testShowGUI;

import javax.swing.JFrame;

import web.gui.secretary.UserTab;
import evoter.share.model.UserType;
/**
 * Test show {@link UserTab} of Students.
 * @author maint
 *
 */
public class TestShowGUI_StudentTab {
	public static void main(String[] args) {
		TestShowGUI_Env.setUpLogIn();
		
		JFrame frame = new JFrame("Test-: display content of student tab on menu bar");
		frame.add(new UserTab(UserType.STUDENT));
		frame.setSize(700, 600);
		frame.setVisible(true);

	}
}

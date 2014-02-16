package web.gui.secretary.testShowGUI;

import javax.swing.JFrame;

import web.gui.secretary.UserTab;
import evoter.share.model.UserType;

/**
 * Test show {@link UserTab} of Teacher.
 * @author maint
 *
 */
public class TestShowGUI_TeacherTab {

	public static void main(String[] args) {
		TestShowGUI_Env.setUpLogIn();
		
		JFrame frame = new JFrame("Test-: display content of teacher tab on menu bar");
		frame.add(new UserTab(UserType.TEACHER));
		frame.setSize(700, 600);
		frame.setVisible(true);

	}

}

package web.gui.secretary.testShowGUI;

import javax.swing.JFrame;

import web.gui.secretary.UserItem;

/**
 * Show a {@link UserItem}
 * @author maint
 *
 */
public class TestShowGUI_UserItem {
	public static void main(String[] args) {
		TestShowGUI_Env.setUpUser();
		
		JFrame frame = new JFrame("Test-: display a user item");
		frame.add(new UserItem(TestShowGUI_Env.userTest));
		frame.setSize(600, 200);
		frame.setVisible(true);

	}
}

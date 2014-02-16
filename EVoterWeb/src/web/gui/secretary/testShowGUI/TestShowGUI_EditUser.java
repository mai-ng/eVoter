package web.gui.secretary.testShowGUI;

import web.gui.secretary.EditUser;

/**
 * Test show GUI {@link EditUser}
 * @author maint
 *
 */
public class TestShowGUI_EditUser {
	public static void main(String[] args) {
		TestShowGUI_Env.setUpUser();
		new EditUser(TestShowGUI_Env.userTest);
	}
}

package web.gui.secretary.testShowGUI;

import web.gui.secretary.ViewUser;

/**
 * Test show {@link ViewUser}
 * @author maint
 *
 */
public class TestShowGUI_ViewUser {
	public static void main(String[] args) {
		TestShowGUI_Env.setUpUser();
		new ViewUser(TestShowGUI_Env.userTest);
	}
}

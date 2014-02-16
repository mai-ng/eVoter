package web.gui.secretary.testShowGUI;

import web.gui.secretary.AddUser;

/**
 * Test show {@link AddUser}
 * @author maint
 *
 */
public class TestShowGUI_AddUser {
	public static void main(String[] args) {
		TestShowGUI_Env.setUpUser();
		new AddUser(TestShowGUI_Env.userTest.getUserTypeId());
	}
}

package web.gui.secretary.testShowGUI;

import web.gui.secretary.ViewSubject;

/**
 * Test show GUI {@link ViewSubject}
 * @author maint
 *
 */
public class TestShowGUI_ViewSubject {

	public static void main(String[] args) {
		TestShowGUI_Env.setUpSubject();
		new ViewSubject(TestShowGUI_Env.subTest);
	}

}

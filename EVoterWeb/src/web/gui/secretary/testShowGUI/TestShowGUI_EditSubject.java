package web.gui.secretary.testShowGUI;

import web.gui.secretary.EditSubject;

/**
 * Test show GUI {@link EditSubject}
 * @author maint
 *
 */
public class TestShowGUI_EditSubject{

		public static void main(String[] args) {
			TestShowGUI_Env.setUpSubject();
			new EditSubject(TestShowGUI_Env.subTest);
		}
}

package web.gui.secretary.testShowGUI;

import javax.swing.JFrame;

import web.gui.secretary.SubjectItem;

/**
 * Show a {@link SubjectItem}
 * @author maint
 *
 */
public class TestShowGUI_SubjectItem {

	public static void main(String[] args) {
		TestShowGUI_Env.setUpSubject();
		
		JFrame frame = new JFrame("Test-: display a subject item");
		frame.add(new SubjectItem(TestShowGUI_Env.subTest));
		frame.setSize(600, 200);
		frame.setVisible(true);
	}

}

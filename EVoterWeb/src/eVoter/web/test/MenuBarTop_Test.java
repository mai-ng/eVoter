package eVoter.web.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import eVoter.web.gui.secretary.ItemOfListView;
import eVoter.web.gui.secretary.MenuBarTop;

public class MenuBarTop_Test{
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MenuBarTop menubar = new MenuBarTop();
//		ItemOfListView item = new ItemOfListView();
		
		JFrame frame = new JFrame();
		frame.add(menubar,BorderLayout.NORTH);
//		frame.add(item);
		frame.setSize(400, 400);
		frame.setVisible(true);

	}

}

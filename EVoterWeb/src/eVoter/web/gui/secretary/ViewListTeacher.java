package eVoter.web.gui.secretary;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ViewListTeacher extends JPanel {

	private static final long serialVersionUID = 1L;
	private MenuBarTop menuBarTop;
	private JPanel teacherList;
	
	
	/**
	 * Create the panel.
	 */
	public ViewListTeacher() {
		initComponents();

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		this.setLayout(new BorderLayout());

		this.add(menuBarTop, BorderLayout.PAGE_START);
		
		this.add(teacherList, BorderLayout.PAGE_END);
	}

	public void initComponents(){
		menuBarTop = new MenuBarTop();
		
		teacherList = new JPanel();
		for( int i = 0; i < 10; i++){
			ItemOfListView item = new ItemOfListView("Label "+i,i);
			teacherList.add(item);
		}
		teacherList.setLayout(new BoxLayout(teacherList, BoxLayout.Y_AXIS));
	
	}
}

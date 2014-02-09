package web.gui.secretary;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import web.gui.secretary.subcomponents.ItemOfListView;

public class TeacherPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel teacherList;
	
	
	/**
	 * Create the panel.
	 */
	public TeacherPanel() {
		initComponents();

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		this.setLayout(new BorderLayout());

		this.add(teacherList, BorderLayout.PAGE_END);
	}

	public void initComponents(){
		teacherList = new JPanel();
		for( int i = 0; i < 10; i++){
			ItemOfListView item = new ItemOfListView("Teacher "+i, i, 1);
			teacherList.add(item);
		}
		teacherList.setLayout(new BoxLayout(teacherList, BoxLayout.Y_AXIS));
	
	}
}

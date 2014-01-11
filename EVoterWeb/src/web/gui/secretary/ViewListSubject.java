package web.gui.secretary;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import web.gui.secretary.subcomponents.ItemOfListView;
import web.gui.secretary.subcomponents.MenuBarTop;

public class ViewListSubject extends JPanel {

	private static final long serialVersionUID = 1L;
	private MenuBarTop menuBarTop;
	private JPanel teacherList;
	
	
	/**
	 * Create the panel.
	 */
	public ViewListSubject() {
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
			ItemOfListView item = new ItemOfListView("Subject "+i, i, 2);
			teacherList.add(item);
		}
		teacherList.setLayout(new BoxLayout(teacherList, BoxLayout.Y_AXIS));
	
	}
}

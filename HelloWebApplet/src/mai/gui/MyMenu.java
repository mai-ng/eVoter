package mai.gui;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	public MyMenu() {
		setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 450, 19);
		add(menuBar);
		
		JMenuItem mntmTeacher = new JMenuItem("Teacher");
		menuBar.add(mntmTeacher);
		
		JMenuItem mntmStudent = new JMenuItem("Student");
		menuBar.add(mntmStudent);
		
		JMenuItem mntmSecretary = new JMenuItem("Subject");
		menuBar.add(mntmSecretary);
		
		JMenu mnNew = new JMenu("New");
		mnNew.setBounds(12, 87, 95, 27);
		add(mnNew);
		
		JMenuItem mntmTeacher_1 = new JMenuItem("Teacher");
		mnNew.add(mntmTeacher_1);
		
		JMenuItem mntmStudent_1 = new JMenuItem("Student");
		mnNew.add(mntmStudent_1);
		
		JMenuItem mntmSubject = new JMenuItem("Subject");
		mnNew.add(mntmSubject);
		
		JMenu mnRemove = new JMenu("Remove");
		mnRemove.setBounds(12, 127, 95, 19);
		add(mnRemove);
		
		JMenuItem mntmTeacher_2 = new JMenuItem("Teacher");
		mnRemove.add(mntmTeacher_2);
		
		JMenuItem mntmStudent_2 = new JMenuItem("Student");
		mnRemove.add(mntmStudent_2);
		
		JMenuItem mntmSubject_1 = new JMenuItem("Subject");
		mnRemove.add(mntmSubject_1);
		
		JMenu mnView = new JMenu("View");
		mnView.setBounds(12, 158, 95, 19);
		add(mnView);

	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

package eVoter.web.gui.secretary;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * @author maint<br>
 * top menu bar which contains 3 tabs:
 * "Teacher / Student / Subject"
 */
public class MenuBarTop extends JMenuBar {

	private static final long serialVersionUID = 1L;

	//tabs on menu bar
	private JMenu teacherBar;
	private JMenu studentBar;
	private JMenu subjectBar;
	

	/**
	 * Create the menu bar on the top.
	 */
	public MenuBarTop() {
		initComponents();
				
		this.add(teacherBar);
		this.add(studentBar);
		this.add(subjectBar);

	}
	
	/**
	 * initialize all menus on the menu bar: Teacher, Student, Subject
	 */
	public void initComponents(){
		
		//create tabs on the menu bar
		teacherBar = new JMenu("Teacher");
		studentBar = new JMenu("Student");
		subjectBar = new JMenu("Subject");
	}

}

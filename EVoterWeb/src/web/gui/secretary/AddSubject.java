package web.gui.secretary;

import java.awt.GridBagConstraints;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @author maint<br>
 * a JFrame to add a subject. 
 * extends {@link GUISubjectAbstract} class.
 */
public class AddSubject extends GUISubjectAbstract {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtTitle;

	private JButton btnInvite;
	

	/**
	 * set the title of the frame, and initialize its components.<br>
	 * Design user interface ( {@link JFrame} ) to add a subject.
	 * @throws IOException 
	 */
	public AddSubject() throws IOException {
		this.setTitle("Add new subject");
		initComponents();
		importFile();
		
		// design user interface
		// Row 0: Title
		c.weightx = 0;
		this.add(new JLabel("Title: "));
		c.weightx = 0.5;
		this.add(txtTitle, c);
		

		// Row 1: Space
		c.gridy = 1;
		c.weightx = 0;
		this.add(new JLabel("  "),c);
		
		

		// setup Teacher panel
		c.weighty = 1;
		JPanel teacherPanel = new JPanel();
		teacherPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Teacher"));

		teacherPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		teacherPanel.add(new JScrollPane(listTeacherView,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		teacherPanel.add(btnAddTeacher, c);
		

		// Row 2: Teacher table
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(teacherPanel, c);

		// Setup student panel
		c.gridwidth = 1;
		c.weighty = 1;
		JPanel studentPanel = new JPanel();
		studentPanel.setBorder(BorderFactory.createTitledBorder("Student"));

		studentPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		studentPanel.add(new JScrollPane(listStudentView,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		studentPanel.add(btnAddStudent, c);

		// Row 3: Students table
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(studentPanel, c);

		// Row 4: Create Button
		c.gridy = 5;
		c.fill = 0;
		c.weighty = 0.2;
		this.add(btnInvite, c);
		
		c.gridx = 2;
		c.fill = 0;
		c.weighty = 0.2;
		this.add(btnClose, c);


	}

	/**
	 * initialize additional components which are not defined in
	 * {@link GUISubjectAbstract}. Components only belong to {@link AddSubject}
	 */
	protected void initComponents() {
		super.initComponents();
		
		txtTitle = new JTextField();

		btnAddTeacher = new JButton(IMPORT_TEACHER);
		btnAddStudent = new JButton(IMPORT_STUDENT);
		btnInvite = new JButton(INVITE);
	}

}
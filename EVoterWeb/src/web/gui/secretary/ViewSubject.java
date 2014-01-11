	package web.gui.secretary;

	import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author maint<br>
 * a JFrame to view detail a subject . 
 * extends {@link GUISubjectAbstract} class.
 */
public class ViewSubject extends GUISubjectAbstract {


	private static final long serialVersionUID = 1L;

	/**
	 * title of a subject
	 */
	private JLabel lblTitle;

	/**
	 * set the title of the frame, and initialize its components.<br>
	 * Design user interface ( {@link JFrame} ) to view a subject.
	 */
	public ViewSubject() {
		this.setTitle("View subject information");
		initComponents();
		
		
		// user interface of viewing detail subject
		// Row 0: Title
		c.weightx = 0;
		this.add(new JLabel("Title: "));
		c.weightx = 0.5;
		this.add(lblTitle,c);
		

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
		teacherPanel.add(new JScrollPane(getTxtTeacher(),
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.gridheight = 1;

		// Row 3: Teacher table
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
		studentPanel.add(new JScrollPane(getTxtStudent(),
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);
		c.gridheight = 1;

		// Row 4: Students table
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(studentPanel, c);

		// Row 5: Create Button
		c.gridy = 5;
		c.gridx = 2;
		c.fill = 0;
		c.weighty = 0.2;
		this.add(btnClose, c);


	}

	/**
	 * initialize additional components which are not defined in
	 * {@link GUISubjectAbstract}. Components of only {@link ViewSubject}
	 */
	protected void initComponents() {
		super.initComponents();
		lblTitle = new JLabel("here is the title of the subject");
		getTxtTeacher().setEditable(false);		
		getTxtStudent().setEditable(false);

	}
	}


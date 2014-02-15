package web.gui.secretary.spec;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import web.gui.secretary.AddSubject;
import web.gui.secretary.EditSubject;
import web.gui.secretary.ViewSubject;
import web.util.ReadFileByClick;

/**
 * extended by {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject}.<br> 
 * Create common components, initialize them, and define a layout and user interface. <br>
 * @author maint<br>
 *         
 */
public abstract class SubjectGUIAbstract extends JFrame {

	private static final long serialVersionUID = 1L;

	protected JLabel lblTitle;
	protected JTextField txtTitle;
	protected JTextArea listTeacherView;
	protected JTextArea listStudentView;

	protected JButton btnUpdate;
	protected JButton btnAddTeacher;
	protected JButton btnAddStudent;

	public SubjectGUIAbstract() {
		initComponents();
		try {
			importFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		buildGUI();
	}

	/**
	 * design user interface for {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject}.
	 */
	public void buildGUI() {
		//show frame
		setSize(700, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		
		// design user interface
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 0, 5);
	
		// Row 0: Title
		c.weightx = 0;
		this.add(lblTitle, c);
		c.weightx = 0.5;
		c.ipady = 10;
		this.add(txtTitle, c);

		// Row 1: Space
		c.gridy = 1;
		c.weightx = 0;
		this.add(new JLabel("  "), c);

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
		this.add(btnUpdate, c);
	}

	/**
	 * import a file to {@link JTextArea} when click a button. This function is
	 * used in {@link AddSubject}, {@link EditSubject} classes.
	 * @throws IOException
	 */
	public void importFile() throws IOException {
		btnAddTeacher.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ReadFileByClick.readFile(listTeacherView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		btnAddStudent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ReadFileByClick.readFile(listStudentView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * initialize all components which are used in {@link AddSubject},
	 * {@link EditSubject}, {@link ViewSubject}.
	 */
	protected void initComponents() {
		lblTitle = new JLabel("Title: ");
		txtTitle = new JTextField();

		listTeacherView = new JTextArea();
		listStudentView = new JTextArea();

		btnAddStudent = new JButton("Import");
		btnAddTeacher = new JButton("Import");
		btnUpdate = new JButton();
	}
	

	/**
	 * @return the txtTitle is title field of a subject
	 */
	public JTextField getTxtTitle() {
		return txtTitle;
	}

}
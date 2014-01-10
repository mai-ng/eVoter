	package eVoter.web.gui.secretary;

	import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ViewSubject extends JPanel {


	private static final long serialVersionUID = 1L;
	
//	public static final String INVITE = "Invite";
//	public static final String IMPORT_TEACHER = "Import";
//	public static final String IMPORT_STUDENT = "Import";
	public static final String CLOSE = "Close";

//	public JTextField txtTitle;
	private JLabel lblTitle;

//	private JButton btnAddTeacher;
//	private JButton btnAddStudent;
//	private JButton btnInvite;
	private JButton btnClose;
	
	public JTable tblTeacher;
	public JTable tblStudent;
	public DefaultTableModel modelTeacher;
	public DefaultTableModel modelStudent;
	GridBagConstraints c;

	/**
	 * paint user interface for viewing a subject
	 */
	public ViewSubject() {
		initComponents();
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		GridBagLayout gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 5);

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
		teacherPanel.add(new JScrollPane(tblTeacher,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

//		c.weightx = 0;
//		c.weighty = 0;
//		c.gridx = 1;
		c.gridheight = 1;
//		teacherPanel.add(btnAddTeacher, c);
		

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
		studentPanel.add(new JScrollPane(tblStudent,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);
//
//		c.weightx = 0;
//		c.weighty = 0;
//		c.gridx = 1;
		c.gridheight = 1;
//		studentPanel.add(btnAddStudent, c);

		// Row 4: Students table
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(studentPanel, c);

		// Row 5: Create Button
		c.gridy = 5;
//		c.fill = 0;
//		c.weighty = 0.2;
//		this.add(btnInvite, c);
		
		c.gridx = 2;
		c.fill = 0;
		c.weighty = 0.2;
		this.add(btnClose, c);


	}

	public void reset() {
//		this.txtTitle.setText("");
		while (modelTeacher.getRowCount() > 0) {
			modelTeacher.removeRow(0);
		}

		while (modelStudent.getRowCount() > 0) {
			modelStudent.removeRow(0);
		}
	}


	private JTable createTable(DefaultTableModel model) {
		JTable table = new JTable(model);
		table.setFillsViewportHeight(true);
		TableColumn column = null;
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);

			if (i == 2) {
				column.setPreferredWidth(100);
			} else {
				column.setPreferredWidth(10);
			}
		}
		return table;
	}

	/**
	 * initialize components
	 */
	private void initComponents() {
//		txtTitle = new JTextField();
		lblTitle = new JLabel("here is the title of the subject");

//		btnAddTeacher = new JButton(IMPORT_TEACHER);
//		btnAddStudent = new JButton(IMPORT_STUDENT);
//		btnInvite = new JButton(INVITE);
		btnClose = new JButton(CLOSE);

		modelTeacher = new DefaultTableModel();
		tblTeacher = createTable(modelTeacher);
		modelStudent = new DefaultTableModel();
		tblStudent = createTable(modelStudent);

	}
	}

